package cz.vutbr.fit.knot.enticing.dto.query

import cz.vutbr.fit.knot.enticing.dto.utils.Defaults
import cz.vutbr.fit.knot.enticing.dto.utils.Extended
import cz.vutbr.fit.knot.enticing.dto.utils.ExtraInfo
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

/**
 * Query to get the full content of a document
 */
data class DocumentQuery(
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


typealias ExtendedDocumentQuery = Extended<DocumentQuery, ExtraInfo>
