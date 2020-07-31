package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.IndexConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger


/**
 * Callback methods that [StructuredDocumentIterator] uses when iterating over the content of the document
 */
interface DocumentListener {
    fun onMatchStart(queryInterval: Interval)
    fun onMatchEnd()
    fun onEntityStart(attributes: List<String>, entityClass: String)
    fun onEntityEnd()
    fun onWord(indexes: List<String>)
}

/**
 * Base class for visitors that generate [ResultFormat.Snippet] formats, provides some helper properties that the subclasses can use
 */
abstract class TextFormatGeneratingListener(
        protected val config: MetadataConfiguration,
        private val defaultIndexName: String,
        private val interval: Interval,
        private val document: IndexedDocument
) : DocumentListener {
    abstract fun build(): ResultFormat.Snippet

    protected val defaultIndex: IndexConfiguration = config.indexes.getValue(defaultIndexName)

    protected val metaIndexes: List<IndexConfiguration> = config.indexes.values.filter { it.name != defaultIndexName }

    protected val location = interval.from

    protected val size = interval.size

    protected val canExtend = interval.from > 0 || interval.to < document.size - 1
}


/**
 * Iterates over a document and informs the visitor about matched regions, entities and words encountered
 */
@Cleanup("Maybe turn into a function OR move some of the function params to constructor?")
class StructuredDocumentIterator(private val metadataConfiguration: MetadataConfiguration, loggerFactory: LoggerFactory) {

    private val logger = loggerFactory.logger { }

    /**
     * Iterates over a document and notifies the visitor about every start and end of matched region, start and end of entity and every word,
     * priority is in this order
     */
    fun iterateDocument(document: IndexedDocument, matchStarts: Map<Int, Interval>, matchEnds: Set<Int>, listener: DocumentListener, interval: Interval? = null) {
        if (metadataConfiguration.entities.isNotEmpty()) {
            requireNotNull(metadataConfiguration.entityIndex) { "some entities are defined, entity index cannot be null" }
            requireNotNull(metadataConfiguration.lengthIndex) { "some entities are defined, entity length index cannot be null" }
        }


        val (from, to) = interval ?: Interval.valueOf(0, document.size)
        var startedEntity: Triple<List<String>, String, Int>? = null

        for ((i, word) in document.withIndex()) {
            if (i < from) continue
            if (i > to) break

            if (i in matchStarts) {
                if (startedEntity != null) listener.onEntityEnd()
                listener.onMatchStart(matchStarts.getValue(i))
                if (startedEntity != null)
                    listener.onEntityStart(startedEntity.first, startedEntity.second)
            }

            if (metadataConfiguration.entities.isNotEmpty()) {
                val entityClass = word[metadataConfiguration.entityIndex!!.columnIndex]
                if (entityClass != "0") {
                    val len = word[metadataConfiguration.lengthIndex!!.columnIndex].toIntOrNull() ?: {
                        logger.warn("${document.title}:${document.id}:[$i]:could not parse entity length index ${word[metadataConfiguration.lengthIndex!!.columnIndex]}")
                        1
                    }()
                    val isReplicated = len == -1
                    if (!isReplicated) {
                        val entity = metadataConfiguration.resolveEntity(entityClass)
                        startedEntity = if (entity != null) {
                            val attributes = entity.allAttributes.values.map { it.index.columnIndex }.map { word[it] }

                            listener.onEntityStart(attributes, entity.name)
                            Triple(attributes, entity.name, i + len - 1)
                        } else {
                            logger.warn("${document.title}:${document.id}:[$i]:encountered entity $entityClass for which there is no known format, attributes will be empty")
                            listener.onEntityStart(emptyList(), entityClass)
                            Triple(emptyList(), entityClass, i)
                        }
                    }
                }
            }

            listener.onWord(word)

            if (startedEntity != null && i == startedEntity.third) {
                listener.onEntityEnd()
                startedEntity = null
            }
            if (i in matchEnds) {
                if (startedEntity != null) listener.onEntityEnd()
                listener.onMatchEnd()
                if (startedEntity != null) listener.onEntityStart(startedEntity.first, startedEntity.second)
            }
        }
    }

}


