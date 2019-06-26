package cz.vutbr.fit.knot.enticing.dto.query

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

// TODO add wantedIndexes

data class SearchQuery(
        @field:NotBlank
        val query: String,
        @field:Positive
        val snippetCount: Int,
        @field:Valid
        val offset: Offset,
        val responseType: ResponseType = ResponseType.SNIPPET,
        val responseFormat: ResponseFormat = ResponseFormat.JSON,
        @field:NotEmpty
        val defaultIndex: String = "token"
)


data class Offset(
        @field:Positive
        val document: Long,
        @field:Positive
        val snippet: Int
)

enum class ResponseType {
    SNIPPET,
    MATCH
}

enum class ResponseFormat {
    HTML,
    JSON;
}
