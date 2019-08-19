package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.QueryMapping
import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import javax.validation.Valid
import javax.validation.constraints.*

object IndexServer {

    /**
     * Query to extend the context of the snippet
     */
    data class ContextExtensionQuery(
            /**
             * Collection into which the snippet belongs
             */
            @field:NotBlank
            val collection: String,
            /**
             * Document into which the snippet belongs
             */
            @field:PositiveOrZero
            val docId: Int,

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
            override val defaultIndex: String = Defaults.defaultIndex,
            /**
             * What should be the format of the response
             */
            override val responseFormat: ResponseFormat = Defaults.responseFormat,

            /**
             * Query, optional
             *
             * if provided, QueryMapping informing about the mapping between the query and the document will
             * be included in the response
             */
            @Incomplete("can be used only when postprocessing is ready")
            val query: String? = null
    ) : Mg4jQuery


    /**
     * Query to get the full content of a document
     */
    data class DocumentQuery(
            /**
             * Collection on the server
             */
            @field:NotBlank
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
            override val defaultIndex: String = Defaults.defaultIndex,

            /**
             * What should be the format of the response
             */
            override val responseFormat: ResponseFormat = Defaults.responseFormat,

            /**
             * Query, optional
             *
             * if provided, QueryMapping informing about the mapping between the query and the document will
             * be included in the response
             */
            @Incomplete("can be used only when postprocessing is ready")
            val query: String? = null
    ) : Mg4jQuery

    /**
     * Full document content returned from an IndexServer
     */
    data class FullDocument(
            @field:NotEmpty
            val title: String,
            @field:NotEmpty
            @field:Pattern(regexp = urlRegexStr)
            val url: String,
            @field:Valid
            val payload: ResultFormat.FullResponse,
            @field:Valid
            val queryMapping: List<QueryMapping> = emptyList()
    ) {
        fun toWebserverFormat(host: String, collection: String, documentId: Int, query: String? = null) = Webserver.FullDocument(host, collection, documentId, title, url, payload, query, queryMapping)
    }

    /**
     * Result of the whole index server
     */
    data class IndexResultList(
            /**
             * List of snippets that matched the query
             */
            @field:Valid
            override val matched: List<Snippet>,
            /**
             * For pagination, where to start next
             *
             */
            @field:Valid
            override val offset: Map<CollectionName, Offset> = emptyMap(),

            /**
             * Errors from collections, if any
             */
            val errors: Map<CollectionName, ErrorMessage> = emptyMap()
    ) : QueryResult<Map<CollectionName, Offset>> {
        override fun createRequest(address: String): RequestData<Map<CollectionName, Offset>> = IndexServerRequestData(address, offset)
    }


    /**
     * Result of a single collection
     */
    data class CollectionResultList(
            /**
             * List of snippets that matched the query
             */
            @field:Valid
            override val matched: List<Snippet>,
            /**
             * For pagination, where to start next
             *
             */
            @field:Valid
            override val offset: Offset? = Offset(0, 0)
    ) : QueryResult<Offset> {
        override fun createRequest(address: String): RequestData<Offset> = CollectionRequestData(address, offset
                ?: Offset(0, 0))
    }

    /**
     * Part of document that was matched by the query
     */
    data class Snippet(
            /**
             * What collection the snippet came from
             */
            @field:NotEmpty
            val collection: String,

            /**
             * What document the snippet came from
             */
            @field:PositiveOrZero
            val documentId: Long,

            /**
             * At which index in the document the snippet starts
             */
            @field:Positive
            val location: Int,

            /**
             * The size of the snippet
             */
            @field:Positive
            val size: Int,

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
            val payload: ResultFormat,

            /**
             * Is it possible to further extend the snippet?
             */
            val canExtend: Boolean
    ) {

        fun withHost(host: String): Webserver.Snippet = Webserver.Snippet(host, collection, documentId, location, size, url, documentTitle, payload, canExtend)
    }

}
