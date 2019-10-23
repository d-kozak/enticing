package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.annotation.Speed
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.Index
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
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

}

