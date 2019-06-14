package cz.vutbr.fit.knot.enticing.indexer.mg4j

import cz.vutbr.fit.knot.enticing.index.config.dsl.Index
import it.unimi.di.big.mg4j.document.AbstractDocumentFactory
import it.unimi.di.big.mg4j.document.Document
import it.unimi.di.big.mg4j.document.DocumentFactory
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import java.io.InputStream

class Mg4jDocumentFactory(private val indexes: List<Index>) : AbstractDocumentFactory() {

    override fun numberOfFields(): Int = indexes.size

    override fun fieldName(field: Int): String = indexes[field].name

    override fun fieldIndex(fieldName: String): Int = indexes.find { it.name == fieldName }?.columnIndex
            ?: throw IllegalArgumentException("Unknown field $fieldName")

    override fun fieldType(field: Int): DocumentFactory.FieldType = indexes[field].type.mg4jType

    override fun copy(): DocumentFactory = Mg4jDocumentFactory(indexes)

    override fun getDocument(rawContent: InputStream, metadata: Reference2ObjectMap<Enum<*>, Any>): Document {
        TODO("Not implemented yet")
    }
}

internal fun parsePageLine(buffer: ByteArray, bufferSize: Int): Pair<String, String> {
    val splitPoint = findSplitPoint(buffer, bufferSize)

    val titleStart = DocumentMarks.PAGE.mark.length + 1
    val titleLen = splitPoint - titleStart
    val title = String(buffer, titleStart, titleLen)

    val uriStart = splitPoint + 1
    val uriLen = bufferSize - splitPoint - 1
    val uri = String(buffer, uriStart, uriLen)
    return title to uri
}

private const val tabByte = '\t'.toByte()
internal fun findSplitPoint(buffer: ByteArray, bufferSize: Int): Int {
    for (i in 0 until bufferSize) {
        if (buffer[i] == tabByte) {
            return i
        }
    }
    throw IllegalArgumentException("Cannot find \\t that should separate title and uri")
}
