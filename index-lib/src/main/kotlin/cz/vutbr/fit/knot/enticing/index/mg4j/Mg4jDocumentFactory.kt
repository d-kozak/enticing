package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.annotation.Speed
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

        // + 1 for hidden glue index
        val fields = List(indexes.size + 1) { StringBuilder() }

        stream.readLine() // skip doc line
        stream.readLine() // skip page line
        var line = stream.readLine()
        var lineIndex = 0
        loop@ while (line != null) {
            when {
                line.isPage() -> log.error("page tag should never be encountered at this context, only after the start of another document and the stream should not proceed that far")
                line.isDoc() -> break@loop // reach the end of current document
                line.isPar() -> {
                    addSpecialToken("§", fields, lineIndex)
                    lineIndex++
                }
                line.isSent() -> {
                    addSpecialToken("¶", fields, lineIndex)
                    lineIndex++
                }
                !line.isMetaInfo() -> {
                    processLine(line, fields, lineIndex, corpusConfiguration)
                    lineIndex++
                }
                else -> log.error("Unnown meta line $line")
            }
            line = stream.readLine()
        }
        metadata[DocumentMetadata.SIZE] = lineIndex
        return Mg4jDocument(corpusConfiguration, metadata, fields.map { it.toString() })
    }

    private fun addSpecialToken(tokenValue: String, fields: List<StringBuilder>, lineIndex: Int) {
        val tokenIndex = corpusConfiguration.indexes.getValue("token").columnIndex
        for ((i, builder) in fields.withIndex()) {
            if (builder.isNotBlank())
                builder.append(' ')
            builder.append(if (i == tokenIndex) tokenValue else '0')
        }
    }
}

data class EntityReplicationInfo(val elems: List<String>, var lineCount: Int)

val NULL = "null"
val whitespaceRegex = """\s""".toRegex()

var replicationInfo: EntityReplicationInfo? = null

@Speed("rewrite using MutableStrings and whitespace readers?")
internal fun processLine(line: String, fields: List<StringBuilder>, lineIndex: Int, corpusConfiguration: CorpusConfiguration) {
    val cells = line.split(whitespaceRegex)
    val cellCount = 27
    val nerlenIndex = 26
    val firstEntityCell = 13

    val tokenIndex = corpusConfiguration.indexes.getValue("token").columnIndex

    if (cells[tokenIndex].isBlank() || cells[tokenIndex] == glueSymbol) {
        log.warn("$lineIndex: token is blank, skipping... at line '$line'")
        return
    }

    if (cells.size != cellCount) {
        log.warn("$lineIndex: $line does not have correct format, skipping")
        log.warn("It has size ${cells.size}, but it should be 27")
        log.warn("was split into $cells")
        return
    }
    for ((i, cell) in cells.withIndex()) {
        val stringBuilder = fields[i]
        if (stringBuilder.isNotBlank())
            stringBuilder.append(' ')

        if (i == tokenIndex) {
            if (cell.isGlued()) {
                fields.last().run {
                    if (isNotBlank()) {
                        setLength(length - 1) // remove last char
                        append('N')
                        append(' ')
                    }
                    append('P')
                }
            } else {
                fields.last().run {
                    if (isNotBlank())
                        append(' ')
                    append('0')
                }
            }
        }

        val elem = if (cell.isGlued()) cell.removeGlue() else cell

        if (i < firstEntityCell) {
            if (elem.isBlank()) {
                log.warn("$lineIndex:$i is blank, at line '$line'")
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
