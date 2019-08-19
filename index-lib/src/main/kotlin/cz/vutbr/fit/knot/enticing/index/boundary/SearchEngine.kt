package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.interval.Interval

typealias DocumentId = Int

/**
 * Describes how a document was matched by a query
 */
data class DocumentResult(
        /**
         * Id of the document
         */
        val documentId: DocumentId,
        /**
         * For each index a list of intervals is returned describing which regions of text matched the document
         */
        val intervals: List<List<Interval>>
)

/**
 * Result of a search
 */
data class QueryResult(
        /**
         * Documents that were matched and how
         */
        val results: List<DocumentResult>,
        /**
         * Where to start next, for pagination
         */
        val nextOffset: Int
)

/**
 * Search engine is a component that handles a set of documents and is able to search in them and return their content.
 */
interface SearchEngine {
    /**
     * Search in the documents
     * @param query search query to execute
     * @param offset how many documents should be skipped
     */
    @Incomplete("how to properly signal 'nothing more?'")
    fun search(query: String, documentCount: Int, offset: Int): QueryResult

    fun loadDocument(id: DocumentId): IndexedDocument
}