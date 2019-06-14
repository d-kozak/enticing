package cz.vutbr.fit.knot.enticing.indexer.mg4j

import it.unimi.di.big.mg4j.document.AbstractDocument
import it.unimi.dsi.fastutil.io.FastBufferedInputStream
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap
import it.unimi.dsi.io.WordReader

val wordReader = WhitespaceWordReader()

class Mg4jDocument(
        private val metadata: Reference2ObjectArrayMap<Enum<*>, Any>,
        private val content: Array<ByteArray>
) : AbstractDocument() {

    override fun title(): CharSequence = metadata[DocumentMetaData.TITLE] as CharSequence

    override fun uri(): CharSequence = metadata[DocumentMetaData.URI] as CharSequence

    override fun wordReader(field: Int): WordReader = wordReader

    override fun content(field: Int): Any = FastBufferedInputStream(content[field].inputStream())

}