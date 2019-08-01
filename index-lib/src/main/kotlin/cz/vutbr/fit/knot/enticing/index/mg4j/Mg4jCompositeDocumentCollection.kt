package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import it.unimi.di.big.mg4j.document.AbstractDocumentCollection
import it.unimi.di.big.mg4j.document.Document
import it.unimi.di.big.mg4j.document.DocumentCollection
import it.unimi.di.big.mg4j.document.DocumentFactory
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import java.io.File
import java.io.InputStream


/**
 * Transforms a list of sizes of collections into an array containing highest document indexes in each collection
 */
internal fun initDocumentRanges(limits: List<Long>): Array<Long> {
    val result = Array(limits.size) { 0L }
    for (i in 0 until limits.size) {
        val prev = if (i > 0) result[i - 1] else -1
        result[i] = prev + limits[i]
    }
    return result
}

/**
 * Composite collection that handles multiple @see Mg4jSingleFileDocumentCollection and delegates requests to the appropriate ones based on the index
 */
class Mg4jCompositeDocumentCollection(
        private val corpusConfiguration: CorpusConfiguration,
        private val files: List<File>)
    : AbstractDocumentCollection() {

    private val factory = Mg4jDocumentFactory(corpusConfiguration)

    private val singleFileCollections = files.map { Mg4jSingleFileDocumentCollection(it, factory.copy()) }

    private val documentRanges = initDocumentRanges(singleFileCollections.map { it.size() })

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

    /**
     * Finds SingleFileCollection at which the document is located and computes the local index of that document inside the collection
     */
    private fun findCollection(index: Long): Pair<Mg4jSingleFileDocumentCollection, Long> {
        var insertionPoint = documentRanges.binarySearch(index)
        if (insertionPoint < 0) {
            insertionPoint = (insertionPoint + 1) * -1
        }
        val localIndex = if (insertionPoint == 0) index else index - documentRanges[insertionPoint - 1] - 1
        return singleFileCollections[insertionPoint] to localIndex
    }

    override fun copy(): DocumentCollection = Mg4jCompositeDocumentCollection(corpusConfiguration, files)

    override fun factory(): DocumentFactory = factory

    override fun size(): Long = singleFileCollections.map { it.size() }.sum()

    override fun close() {
        for (collection in singleFileCollections) {
            collection.close()
        }
    }

}