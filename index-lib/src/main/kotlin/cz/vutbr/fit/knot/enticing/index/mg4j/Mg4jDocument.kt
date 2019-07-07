package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.config.dsl.Index
import it.unimi.di.big.mg4j.document.AbstractDocument
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import it.unimi.dsi.io.WordReader
import it.unimi.dsi.lang.MutableString
import java.io.StringReader

internal val wordReader = WhitespaceWordReader()

typealias DocumentContent = Map<String, List<String>>

class Mg4jDocument(
        private val indexes: List<Index>,
        private val metadata: Reference2ObjectMap<Enum<*>, Any>,
        private val content: List<String>
) : AbstractDocument() {

    override fun title(): CharSequence = metadata[DocumentMetadata.TITLE] as CharSequence

    override fun uri(): CharSequence = metadata[DocumentMetadata.URI] as CharSequence

    override fun wordReader(field: Int): WordReader = wordReader

    override fun content(field: Int): Any = content[field].reader()

    fun readContentAt(left: Int, right: Int): DocumentContent = indexes.asSequence()
            .map { it.name to readIndex(it.columnIndex, left, right) }
            .toMap()

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

    /**
     * reads the content of all indexes
     * this is EXPENSIVE, for debug purposes
     */
    fun wholeContent(): Map<String, List<String>> = indexes.map {
        it.name to readIndex(it.columnIndex)
    }.toMap()
}