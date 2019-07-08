package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.Index
import it.unimi.di.big.mg4j.document.AbstractDocumentFactory
import it.unimi.di.big.mg4j.document.Document
import it.unimi.di.big.mg4j.document.DocumentFactory
import it.unimi.dsi.fastutil.bytes.ByteArrays
import it.unimi.dsi.fastutil.io.FastBufferedInputStream
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import org.slf4j.LoggerFactory
import java.io.InputStream

private val log = LoggerFactory.getLogger(Mg4jDocumentFactory::class.java)

class Mg4jDocumentFactory(private val corpusConfiguration: CorpusConfiguration) : AbstractDocumentFactory() {


    private val indexes: List<Index>
        get() = corpusConfiguration.indexes.values.toList()

    override fun numberOfFields(): Int = indexes.size

    override fun fieldName(field: Int): String = indexes[field].name

    override fun fieldIndex(fieldName: String): Int = indexes.find { it.name == fieldName }?.columnIndex
            ?: throw IllegalArgumentException("Unknown field $fieldName")

    override fun fieldType(field: Int): DocumentFactory.FieldType = indexes[field].type.mg4jType

    override fun copy(): DocumentFactory = Mg4jDocumentFactory(corpusConfiguration)

    override fun getDocument(rawContent: InputStream, metadata: Reference2ObjectMap<Enum<*>, Any>): Document {
        val stream = (rawContent as FastBufferedInputStream).bufferedReader()

        val fields = indexes.map { StringBuilder() }

        stream.readLine() // skip doc line
        var line = stream.readLine()
        var lineIndex = 0
        while (line != null && !line.isDoc()) {
            if (!line.isMetaInfo()) {
                processLine(line, fields, lineIndex)
            }
            line = stream.readLine()
            lineIndex++
        }

        metadata[DocumentMetadata.SIZE] = lineIndex
        // todo avoid copying of the content from bytelist to bytearray
        return Mg4jDocument(corpusConfiguration, metadata, fields.map { it.toString() })
    }
}

data class EntityReplicationInfo(val elems: List<String>, var lineCount: Int)

val NULL = "null"
val whitespaceRegex = """\s""".toRegex()

var replicationInfo: EntityReplicationInfo? = null

internal fun processLine(line: String, fields: List<StringBuilder>, lineIndex: Int) {
    // todo @Speed rewrite using MutableStrings and whitespace readers?
    val cells = line.split(whitespaceRegex)
    val cellCount = 27
    val nerlenIndex = 26
    val firstEntityCell = 13



    if (cells.size != cellCount) {
        log.warn("$lineIndex: $line does not have correct format, skipping")
        log.warn("It has size ${cells.size}, but it should be 27")
        log.warn("was split into $cells")
        return
    }
    for ((i, elem) in cells.withIndex()) {
        val stringBuilder = fields[i]
        if (stringBuilder.isNotBlank())
            stringBuilder.append(' ')

        if (i < firstEntityCell) {
            if (elem.isBlank()) {
                log.warn("$lineIndex:$i is blank")
                stringBuilder.append(NULL)
            } else {
                stringBuilder.append(elem)
            }
        } else if (replicationInfo != null) {
            if (i != cellCount - 1) {
                stringBuilder.append(replicationInfo!!.elems[i - firstEntityCell])
            } else {
                stringBuilder.append('0')
                replicationInfo!!.lineCount--
                if (replicationInfo!!.lineCount == 0) {
                    replicationInfo = null
                }
            }
        } else if (i == nerlenIndex) {
            val nerlen = elem.toIntOrNull() ?: 0
            if (nerlen != 0) {
                replicationInfo = EntityReplicationInfo(cells.subList(firstEntityCell, cellCount), nerlen)
            }
            stringBuilder.append(elem)
        } else {
            stringBuilder.append(elem)
        }
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
