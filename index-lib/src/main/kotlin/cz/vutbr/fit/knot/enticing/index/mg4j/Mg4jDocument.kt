package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.Index
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetPartsFields
import it.unimi.di.big.mg4j.document.AbstractDocument
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import it.unimi.dsi.io.WordReader
import it.unimi.dsi.lang.MutableString
import java.io.StringReader

internal val wordReader = WhitespaceWordReader()


class Mg4jDocument(
        private val corpusConfiguration: CorpusConfiguration,
        private val metadata: Reference2ObjectMap<Enum<*>, Any>,
        private val content: List<String>
) : AbstractDocument() {

    private val indexes: List<Index>
        get() = corpusConfiguration.indexes.values.toList()

    override fun title(): CharSequence = metadata[DocumentMetadata.TITLE] as CharSequence

    override fun uri(): CharSequence = metadata[DocumentMetadata.URI] as CharSequence

    override fun wordReader(field: Int): WordReader = wordReader

    override fun content(field: Int): Any = content[field].reader()

    fun loadSnippetPartsFields(left: Int? = null, right: Int? = null): SnippetPartsFields {
        val indexContent = indexes.asSequence()
                .map { it.name to readIndex(it.columnIndex, left, right) }
                .toMap()

        fun collectIndexValuesAt(i: Int): List<String> = indexes.map { indexContent[it.name]!![i] }

        // all indexes should be the same length
        val len = indexContent["token"]!!.size

        val result = mutableListOf<SnippetElement>()
        var i = 0
        while (i < len) {
            val nertag = indexContent["nertag"]!![i]
            if (nertag != "0") {
                val entityInfo = listOf<String>()
                val nerlen = Math.max(indexContent["nerlength"]!![i].toIntOrNull() ?: 1, 1)
                val words = (i until i + nerlen)
                        .map { SnippetElement.Word(it, collectIndexValuesAt(it)) }
                result.add(SnippetElement.Entity(i, entityInfo, words))
                i += nerlen

            } else {
                result.add(SnippetElement.Word(i, collectIndexValuesAt(i)))
                i++
            }
        }
        return SnippetPartsFields(result, corpusConfiguration)
    }


    private fun readIndex(index: Int, left: Int? = null, right: Int? = null): List<String> {
        val inputReader = content(index) as StringReader
        val wordReader = wordReader(index)
        val combined = wordReader.setReader(inputReader)
        val result = mutableListOf<String>()

        val word = MutableString()
        val nonWord = MutableString()

        val range = if (right != null) 0 until right else 0..Int.MAX_VALUE

        for (i in range) {
            if (!combined.next(word, nonWord)) {
                break
            }
            if (left != null) {
                if (i >= left)
                    result.add(word.toString())
            } else result.add(word.toString())
        }
        return result
    }
}