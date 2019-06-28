package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.index.config.dsl.Index
import it.unimi.di.big.mg4j.document.AbstractDocumentFactory
import it.unimi.di.big.mg4j.document.Document
import it.unimi.di.big.mg4j.document.DocumentFactory
import it.unimi.dsi.fastutil.bytes.ByteArrayList
import it.unimi.dsi.fastutil.bytes.ByteArrays
import it.unimi.dsi.fastutil.io.FastBufferedInputStream
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import org.slf4j.LoggerFactory
import java.io.InputStream

private val log = LoggerFactory.getLogger(Mg4jDocumentFactory::class.java)

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
        var lineIndex = 0
        while (lineSize >= 0 && !buffer.isDoc()) {
            if (!buffer.isMetaInfo()) {
                processLine(buffer, fields, lineSize, lineIndex, indexes)
            }
            lineSize = stream.readLine(buffer)
            lineIndex++
        }

        // todo avoid copying of the content from bytelist to bytearray
        return Mg4jDocument(indexes, metadata, fields.map { it.toByteArray() })
    }
}

data class EntityReplicationInfo(val buffer: ByteArray, var lineCount: Int, var startAt: Int?)

val NULL = "null".toByteArray()

internal fun processLine(buffer: ByteArray, fields: List<ByteArrayList>, lineSize: Int, lineIndex: Int, indexes: List<Index>) {
    var start = 0
    var fieldIndex = 0

    var replicationInfo: EntityReplicationInfo? = null

    var columnIndex = 0
    while (start >= 0) {
        val lineBuffer = if (columnIndex > 12 && replicationInfo != null) {
            if (--replicationInfo.lineCount > 0) {
                if (replicationInfo.startAt != null) {
                    start = replicationInfo.startAt!!
                    replicationInfo.startAt = null
                }
                replicationInfo.buffer.also {
                    replicationInfo = null
                }
            } else buffer
        } else buffer

        val nextTab = lineBuffer.next(tabByte, start, lineSize).let { if (it == -1) lineSize else it }
        val fieldContent = fields[fieldIndex++]
        if (fieldContent.isNotEmpty())
            fieldContent.add(' '.toByte())
        if (lineBuffer[start].toChar().isWhitespace()) {
            log.warn("Empty value at line $lineIndex for column $columnIndex")
            fieldContent.addElements(fieldContent.size, NULL)
        } else {
            fieldContent.addElements(fieldContent.size, lineBuffer, start, nextTab - start)
        }
        if (columnIndex == 26) {
//            val nerlen = lineBuffer.inputStream()
//                    .bufferedReader()
//                    .readText()
//                    .substring(start, nextTab - start)
//                    .toIntOrNull() ?: 0
//            if (nerlen != 0) {
//                if(replicationInfo != null){
//                    log.warn("replication info is already set, it will be replaced")
//                }
//                replicationInfo = EntityReplicationInfo(lineBuffer,nerlen,start)
//            }
        }
        start = if (nextTab != lineSize) nextTab + 1 else -1
        columnIndex++
    }
    if (columnIndex != indexes.size) {
        val lineContent = buffer.inputStream().bufferedReader().readText().substring(0, lineSize)
        System.err.println("LineContent: $lineContent")
        throw IllegalArgumentException("Invalid number of columns processed at line $lineIndex, real $columnIndex, expected ${indexes.size}")
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
