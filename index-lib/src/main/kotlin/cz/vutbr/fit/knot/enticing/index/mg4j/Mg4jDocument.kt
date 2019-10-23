package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.annotation.Speed
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.Index
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.DocumentElement
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.StructuredDocumentContent
import it.unimi.di.big.mg4j.document.AbstractDocument
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import it.unimi.dsi.io.WordReader
import it.unimi.dsi.lang.MutableString
import it.unimi.dsi.util.Interval
import java.io.StringReader
import kotlin.math.max
import kotlin.math.min

internal val wordReader = WhitespaceWordReader()

/**
 * Keys for accessing document metadata
 */
enum class DocumentMetadata {
    ID,
    UUID,
    TITLE,
    URI,
    SIZE;
}

/**
 * Represents one single document, the content is currently preloaded when the document instance is created
 */
class Mg4jDocument(
        private val corpusConfiguration: CorpusConfiguration,
        internal val metadata: Reference2ObjectMap<Enum<*>, Any>,
        override val content: List<List<String>>
) : AbstractDocument(), IndexedDocument {

    init {
        val sizeSet = content.map { it.size }.toSet()
        require(sizeSet.size == 1) { "broken invariant, all indexes should have the same len: $sizeSet" }
    }

    override val id: Int
        get() = metadata[DocumentMetadata.ID] as Int

    override val uuid: String
        get() = metadata[DocumentMetadata.UUID] as String

    override val title: String
        get() = metadata[DocumentMetadata.TITLE] as String

    override val uri: String
        get() = metadata[DocumentMetadata.URI] as String

    override val size: Int
        get() = metadata[DocumentMetadata.SIZE] as Int

    private val indexes: List<Index> = corpusConfiguration.indexes.values.toList()

    override fun title(): CharSequence = title

    override fun uri(): CharSequence = uri

    fun size() = size

    override fun wordReader(field: Int): WordReader = wordReader

    private val contentMemo = mutableMapOf<Int, Any>()

    override fun content(field: Int): Any = contentMemo.computeIfAbsent(field) { i -> content[i].joinToString(" ").reader() }


    /**
     * Loads SnippetPartsFields from part of the document
     *
     * @param interval interval which should be loaded
     */
    fun loadStructuredContent(interval: Interval, filteredConfig: CorpusConfiguration): StructuredDocumentContent = loadStructuredContent(interval.left, interval.right, filteredConfig)

    /**
     * Loads SnippetPartsFields from part of the document
     *
     * @param left left limit, inclusive
     * @param right right limit, inclusive
     */
    @Speed("Could be rewritten using a set of mutable strings and advancing over them... but the speed is actually not bad right now :)")
    fun loadStructuredContent(left: Int = 0, _right: Int = -1, filteredConfig: CorpusConfiguration): StructuredDocumentContent {
        val right = if (_right == -1) size() else _right
        val indexContent = indexes.asSequence()
                .map { it.name to readIndex(it.columnIndex, left, right) }
                .toMap()

        val loadedDataSize = indexContent.getValue("token").size
        fun collectIndexValuesAt(i: Int): List<String> = indexes.map { indexContent.getValue(it.name)[i] }

        val result = mutableListOf<DocumentElement>()
        var i = 0
        val limit = min(right - left, loadedDataSize)
        while (i < limit) {
            val entityClass = indexContent.getValue(filteredConfig.entityMapping.entityIndex)[i]
            if (entityClass != "0") {
                val entityInfo: List<String> = filteredConfig.entities[entityClass]?.let { entity ->
                    entity.attributes.values.map { indexContent.getValue(it.correspondingIndex)[i] }
                } ?: listOf()

                val nerlen = max(indexContent.getValue("nerlength")[i].toIntOrNull() ?: 1, 1)
                val words = (i until min(i + nerlen, loadedDataSize))
                        .map { DocumentElement.Word(left + it, collectIndexValuesAt(it)) }
                result.add(DocumentElement.Entity(left + i, entityClass, entityInfo, words))
                i += nerlen

            } else {
                result.add(DocumentElement.Word(left + i, collectIndexValuesAt(i)))
                i++
            }
        }
        return StructuredDocumentContent(result, filteredConfig)
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

