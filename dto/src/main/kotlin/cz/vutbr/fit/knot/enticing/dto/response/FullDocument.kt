package cz.vutbr.fit.knot.enticing.dto.response

import cz.vutbr.fit.knot.enticing.dto.utils.Extended
import cz.vutbr.fit.knot.enticing.dto.utils.Extension
import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

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
        val payload: Payload.FullResponse,
        @field:Valid
        val queryMapping: List<QueryMapping> = emptyList()
)

/**
 * Information handled by Webserver
 */
data class DocumentExtra(
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
         * Query, optional
         *
         * if provided, QueryMapping informing about the mapping between the query and the document will
         * be included in the response
         */
        val query: String? = null
) : Extension

typealias ExtendedDocument = Extended<FullDocument, DocumentExtra>