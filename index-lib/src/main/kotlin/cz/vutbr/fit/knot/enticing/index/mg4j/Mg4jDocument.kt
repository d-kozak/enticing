package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.Speed
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.Index
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetPartsFields
import it.unimi.di.big.mg4j.document.AbstractDocument
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import it.unimi.dsi.io.WordReader
import it.unimi.dsi.lang.MutableString
import it.unimi.dsi.util.Interval
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

    fun size() = metadata[DocumentMetadata.SIZE] as Int

    override fun wordReader(field: Int): WordReader = wordReader

    override fun content(field: Int): Any = content[field].reader()


    /**
     * Loads SnippetPartsFields from part of the document
     *
     * @param interval interval which should be loaded
     */
    fun loadSnippetPartsFields(interval: Interval, filteredConfig: CorpusConfiguration): SnippetPartsFields = loadSnippetPartsFields(interval.left, interval.right, filteredConfig)
    /**
     * Loads SnippetPartsFields from part of the document
     *
     * @param left left limit, inclusive
     * @param right right limit, inclusive
     */
    @Cleanup("Ugly code, should be refactored")
    @Speed("This is probably slower than necessary")
    fun loadSnippetPartsFields(left: Int = 0, _right: Int = -1, filteredConfig: CorpusConfiguration): SnippetPartsFields {
        val right = if (_right == -1) size() else _right
        val indexContent = indexes.asSequence()
                .map { it.name to readIndex(it.columnIndex, left, right) }
                .toMap()
        val loadedDataSize = indexContent["token"]!!.size
        fun collectIndexValuesAt(i: Int): List<String> = indexes.map { indexContent[it.name]!![i] }

        val result = mutableListOf<SnippetElement>()
        var i = 0
        val limit = Math.min(right - left, loadedDataSize)
        while (i < limit) {
            val entityClass = indexContent[filteredConfig.entityMapping.entityIndex]!![i]
            if (entityClass != "0") {
                val entityInfo: List<String> = filteredConfig.entities[entityClass]?.let { entity ->
                    entity.attributes.values.map { indexContent[it.correspondingIndex]!![i] }
                } ?: listOf()

                val nerlen = Math.max(indexContent["nerlength"]!![i].toIntOrNull() ?: 1, 1)
                val words = (i until Math.min(i + nerlen, loadedDataSize))
                        .map { SnippetElement.Word(left + it, collectIndexValuesAt(it)) }
                result.add(SnippetElement.Entity(left + i, entityClass, entityInfo, words))
                i += nerlen

            } else {
                result.add(SnippetElement.Word(left + i, collectIndexValuesAt(i)))
                i++
            }
        }
        return SnippetPartsFields(result, filteredConfig)
    }


    private fun readIndex(index: Int, left: Int? = null, right: Int? = null): List<String> {
        val inputReader = content(index) as StringReader
        val wordReader = wordReader(index)
        val combined = wordReader.setReader(inputReader)
        val result = mutableListOf<String>()

        val word = MutableString()
        val nonWord = MutableString()

        val range = if (right != null) 0..right else 0..Int.MAX_VALUE

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