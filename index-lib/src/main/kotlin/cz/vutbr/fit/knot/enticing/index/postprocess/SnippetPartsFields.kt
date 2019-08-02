package cz.vutbr.fit.knot.enticing.index.postprocess

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import java.util.*

@Cleanup("Merge together with TextUnit")
data class SnippetPartsFields(
        val elements: List<SnippetElement>,
        val corpusConfiguration: CorpusConfiguration
) : Iterable<SnippetElement> {

    init {
        for (element in elements) {
            element.parent = this
            if (element is SnippetElement.Entity) {
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
                is SnippetElement.Word -> {
                    result.add(element[corpusConfiguration.indexOf(index)])
                }

                is SnippetElement.Entity -> {
                    result.addAll(element[corpusConfiguration.indexOf(index)])
                }
            }
        }

        return result
    }

    operator fun get(i: Int): SnippetElement {
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

    override fun iterator(): Iterator<SnippetElement> = elements.iterator()
}

sealed class SnippetElement {

    internal lateinit var parent: SnippetPartsFields

    data class Word(override val index: Int, val indexes: List<String>) : SnippetElement() {
        operator fun get(i: Int) = if (i < indexes.size) indexes[i] else {
            System.err.println("$i is too big")
            "NULL"
        }

        operator fun get(key: String) = this[parent.corpusConfiguration.indexOf(key)]
    }

    data class Entity(override val index: Int, val entityClass: String, val entityInfo: List<String>, val words: List<Word>) : SnippetElement() {
        operator fun get(i: Int): List<String> = words.map { it[i] }

        operator fun get(key: String) = this[parent.corpusConfiguration.indexOf(key)]
    }

    abstract val index: Int
}