package cz.vutbr.fit.knot.enticing.indexer.mg4j

import cz.vutbr.fit.knot.enticing.index.config.dsl.Index
import it.unimi.di.big.mg4j.document.AbstractDocumentFactory
import it.unimi.di.big.mg4j.document.Document
import it.unimi.di.big.mg4j.document.DocumentFactory
import it.unimi.dsi.fastutil.bytes.ByteArrayList
import it.unimi.dsi.fastutil.bytes.ByteArrays
import it.unimi.dsi.fastutil.io.FastBufferedInputStream
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
        val stream = rawContent as FastBufferedInputStream

        val buffer = ByteArray(1024)
        val fields = indexes.map { ByteArrayList() }

        stream.readLine(buffer) // skip doc line
        var lineSize = stream.readLine(buffer)
        while (lineSize >= 0 && !buffer.isDoc()) {
            if (!buffer.isMetaInfo()) {
                var start = 0
                var fieldIndex = 0
                while (start >= 0) {
                    val nextTab = buffer.next(tabByte, start, lineSize).let { if (it == -1) lineSize else it }
                    val fieldContent = fields[fieldIndex++]
                    fieldContent.addElements(fieldContent.size, buffer, start, nextTab - start)
                    if (nextTab != lineSize)
                        fieldContent.add(' '.toByte())
                    start = if (nextTab != lineSize) nextTab + 1 else -1
                }
            }
            lineSize = stream.readLine(buffer)
        }

        // todo avoid copying of the content from bytelist to bytearray
        return Mg4jDocument(metadata, fields.map { it.toByteArray() })
    }
}

fun ByteArray.growBy(length: Int): ByteArray = ByteArrays.grow(this, this.size + length)

fun ByteArray.next(b: Byte, offset: Int = 0, size: Int = this.size): Int {
    for (i in offset until size) {
        if (this[i] == b)
            return i
    }
    return -1
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
    for (i in bufferSize - 1 downTo 0) {
        if (buffer[i] == tabByte) {
            return i
        }
    }
    throw IllegalArgumentException("Cannot find \\t that should separate title and uri")
}
