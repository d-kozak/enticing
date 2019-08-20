package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.next

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import org.slf4j.LoggerFactory


interface DocumentIteratorListener {
    fun matchStart(queryInterval: Interval)
    fun matchEnd()
    fun entityStart(attributes: List<String>, entityClass: String)
    fun entityEnd()
    fun word(indexes: List<String>)

}


class StructuredDocumentIterator(private val corpusConfiguration: CorpusConfiguration) {
    private val log = LoggerFactory.getLogger(StructuredDocumentIterator::class.java)

    /**
     * Iterates over a document and notifies the listener about every start and end of matched region, start and end of entity and every word,
     * priority is in this order
     */
    fun iterateDocument(document: IndexedDocument, matchStarts: Map<Int, Interval>, matchEnds: Set<Int>, listener: DocumentIteratorListener, from: Int = 0, to: Int = document.size) {

        var startedEntity: Triple<List<String>, String, Int>? = null

        for ((i, word) in document.withIndex()) {
            if (i < from) continue
            if (i > to) break

            matchStarts[i]?.let {
                if (startedEntity != null) listener.entityEnd()
                listener.matchStart(it)
                if (startedEntity != null)
                    listener.entityStart(startedEntity!!.first, startedEntity!!.second)
            }

            val entityClass = word[corpusConfiguration.entityIndex]
            if (entityClass != "0") {
                if (startedEntity != null) {
                    log.warn("new entity started before previous one ($startedEntity) finished, old will be finished now")
                    listener.entityEnd()
                }

                val attributeInfo = corpusConfiguration.entities[entityClass]
                startedEntity = if (attributeInfo != null) {
                    val attributes = attributeInfo.attributes.values.map { it.columnIndex }.map { word[it] }
                    val len = word[corpusConfiguration.entityLengthIndex].toIntOrNull() ?: {
                        log.warn("could not parse entity length index ${word[corpusConfiguration.entityLengthIndex]}")
                        1
                    }()
                    listener.entityStart(attributes, entityClass)
                    Triple(attributes, entityClass, i + len - 1)
                } else {
                    log.warn("encountered entity $entityClass for which there is no known format, attributes will be empty")
                    listener.entityStart(emptyList(), entityClass)
                    Triple(emptyList(), entityClass, i)
                }
            }

            listener.word(word)

            if (startedEntity != null && i == startedEntity.third) {
                listener.entityEnd()
                startedEntity = null
            }
            if (i in matchEnds) {
                if (startedEntity != null) listener.entityEnd()
                listener.matchEnd()
                if (startedEntity != null) listener.entityStart(startedEntity.first, startedEntity.second)
            }
        }
    }

}


