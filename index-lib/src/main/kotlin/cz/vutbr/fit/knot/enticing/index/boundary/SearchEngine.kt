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
         * How many documents in total are matching the query (includes really all the documents, even the ones that were skipped because of offset
         * or the ones that were not used (yet) because of result size limit. MG4J gives this number even if we don't ask and it can at least be used to show
         * back in the UI how many documents might satisfy the query (before evaluating global constraints).
         */
        val relevantDocuments: Int
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

    fun getRawDocument(id: DocumentId, from: Int, to: Int): String

    fun loadDocument(id: DocumentId): IndexedDocument
}