package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

object Webserver {
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
            @field:Positive
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
            val responseFormat: ResponseFormat = Defaults.responseFormat,

            /**
             * Query, optional
             *
             * if provided, QueryMapping informing about the mapping between the query and the document will
             * be included in the response
             */
            val query: String? = null
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
            @field:Positive
            val documentId: Int,
            @field:NotEmpty
            val title: String,
            @field:NotEmpty
            @field:Pattern(regexp = urlRegexStr)
            val url: String,
            @field:Valid
            val payload: Payload.FullResponse,
            /**
             * Query, optional
             *
             * if provided, QueryMapping informing about the mapping between the query and the document will
             * be included in the response
             */
            val query: String? = null,
            @field:Valid
            val queryMapping: List<QueryMapping> = emptyList()
    )

    /**
     * Part of document that was matched by the query
     */
    data class Snippet(
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
            val payload: Payload,

            /**
             * Is it possible to further extend the snippet?
             */
            val canExtend: Boolean
    )
}