package cz.vutbr.fit.knot.enticing.dto.response

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import cz.vutbr.fit.knot.enticing.dto.query.Offset
import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegex
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

data class SearchResult(
        @field:Valid
        val matched: List<Match>,
        @field:Valid
        val offset: Offset?
)

data class Match(
        @field:NotEmpty
        val collection: String,
        @field:Positive
        val documentId: Long,
        @field:Positive
        val location: Int,
        @field:Positive
        val size: Int,
        @field:NotEmpty
        @field:Pattern(regexp = urlRegex)
        val url: String,
        @field:NotBlank
        val documentTitle: String,
        @field:Valid
        val payload: Payload,
        val canExtend: Boolean
)

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(Payload.IdentifierList::class, name = "identifiers"),
        JsonSubTypes.Type(Payload.Snippet::class, name = "snippet")
)
sealed class Payload {
    data class IdentifierList(@field:Valid val list: List<IdentifierMatch>) : Payload()

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type"
    )
    @JsonSubTypes(
            JsonSubTypes.Type(Snippet.Html::class, name = "html"),
            JsonSubTypes.Type(Snippet.Json::class, name = "json")
    )
    sealed class Snippet : Payload() {
        data class Html(val content: String) : Snippet()
        data class Json(@field:Valid val content: AnnotatedText) : Snippet()
    }
}

data class IdentifierMatch(
        @field:NotBlank
        val identifier: String,
        @field:Valid
        val snippet: Payload.Snippet
)