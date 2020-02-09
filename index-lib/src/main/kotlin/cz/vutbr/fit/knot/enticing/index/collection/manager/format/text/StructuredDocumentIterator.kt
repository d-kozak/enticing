package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.IndexConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import org.slf4j.LoggerFactory


/**
 * Callback methods that [StructuredDocumentIterator] uses when iterating over the content of the document
 */
interface DocumentVisitor {
    fun visitMatchStart(queryInterval: Interval)
    fun visitMatchEnd()
    fun visitEntityStart(attributes: List<String>, entityClass: String)
    fun visitEntityEnd()
    fun visitWord(indexes: List<String>)
}

/**
 * Base class for visitors that generate [ResultFormat.Snippet] formats, provides some helper properties that the subclasses can use
 */
abstract class TextFormatGeneratingVisitor(
        protected val config: MetadataConfiguration,
        private val defaultIndexName: String,
        private val interval: Interval,
        private val document: IndexedDocument
) : DocumentVisitor {
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
class StructuredDocumentIterator(private val corpusConfiguration: MetadataConfiguration) {
    private val log = LoggerFactory.getLogger(StructuredDocumentIterator::class.java)

    /**
     * Iterates over a document and notifies the visitor about every start and end of matched region, start and end of entity and every word,
     * priority is in this order
     */
    fun iterateDocument(document: IndexedDocument, matchStarts: Map<Int, Interval>, matchEnds: Set<Int>, visitor: DocumentVisitor, interval: Interval? = null) {
        if (corpusConfiguration.entities.isNotEmpty()) {
            requireNotNull(corpusConfiguration.entityIndex) { "some entities are defined, entity index cannot be null" }
            requireNotNull(corpusConfiguration.lengthIndex) { "some entities are defined, entity length index cannot be null" }
        }


        val (from, to) = interval ?: Interval.valueOf(0, document.size)
        var startedEntity: Triple<List<String>, String, Int>? = null

        for ((i, word) in document.withIndex()) {
            if (i < from) continue
            if (i > to) break

            if (i in matchStarts) {
                if (startedEntity != null) visitor.visitEntityEnd()
                visitor.visitMatchStart(matchStarts.getValue(i))
                if (startedEntity != null)
                    visitor.visitEntityStart(startedEntity.first, startedEntity.second)
            }

            if (corpusConfiguration.entities.isNotEmpty()) {
                val entityClass = word[corpusConfiguration.entityIndex!!.columnIndex]
                if (entityClass != "0") {
                    val len = word[corpusConfiguration.lengthIndex!!.columnIndex].toIntOrNull() ?: {
                        log.warn("${document.title}:${document.id}:[$i]:could not parse entity length index ${word[corpusConfiguration.lengthIndex!!.columnIndex]}")
                        1
                    }()
                    val isReplicated = len == -1
                    if (!isReplicated) {
                        val attributeInfo = corpusConfiguration.entities[entityClass]
                        startedEntity = if (attributeInfo != null) {
                            val attributes = attributeInfo.attributes.values.map { it.index.columnIndex }.map { word[it] }

                            visitor.visitEntityStart(attributes, entityClass)
                            Triple(attributes, entityClass, i + len - 1)
                        } else {
                            log.warn("${document.title}:${document.id}:[$i]:encountered entity $entityClass for which there is no known format, attributes will be empty")
                            visitor.visitEntityStart(emptyList(), entityClass)
                            Triple(emptyList(), entityClass, i)
                        }
                    }
                }
            }

            visitor.visitWord(word)

            if (startedEntity != null && i == startedEntity.third) {
                visitor.visitEntityEnd()
                startedEntity = null
            }
            if (i in matchEnds) {
                if (startedEntity != null) visitor.visitEntityEnd()
                visitor.visitMatchEnd()
                if (startedEntity != null) visitor.visitEntityStart(startedEntity.first, startedEntity.second)
            }
        }
    }

}


