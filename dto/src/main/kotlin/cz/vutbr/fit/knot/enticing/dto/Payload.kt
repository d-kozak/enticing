package cz.vutbr.fit.knot.enticing.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import javax.validation.Valid
import javax.validation.constraints.NotBlank


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
            JsonSubTypes.Type(FullResponse.Annotated::class, name = "annotated"),
            JsonSubTypes.Type(FullResponse.NewAnnotated::class, name = "new")
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

        /**
         * New annotated format
         */
        data class NewAnnotated(@field:Valid val content: NewAnnotatedText) : FullResponse()
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