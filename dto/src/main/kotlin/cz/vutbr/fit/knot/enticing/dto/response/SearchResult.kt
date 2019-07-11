package cz.vutbr.fit.knot.enticing.dto.response

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import cz.vutbr.fit.knot.enticing.dto.query.Offset
import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

/**
 * Result from an IndexServer
 */
data class SearchResult(
        /**
         * List of snippets that matched the query
         */
        @field:Valid
        val matched: List<Snippet>,
        /**
         * For pagination, where to start next
         *
         * null means that there are no more snippets available
         */
        @field:Valid
        val offset: Offset?
)

/**
 * Part of document that was matched by the query
 */
data class Snippet(
        /**
         * What host the snippet came from
         *
         * This field is set later on in the QueryProcessor, there is no need to set this in the IndexServer
         */
        val host: String?,
        @field:NotEmpty

        /**
         * What collection the snippet came from
         */
        val collection: String,
        @field:Positive

        /**
         * What document the snippet came from
         */
        val documentId: Long,
        @field:Positive
        /**
         * At which index in the document the snippet starts
         */
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

/**
 * The actual data in the snippet
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(Payload.Identifiers::class, name = "identifiers"),
        JsonSubTypes.Type(Payload.FullResponse::class, name = "full")
)
sealed class Payload {
    /**
     * List of identifiers from the query and their values
     */
    data class Identifiers(@field:Valid val list: List<Identifier>) : Payload()

    /**
     * Part of the document that was matched
     */
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type"
    )
    @JsonSubTypes(
            JsonSubTypes.Type(FullResponse.Html::class, name = "html"),
            JsonSubTypes.Type(FullResponse.Annotated::class, name = "annotated")
    )
    sealed class FullResponse : Payload() {
        /**
         * Simple html format
         */
        data class Html(val content: String) : FullResponse()

        /**
         * Text with annotations
         */
        data class Annotated(@field:Valid val content: AnnotatedText) : FullResponse()
    }
}

/**
 * Identifier from the query and it's value
 */
data class Identifier(
        @field:NotBlank
        val identifier: String,
        @field:Valid
        val snippet: Payload.FullResponse
)