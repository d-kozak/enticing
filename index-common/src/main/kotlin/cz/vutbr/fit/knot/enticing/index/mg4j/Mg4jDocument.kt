package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.index.config.dsl.Index
import it.unimi.di.big.mg4j.document.AbstractDocument
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import it.unimi.dsi.io.WordReader

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

    val contentPerIndex: Map<String, List<String>> by lazy(LazyThreadSafetyMode.NONE) {
        indexes.map {
            it.name to content[it.columnIndex].inputStream().bufferedReader().readText().split(whitespaceRegex)
        }.toMap()
    }
}