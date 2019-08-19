package cz.vutbr.fit.knot.enticing.dto.format.result

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import cz.vutbr.fit.knot.enticing.dto.format.text.StringWithMetadata
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnitList
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
        JsonSubTypes.Type(ResultFormat.IdentifierList::class, name = "identifiers"),
        JsonSubTypes.Type(ResultFormat.FullResponse::class, name = "full")
)
sealed class ResultFormat {
    /**
     * List of identifiers from the query and their values
     */
    data class IdentifierList(@field:Valid val list: List<Identifier>) : ResultFormat()

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
    sealed class FullResponse : ResultFormat() {
        /**
         * Simple html format
         */
        data class Html(val content: String) : FullResponse()

        /**
         * Text with annotations
         */
        data class Annotated(@field:Valid val content: StringWithMetadata) : FullResponse()

        /**
         * New annotated format
         */
        data class NewAnnotated(@field:Valid val content: TextUnitList) : FullResponse()
    }
}

/**
 * Identifier from the query and it's value
 */
data class Identifier(
        @field:NotBlank
        val identifier: String,
        @field:Valid
        val snippet: ResultFormat.FullResponse
)