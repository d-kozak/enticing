package cz.vutbr.fit.knot.enticing.indexer.mg4j

import it.unimi.di.big.mg4j.document.AbstractDocumentCollection
import it.unimi.di.big.mg4j.document.Document
import it.unimi.di.big.mg4j.document.DocumentCollection
import it.unimi.di.big.mg4j.document.DocumentFactory
import it.unimi.dsi.fastutil.io.FastBufferedInputStream
import it.unimi.dsi.fastutil.longs.LongArrayList
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import it.unimi.dsi.sux4j.util.EliasFanoMonotoneLongBigList
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class Mg4jSingleFileDocumentCollection(
        private val inputFile: File,
        private val factory: DocumentFactory)
    : AbstractDocumentCollection() {

    override fun factory(): DocumentFactory = factory

    override fun copy(): DocumentCollection = Mg4jSingleFileDocumentCollection(inputFile, factory)

    private val documentIndexes = findDocumentIndexes(inputFile)


    override fun metadata(index: Long): Reference2ObjectMap<Enum<*>, Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stream(index: Long): InputStream {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun document(index: Long): Document = factory.getDocument(stream(index), metadata(index))

    override fun size(): Long = documentIndexes.size64()
}


fun findDocumentIndexes(inputFile: File): EliasFanoMonotoneLongBigList {
    val list = LongArrayList()

    FastBufferedInputStream(FileInputStream(inputFile)).use {
        val buffer = ByteArray(1024)
        val docMark = "%%#DOC".toByteArray()

        var byteCount = it.readLine(buffer)
        var lineIndex = 0L
        while (byteCount != -1) {
            if (buffer.starstWith(docMark)) {
                list.add(lineIndex)
            }
            byteCount = it.readLine(buffer)
            lineIndex++
        }
    }

    return EliasFanoMonotoneLongBigList(list)
}

fun ByteArray.starstWith(prefix: ByteArray): Boolean = startsWith(this, prefix)

fun startsWith(buffer: ByteArray, prefix: ByteArray): Boolean {
    for (i in 0 until prefix.size) {
        if (buffer[i] != prefix[i]) return false
    }
    return true
}
