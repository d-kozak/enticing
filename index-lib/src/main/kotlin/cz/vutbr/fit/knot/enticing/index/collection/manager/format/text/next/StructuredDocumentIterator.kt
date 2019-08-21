package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.next

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.Index
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
abstract class TextFormatGeneratingVisitor(protected val config: CorpusConfiguration, private val defaultIndexName: String) : DocumentVisitor {
    abstract fun build(): ResultFormat.Snippet

    protected val defaultIndex: Index = config.indexes.getValue(defaultIndexName)

    protected val metaIndexes: List<Index> = config.indexes.values.filter { it.name != defaultIndexName }
}


/**
 * Iterates over a document and informs the visitor about matched regions, entities and words encountered
 */
@Cleanup("Maybe turn into a function OR move some of the function params to constructor?")
class StructuredDocumentIterator(private val corpusConfiguration: CorpusConfiguration) {
    private val log = LoggerFactory.getLogger(StructuredDocumentIterator::class.java)

    /**
     * Iterates over a document and notifies the listener about every start and end of matched region, start and end of entity and every word,
     * priority is in this order
     */
    fun iterateDocument(document: IndexedDocument, matchStarts: Map<Int, Interval>, matchEnds: Set<Int>, listener: DocumentVisitor, interval: Interval? = null) {
        val (from, to) = interval ?: Interval.valueOf(0, document.size)
        var startedEntity: Triple<List<String>, String, Int>? = null

        for ((i, word) in document.withIndex()) {
            if (i < from) continue
            if (i > to) break

            matchStarts[i]?.let {
                if (startedEntity != null) listener.visitEntityEnd()
                listener.visitMatchStart(it)
                if (startedEntity != null)
                    listener.visitEntityStart(startedEntity!!.first, startedEntity!!.second)
            }

            val entityClass = word[corpusConfiguration.entityIndex]
            if (entityClass != "0") {
                if (startedEntity != null) {
                    log.warn("new entity started before previous one ($startedEntity) finished, old will be finished now")
                    listener.visitEntityEnd()
                }

                val attributeInfo = corpusConfiguration.entities[entityClass]
                startedEntity = if (attributeInfo != null) {
                    val attributes = attributeInfo.attributes.values.map { it.columnIndex }.map { word[it] }
                    val len = word[corpusConfiguration.entityLengthIndex].toIntOrNull() ?: {
                        log.warn("could not parse entity length index ${word[corpusConfiguration.entityLengthIndex]}")
                        1
                    }()
                    listener.visitEntityStart(attributes, entityClass)
                    Triple(attributes, entityClass, i + len - 1)
                } else {
                    log.warn("encountered entity $entityClass for which there is no known format, attributes will be empty")
                    listener.visitEntityStart(emptyList(), entityClass)
                    Triple(emptyList(), entityClass, i)
                }
            }

            listener.visitWord(word)

            if (startedEntity != null && i == startedEntity.third) {
                listener.visitEntityEnd()
                startedEntity = null
            }
            if (i in matchEnds) {
                if (startedEntity != null) listener.visitEntityEnd()
                listener.visitMatchEnd()
                if (startedEntity != null) listener.visitEntityStart(startedEntity.first, startedEntity.second)
            }
        }
    }

}


