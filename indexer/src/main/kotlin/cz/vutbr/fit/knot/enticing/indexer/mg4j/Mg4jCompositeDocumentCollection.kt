package cz.vutbr.fit.knot.enticing.indexer.mg4j

import cz.vutbr.fit.knot.enticing.index.config.dsl.Index
import it.unimi.di.big.mg4j.document.AbstractDocumentCollection
import it.unimi.di.big.mg4j.document.Document
import it.unimi.di.big.mg4j.document.DocumentCollection
import it.unimi.di.big.mg4j.document.DocumentFactory
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import java.io.File
import java.io.InputStream

class Mg4jCompositeDocumentCollection(
        private val indexes: List<Index>,
        private val files: List<File>)
    : AbstractDocumentCollection() {

    private val factory = Mg4jDocumentFactory(indexes)

    private val singleFileCollections = files.map { Mg4jSingleFileDocumentCollection(it, factory.copy()) }

    private val documentRanges = Array(files.size) { 0L }

    init {
        for (i in 0 until singleFileCollections.size) {
            val prev = if (i > 0) documentRanges[i - 1] else 0
            documentRanges[i] = prev + singleFileCollections.size
        }
    }

    override fun metadata(index: Long): Reference2ObjectMap<Enum<*>, Any> {
        val (collection, localIndex) = findCollection(index)
        return collection.metadata(localIndex)
    }

    override fun stream(index: Long): InputStream {
        val (collection, localIndex) = findCollection(index)
        return collection.stream(localIndex)
    }

    override fun document(index: Long): Document {
        val (collection, localIndex) = findCollection(index)
        return collection.document(localIndex)
    }

    private fun findCollection(index: Long): Pair<Mg4jSingleFileDocumentCollection, Long> {
        var insertionPoint = documentRanges.binarySearch(index)
        if (insertionPoint < 0) {
            insertionPoint = (insertionPoint + 1) * -1
        }
        val localIndex = if (insertionPoint == 0) index else index - documentRanges[insertionPoint - 1]
        return singleFileCollections[insertionPoint] to localIndex
    }

    override fun copy(): DocumentCollection = Mg4jCompositeDocumentCollection(indexes, files)

    override fun factory(): DocumentFactory = factory

    override fun size(): Long = singleFileCollections.map { it.size() }.sum()

    override fun close() {
        for (collection in singleFileCollections) {
            collection.close()
        }
    }

}