package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.annotation.Speed
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.Index
import it.unimi.di.big.mg4j.document.AbstractDocumentFactory
import it.unimi.di.big.mg4j.document.DocumentFactory
import it.unimi.dsi.fastutil.bytes.ByteArrays
import it.unimi.dsi.fastutil.io.FastBufferedInputStream
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import org.slf4j.LoggerFactory
import java.io.InputStream

private val log = LoggerFactory.getLogger(Mg4jDocumentFactory::class.java)

class Mg4jDocumentFactory(private val corpusConfiguration: CorpusConfiguration) : AbstractDocumentFactory() {

    internal data class EntityReplicationInfo(val elems: List<String>, val entityType: String, val lineCount: Int)

    private val indexes: List<Index>
        get() = corpusConfiguration.indexes.values.toList()

    override fun numberOfFields(): Int = indexes.size

    override fun fieldName(field: Int): String = indexes[field].name

    override fun fieldIndex(fieldName: String): Int = indexes.find { it.name == fieldName }?.columnIndex
            ?: throw IllegalArgumentException("Unknown field $fieldName")

    override fun fieldType(field: Int): DocumentFactory.FieldType = indexes[field].type.mg4jType

    override fun copy() = Mg4jDocumentFactory(corpusConfiguration)

    override fun getDocument(rawContent: InputStream, metadata: Reference2ObjectMap<Enum<*>, Any>): Mg4jDocument {
        val stream = (rawContent as FastBufferedInputStream).bufferedReader()

        // + 1 for hidden glue index
        val fields = List(indexes.size + 1) { mutableListOf<String>() }

        val invalidLines = mutableSetOf<Int>()

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
                    val (parseSuccess, replicationInfo) = processLine(line, fields, lineIndex, corpusConfiguration)
                    if (!parseSuccess) invalidLines.add(lineIndex)
                    if (parseSuccess && replicationInfo != null) {
                        val lastParsedLineIndex = fields[0].size - 1
                        for (i in lastParsedLineIndex - (replicationInfo.lineCount - 1) until lastParsedLineIndex) {
                            for ((j, elem) in replicationInfo.elems.withIndex()) {
                                fields[corpusConfiguration.firstAttributeIndex!! + j][i] = elem
                            }
                            fields[corpusConfiguration.entityIndex!!][i] = replicationInfo.entityType
                            fields[corpusConfiguration.entityLengthIndex!!][i] = "-1"  // hardwired constant to signal that this entity is replicated
                        }
                        fields[corpusConfiguration.entityLengthIndex!!][lastParsedLineIndex - (replicationInfo.lineCount - 1)] = replicationInfo.lineCount.toString()
                        fields[corpusConfiguration.entityIndex!!][lastParsedLineIndex - (replicationInfo.lineCount - 1)] = replicationInfo.entityType
                    }
                    lineIndex++
                }
                else -> log.error("Unknown meta line $line")
            }
            line = stream.readLine()
        }
        if (invalidLines.isNotEmpty())
            log.warn("Document ${metadata[DocumentMetadata.ID]}:${metadata[DocumentMetadata.TITLE]} has invalid lines: $invalidLines, they were skipped")
        metadata[DocumentMetadata.SIZE] = lineIndex
        return Mg4jDocument(corpusConfiguration, metadata, fields.map { it.joinToString(" ") })
    }

    @Incomplete("'token' is hardwired here")
    private fun addSpecialToken(tokenValue: String, fields: List<MutableList<String>>, lineIndex: Int) {
        val tokenIndex = corpusConfiguration.indexes.getValue("token").columnIndex
        for ((i, list) in fields.withIndex()) {
            list.add(if (i == tokenIndex) tokenValue else "0")
        }
    }
}

private val NULL = "null"
private val whitespaceRegex = """\s""".toRegex()

@Speed("rewrite using MutableStrings and whitespace readers?")
@Cleanup("Should be refactored, it is smelly")
internal fun processLine(line: String, fields: List<MutableList<String>>, lineIndex: Int, corpusConfiguration: CorpusConfiguration): Pair<Boolean, Mg4jDocumentFactory.EntityReplicationInfo?> {
    val cells = line.split(whitespaceRegex)
    val indexCount = corpusConfiguration.indexes.size
    val entityLenIndex = corpusConfiguration.entityLengthIndex
    val tokenIndex = corpusConfiguration.indexes.getValue("token").columnIndex
    val firstEntityIndex = corpusConfiguration.firstAttributeIndex
    val nertagIndex = corpusConfiguration.entityIndex

    if (cells.size != indexCount || cells[tokenIndex].isBlank() || cells[tokenIndex] == glueSymbol1 || cells[tokenIndex] == glueSymbol2) {
        return false to null
    }

    var replicationInfo: Mg4jDocumentFactory.EntityReplicationInfo? = null

    for ((i, cell) in cells.withIndex()) {
        val currentIndexWords = fields[i]
        if (i == tokenIndex) {
            if (cell.isGlued()) {
                fields.last().run {
                    if (isNotEmpty()) {
                        removeAt(size - 1)
                        add("N")
                    }
                    add("P")
                }
            } else {
                fields.last().run {
                    add("0")
                }
            }
        }

        var elem = if (cell.isGlued()) cell.removeGlue() else cell
        if (elem.isBlank()) {
            log.debug("$lineIndex:$i is blank, at line '$line'")
            elem = NULL
        }

        if (i == entityLenIndex && firstEntityIndex != null && nertagIndex != null) {
            val nerlen = elem.toIntOrNull() ?: 0
            if (nerlen != 0) {
                replicationInfo = Mg4jDocumentFactory.EntityReplicationInfo(cells.subList(firstEntityIndex, indexCount), cells[nertagIndex], nerlen)
                currentIndexWords.add("-1")
            } else {
                currentIndexWords.add(elem)
            }
        } else {
            currentIndexWords.add(elem)
        }
    }
    return true to replicationInfo
}

fun ByteArray.growBy(length: Int): ByteArray = ByteArrays.grow(this, this.size + length)

fun ByteArray.next(b: Byte, offset: Int = 0, size: Int = this.size): Int {
    for (i in offset until size) {
        if (this[i] == b)
            return i
    }
    return -1
}

internal fun parseUuid(buffer: ByteArray, bufferSize: Int): String? {
    val splitPoint = findSplitPoint(buffer, bufferSize) ?: return null
    return String(buffer, splitPoint + 1, bufferSize - (splitPoint + 1))
}

internal fun parsePageLine(buffer: ByteArray, bufferSize: Int): Pair<String, String> {
    val splitPoint = findSplitPoint(buffer, bufferSize) ?: return Pair("none", "none")

    val titleStart = DocumentMarks.PAGE.mark.length + 1
    val titleLen = splitPoint - titleStart
    val title = String(buffer, titleStart, titleLen)

    val uriStart = splitPoint + 1
    val uriLen = bufferSize - splitPoint - 1
    val uri = String(buffer, uriStart, uriLen)
    return title to uri
}

private const val tabByte = '\t'.toByte()
internal fun findSplitPoint(buffer: ByteArray, bufferSize: Int): Int? {
    for (i in bufferSize - 1 downTo 0) {
        if (buffer[i] == tabByte) {
            return i
        }
    }
    return null
}
