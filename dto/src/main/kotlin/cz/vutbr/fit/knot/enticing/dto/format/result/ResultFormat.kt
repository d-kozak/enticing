package cz.vutbr.fit.knot.enticing.dto.format.result

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
        JsonSubTypes.Type(ResultFormat.IdentifierList::class, name = "identifiers"),
        JsonSubTypes.Type(ResultFormat.Snippet::class, name = "full")
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
            JsonSubTypes.Type(Snippet.PlainText::class, name = "plain"),
            JsonSubTypes.Type(Snippet.Html::class, name = "html"),
            JsonSubTypes.Type(Snippet.StringWithMetadata::class, name = "annotated"),
            JsonSubTypes.Type(Snippet.TextUnitList::class, name = "new")
    )
    sealed class Snippet : ResultFormat() {
        /**
         * Just the text from the default index
         */
        data class PlainText(val content: String) : Snippet()
        /**
         * Simple html format
         */
        data class Html(val content: String) : Snippet()

        /**
         * Text with annotations
         */
        data class StringWithMetadata(@field:Valid val content: cz.vutbr.fit.knot.enticing.dto.format.text.StringWithMetadata) : Snippet()

        /**
         * New annotated format
         */
        data class TextUnitList(@field:Valid val content: cz.vutbr.fit.knot.enticing.dto.format.text.TextUnitList) : Snippet()
    }
}

/**
 * Identifier from the query and it's value
 */
data class Identifier(
        @field:NotBlank
        val identifier: String,
        @field:Valid
        val snippet: ResultFormat.Snippet
)