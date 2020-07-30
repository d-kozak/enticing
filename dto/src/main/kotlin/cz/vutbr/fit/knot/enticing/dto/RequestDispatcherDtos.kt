package cz.vutbr.fit.knot.enticing.dto

/**
 * Contains information about what is requested in the response
 */
interface GeneralFormatInfo {
    val defaultIndex: String
    val textFormat: TextFormat
    val metadata: TextMetadata
}

/**
 * Base interface for representing a query
 */
interface Query<T:Query<T>> {
    /**
     * The actual EQL query
     */
    val query: String

    /**
     * How many snippets should be returned
     */
    val snippetCount: Int

    /**
     * Creates a new verion of the query object with updated snippet count
     */
    fun updateSnippetCount(newSnippetCount: Int): T
}

/**
 * Base interface for representing results returned from an index server
 */
interface QueryResult<OffsetType> {
    /**
     * The actual results
     */
    val searchResults: List<IndexServer.SearchResult>

    /**
     * Offset for the next query
     */
    val offset: OffsetType?

    /**
     * Creates a request for the 'next page' according the the offset
     */
    fun createRequest(address: String): RequestData<OffsetType>
}