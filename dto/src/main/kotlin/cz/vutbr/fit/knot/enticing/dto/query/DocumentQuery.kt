package cz.vutbr.fit.knot.enticing.dto.query

import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

/**
 * Query to get the full content of a document
 */
data class DocumentQuery(
        /**
         * Url of the server owning the document
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
        val metadata: TextMetadata,

        /**
         * What should be the defaultIndex
         */
        @field:NotBlank
        val defaultIndex: String,

        /**
         * Query, optional
         *
         * if provided, QueryMapping informing about the mapping between the query and the document will
         * be included in the response
         */
        val query: String?
)