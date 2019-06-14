package cz.vutbr.fit.knot.enticing.indexer.mg4j

import it.unimi.di.big.mg4j.document.AbstractDocument
import it.unimi.dsi.fastutil.io.FastBufferedInputStream
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import it.unimi.dsi.io.WordReader

val wordReader = WhitespaceWordReader()

class Mg4jDocument(
        private val metadata: Reference2ObjectMap<Enum<*>, Any>,
        private val content: List<ByteArray>
) : AbstractDocument() {

    override fun title(): CharSequence = metadata[DocumentMetadata.TITLE] as CharSequence

    override fun uri(): CharSequence = metadata[DocumentMetadata.URI] as CharSequence

    override fun wordReader(field: Int): WordReader = wordReader

    override fun content(field: Int): Any = FastBufferedInputStream(content[field].inputStream())

}