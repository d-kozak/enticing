package cz.vutbr.fit.knot.enticing.index.mg4j

import it.unimi.di.big.mg4j.document.AbstractDocumentCollection
import it.unimi.di.big.mg4j.document.Document
import it.unimi.di.big.mg4j.document.DocumentCollection
import it.unimi.di.big.mg4j.document.DocumentFactory
import it.unimi.dsi.fastutil.io.FastBufferedInputStream
import it.unimi.dsi.fastutil.longs.LongArrayList
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import it.unimi.dsi.sux4j.util.EliasFanoMonotoneLongBigList
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

private val log = LoggerFactory.getLogger(Mg4jSingleFileDocumentCollection::class.java)

/**
 * Mg4j collection handling one mg4j file ( which usually consists of multiple mg4j documents )
 */
class Mg4jSingleFileDocumentCollection(
        private val inputFile: File,
        private val factory: DocumentFactory)
    : AbstractDocumentCollection() {

    private val documentIndexes = findDocumentIndexes(inputFile)

    override fun factory(): DocumentFactory = factory

    override fun copy(): DocumentCollection = Mg4jSingleFileDocumentCollection(inputFile, factory)


    override fun metadata(index: Long): Reference2ObjectMap<Enum<*>, Any> = metadataAndStream(index).second


    private var last: FastBufferedInputStream? = null
    override fun stream(index: Long): InputStream {
        if (last != null) {
            last!!.close()
        }
        last = FastBufferedInputStream(FileInputStream(inputFile)).also {
            it.position(documentIndexes.getLong(index))
        }
        return last!!
    }

    private fun metadataAndStream(index: Long): Pair<FastBufferedInputStream, Reference2ObjectArrayMap<Enum<*>, Any>> {
        val map = Reference2ObjectArrayMap<Enum<*>, Any>()
        val stream = stream(index) as FastBufferedInputStream
        val startPosition = stream.position()


        val buffer = ByteArray(1024)
        var lineSize = stream.readLine(buffer)
        require(lineSize > 0) { "Doc line should not be empty" }
        lineSize = stream.readLine(buffer)
        require(lineSize > 0) { "Page line should not be empty" }
        require(buffer.isPage()) { "Should be a page line" }


        val (title, uri) = parsePageLine(buffer, lineSize)
        map[DocumentMetadata.TITLE] = title
        map[DocumentMetadata.URI] = uri

        stream.position(startPosition)
        return stream to map
    }

    override fun document(index: Long): Document = metadataAndStream(index).let { (stream, metadata) -> factory.getDocument(stream, metadata) }

    override fun size(): Long = documentIndexes.size64()

    override fun close() {
        super.close()
        if (last != null)
            last!!.close()
    }
}

internal fun findDocumentIndexes(inputFile: File): EliasFanoMonotoneLongBigList =
        FastBufferedInputStream(FileInputStream(inputFile)).use { stream ->
            log.info("Preprocessing file ${inputFile.name}")
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





