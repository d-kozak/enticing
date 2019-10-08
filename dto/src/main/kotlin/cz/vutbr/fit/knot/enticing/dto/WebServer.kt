package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import javax.validation.Valid
import javax.validation.constraints.*

typealias ServerId = String
typealias ErrorMessage = String
typealias CollectionName = String

object WebServer {

    /**
     * Result for SearchQuery
     */
    data class ResultList(
            /**
             * Merged results from all servers
             */
            val searchResults: List<SearchResult>,
            /**
             * Any error messages that came from servers
             */
            val errors: Map<ServerId, ErrorMessage> = emptyMap()
    )

    /**
     * Query to extend the context of the snippet
     */
    data class ContextExtensionQuery(
            /**
             * Url of the index server
             */
            @field:NotBlank
            @field:Pattern(regexp = urlRegexStr)
            val host: String,

            /**
             * Collection into which the snippet belongs
             */
            @field:NotBlank
            val collection: String,
            /**
             * Document into which the snippet belongs
             */
            @field:PositiveOrZero
            val documentId: Int,

            /**
             * Where the original snippet starts
             */
            @field:PositiveOrZero
            val location: Int,

            /**
             * How big the original snippet is
             */
            @field:Positive
            val size: Int,

            /**
             * The number of words by which the snippet should be extended
             */
            @field:Positive
            val extension: Int,
            /**
             * Which metadata to include
             */
            @field:Valid
            val metadata: TextMetadata = Defaults.metadata,
            /**
             * What should be the defaultIndex
             */
            @field:NotBlank
            val defaultIndex: String = Defaults.defaultIndex,
            /**
             * What should be the format of the response
             */
            val textFormat: TextFormat = Defaults.textFormat,

            /**
             * Query, optional
             *
             * if provided, QueryMapping informing about the mapping between the query and the document will
             * be included in the response
             */
            @Incomplete("can be used only when postprocessing is ready")
            val query: String? = null
    ) {
        fun toIndexFormat() = IndexServer.ContextExtensionQuery(collection, documentId, location, size, extension, metadata, defaultIndex, textFormat)
    }


    /**
     * Query to get the full content of a document
     */
    data class DocumentQuery(
            /**
             * Url of the index server
             */
            @field:NotBlank
            @field:Pattern(regexp = urlRegexStr)
            val host: String,
            /**
             * Collection on the server
             */
            @field:NotEmpty
            val collection: String,
            /**
             * Id of the document in the collection
             */
            @field:PositiveOrZero
            val documentId: Int,
            /**
             * Which metadata to include
             */
            @field:Valid
            val metadata: TextMetadata = Defaults.metadata,

            /**
             * What should be the defaultIndex
             */
            @field:NotBlank
            val defaultIndex: String = Defaults.defaultIndex,

            /**
             * What should be the format of the response
             */
            val textFormat: TextFormat = Defaults.textFormat,

            /**
             * Query, optional
             *
             * if provided, QueryMapping informing about the mapping between the query and the document will
             * be included in the response
             */
            val query: String? = null
    ) {
        fun toIndexFormat() = IndexServer.DocumentQuery(collection, documentId, metadata, defaultIndex, textFormat)
    }


    data class RawDocumentRequest(
            /**
             * Url of the index server
             */
            @field:NotBlank
            @field:Pattern(regexp = urlRegexStr)
            val server: String,
            /**
             * Collection on the server
             */
            @field:NotEmpty
            val collection: String,
            /**
             * Id of the document in the collection
             */
            @field:PositiveOrZero
            val documentId: Int,
            val from: Int? = null,
            val to: Int? = null
    )

    /**
     * Full document content returned from an IndexServer
     */
    data class FullDocument(
            /**
             * Url of the index server
             */
            @field:NotBlank
            @field:Pattern(regexp = urlRegexStr)
            val host: String,
            /**
             * Collection on the server
             */
            @field:NotEmpty
            val collection: String,
            /**
             * Id of the document in the collection
             */
            @field:PositiveOrZero
            val documentId: Int,
            @field:NotEmpty
            val title: String,
            @field:NotEmpty
            @field:Pattern(regexp = urlRegexStr)
            val url: String,
            @field:Valid
            val payload: ResultFormat.Snippet,
            /**
             * Query, optional
             *
             * if provided, QueryMapping informing about the mapping between the query and the document will
             * be included in the response
             */
            val query: String? = null
    ) {
        fun toIndexFormat() = IndexServer.FullDocument(title, url, payload)
    }

    /**
     * Part of document that was matched by the query
     */
    data class SearchResult(
            /**
             * Url of the index server
             */
            @field:NotBlank
            @field:Pattern(regexp = urlRegexStr)
            val host: String,

            /**
             * What collection the snippet came from
             */
            @field:NotEmpty
            val collection: String,


            /**
             * What document the snippet came from
             */
            @field:Positive
            val documentId: Int,

            /**
             * Url of the original document
             */
            @field:NotEmpty
            @field:Pattern(regexp = urlRegexStr)
            val url: String,

            /**
             * Title of the original document
             */
            @field:NotBlank
            val documentTitle: String,

            /**
             * The actual snippet
             */
            @field:Valid
            val payload: ResultFormat
    ) {
        fun toIndexServerFormat() = IndexServer.SearchResult(collection, documentId, url, documentTitle, payload)
    }
}