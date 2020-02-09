package cz.vutbr.fit.knot.enticing.index.mg4j


import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import it.unimi.di.big.mg4j.document.AbstractDocumentCollection
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
    for (i in limits.indices) {
        val prev = if (i > 0) result[i - 1] else -1
        result[i] = prev + limits[i]
    }
    return result
}

/**
 * Composite collection that handles multiple @see Mg4jSingleFileDocumentCollection and delegates requests to the appropriate ones based on the index
 */
class Mg4jCompositeDocumentCollection(
        private val metadataConfiguration: MetadataConfiguration,
        private val files: List<File>)
    : AbstractDocumentCollection() {

    private val factory = Mg4jDocumentFactory(metadataConfiguration)

    private val singleFileCollections = files.map { Mg4jSingleFileDocumentCollection(it, factory.copy()) }

    private val documentRanges = initDocumentRanges(singleFileCollections.map { it.size() })

    override fun metadata(index: Long): Reference2ObjectMap<Enum<*>, Any> {
        val (collection, localIndex) = findCollection(index)
        return collection.metadata(localIndex)
    }

    fun getRawDocument(index: Long, from: Int = 0, to: Int = Int.MAX_VALUE): String {
        val (collection, localIndex) = findCollection(index)
        return collection.getRawDocument(localIndex, from, to)
    }

    override fun stream(index: Long): InputStream {
        val (collection, localIndex) = findCollection(index)
        return collection.stream(localIndex)
    }

    override fun document(index: Long): Mg4jDocument {
        val (collection, localIndex) = findCollection(index)
        val document = collection.document(localIndex)
        document.metadata[DocumentMetadata.ID] = index.toInt() // the single file collections set incorrect local id, has to be updated to the global one
        return document
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

    override fun copy(): DocumentCollection = Mg4jCompositeDocumentCollection(metadataConfiguration, files)

    override fun factory(): DocumentFactory = factory

    override fun size(): Long = singleFileCollections.map { it.size() }.sum()

    override fun close() {
        for (collection in singleFileCollections) {
            collection.close()
        }
    }

}