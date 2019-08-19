package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import java.util.*

data class StructuredDocumentContent(
        val elements: List<DocumentElement>,
        val corpusConfiguration: CorpusConfiguration
) : Iterable<DocumentElement> {

    init {
        for (element in elements) {
            element.parent = this
            if (element is DocumentElement.Entity) {
                for (word in element.words) {
                    word.parent = this
                }
            }
        }
    }

    operator fun get(index: String): List<String> {
        val result = mutableListOf<String>()

        for (element in elements) {
            when (element) {
                is DocumentElement.Word -> {
                    result.add(element[corpusConfiguration.indexOf(index)])
                }

                is DocumentElement.Entity -> {
                    result.addAll(element[corpusConfiguration.indexOf(index)])
                }
            }
        }

        return result
    }

    operator fun get(i: Int): DocumentElement {
        val indexes = elements.map { it.index }
        val j = Collections.binarySearch(indexes, i)
        return if (j >= 0) elements[j] else elements[-j - 1]
    }

    fun toMap(): Map<String, List<String>> = corpusConfiguration.indexes
            .values
            .asSequence()
            .map { it.name }
            .map { it to this[it] }
            .toMap()

    override fun iterator(): Iterator<DocumentElement> = elements.iterator()
}

sealed class DocumentElement {

    internal lateinit var parent: StructuredDocumentContent

    data class Word(override val index: Int, val indexes: List<String>) : DocumentElement() {
        operator fun get(i: Int) = if (i < indexes.size) indexes[i] else {
            System.err.println("$i is too big")
            "NULL"
        }

        operator fun get(key: String) = this[parent.corpusConfiguration.indexOf(key)]
    }

    data class Entity(override val index: Int, val entityClass: String, val entityInfo: List<String>, val words: List<Word>) : DocumentElement() {
        operator fun get(i: Int): List<String> = words.map { it[i] }

        operator fun get(key: String) = this[parent.corpusConfiguration.indexOf(key)]
    }

    abstract val index: Int
}