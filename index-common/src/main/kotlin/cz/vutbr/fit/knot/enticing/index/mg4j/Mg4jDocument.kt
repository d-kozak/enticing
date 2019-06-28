package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.index.config.dsl.Index
import it.unimi.di.big.mg4j.document.AbstractDocument
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import it.unimi.dsi.io.WordReader
import it.unimi.dsi.lang.MutableString
import java.io.InputStreamReader

internal val wordReader = WhitespaceWordReader()

internal val whitespaceRegex = """\s+""".toRegex()

class Mg4jDocument(
        private val indexes: List<Index>,
        private val metadata: Reference2ObjectMap<Enum<*>, Any>,
        private val content: List<ByteArray>
) : AbstractDocument() {

    override fun title(): CharSequence = metadata[DocumentMetadata.TITLE] as CharSequence

    override fun uri(): CharSequence = metadata[DocumentMetadata.URI] as CharSequence

    override fun wordReader(field: Int): WordReader = wordReader

    override fun content(field: Int): Any = content[field].inputStream().reader()

    fun readContentAt(left: Int, right: Int): Map<String, List<String>> = indexes.asSequence()
            .map { it.name to readIndex(it.columnIndex, left, right) }
            .toMap()

    private fun readIndex(index: Int, left: Int, right: Int): List<String> {
        val inputReader = content(index) as InputStreamReader
        val wordReader = wordReader(index)
        val combined = wordReader.setReader(inputReader)
        val result = mutableListOf<String>()

        val word = MutableString()
        val nonWord = MutableString()

        for (i in 0 until right) {
            if (!combined.next(word, nonWord)) {
                throw IllegalStateException("Could not read enough words at index ${indexes[index].name}, left $left, right $right, result $result")
            }
            if (i >= left)
                result.add(word.toString())
        }

        return result
    }

    val size: Int
        get() = content[0].size

    /**
     * reads the content of all indexes
     * this is EXPENSIVE, for debug purposes
     */
    fun wholeContent(): Map<String, List<String>> = indexes.map {
        it.name to content[it.columnIndex].inputStream().bufferedReader().readText().split(whitespaceRegex)
    }.toMap()
}