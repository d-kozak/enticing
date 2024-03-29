package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import it.unimi.di.big.mg4j.document.AbstractDocumentCollection
import it.unimi.di.big.mg4j.document.DocumentCollection
import it.unimi.di.big.mg4j.document.DocumentFactory
import it.unimi.dsi.fastutil.io.FastBufferedInputStream
import it.unimi.dsi.fastutil.longs.LongArrayList
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import it.unimi.dsi.sux4j.util.EliasFanoMonotoneLongBigList
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

/**
 * Mg4j collection handling one mg4j file ( which usually consists of multiple mg4j documents )
 */
class Mg4jSingleFileDocumentCollection(
        private val inputFile: File,
        private val factory: Mg4jDocumentFactory,
        val loggerFactory: LoggerFactory
) : AbstractDocumentCollection() {

    private val logger = loggerFactory.logger { }

    private val documentIndexes = findDocumentIndexes(inputFile)

    override fun factory(): DocumentFactory = factory

    override fun copy(): DocumentCollection = Mg4jSingleFileDocumentCollection(inputFile, factory, loggerFactory)


    override fun metadata(index: Long): Reference2ObjectMap<Enum<*>, Any> = metadataAndStream(index).second


    override fun stream(index: Long): InputStream = FastBufferedInputStream(FileInputStream(inputFile)).also {
        it.position(documentIndexes.getLong(index))
    }


    fun getRawDocument(index: Long, from: Int = 0, to: Int = Int.MAX_VALUE): String {
        require(from >= 0) { "from >= 0" }
        require(from <= to) { "from <= to" }
        require(to > 0) { "to > 0" }
        val stream = stream(index).bufferedReader()
        return buildString {
            var lineIndex = 1
            if (from == 0) {
                append(stream.readLine())
                append('\n')
            } else stream.readLine()
            if (to == 0) return@buildString

            var line = stream.readLine()
            while (lineIndex < to && line != null && !line.isDoc()) {
                if (lineIndex >= from) {
                    append(line)
                    append('\n')
                }
                line = stream.readLine()
                lineIndex++
            }
        }
    }

    private fun metadataAndStream(index: Long): Pair<FastBufferedInputStream, Reference2ObjectArrayMap<Enum<*>, Any>> {
        val metadata = Reference2ObjectArrayMap<Enum<*>, Any>()
        metadata[DocumentMetadata.ID] = index.toInt()
        val stream = stream(index) as FastBufferedInputStream
        val startPosition = stream.position()


        val buffer = ByteArray(1024)
        var lineSize = stream.readLine(buffer)
        require(lineSize > 0) { "Doc line should not be empty" }
        metadata[DocumentMetadata.UUID] = parseUuid(buffer, lineSize) ?: "NULL"
        lineSize = stream.readLine(buffer)
        require(lineSize > 0) { "Page line should not be empty" }
        require(buffer.isPage()) { "Should be a page line" }


        val (title, uri) = parsePageLine(buffer, lineSize)
        metadata[DocumentMetadata.TITLE] = title
        metadata[DocumentMetadata.URI] = uri

        stream.position(startPosition)
        return stream to metadata
    }


    override fun document(index: Long): Mg4jDocument = metadataAndStream(index).let { (stream, metadata) -> factory.getDocument(stream, metadata) }

    override fun size(): Long = documentIndexes.size64()

    internal fun findDocumentIndexes(inputFile: File): EliasFanoMonotoneLongBigList =
            FastBufferedInputStream(FileInputStream(inputFile)).use { stream ->
                logger.info("Preprocessing file ${inputFile.name}")
                val list = LongArrayList()
                val buffer = ByteArray(1024)


                var byteCount = stream.readLine(buffer)
                while (byteCount != -1) {
                    if (buffer.isDoc()) {
                        list.add(stream.position() - byteCount - 1) // save the position before the current line
                    }
                    byteCount = stream.readLine(buffer)
                }
                EliasFanoMonotoneLongBigList(list)
            }
}







