package cz.vutbr.fit.knot.enticing.dto.config

import cz.vutbr.fit.knot.enticing.dto.ResponseFormat
import cz.vutbr.fit.knot.enticing.dto.ResponseType
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.TextMetadata
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

/**
 * Search config for console client
 */
data class SearchConfig(
        @field:Positive
        var snippetCount: Int = 20,
        var metadata: TextMetadata = TextMetadata.Predefined("all"),
        var responseType: ResponseType = ResponseType.FULL,
        var responseFormat: ResponseFormat = ResponseFormat.ANNOTATED_TEXT,
        @field:NotEmpty
        var defaultIndex: String = "token"
) {

    fun toTemplateQuery(): SearchQuery = SearchQuery(
            query = "",
            snippetCount = snippetCount,
            offset = emptyMap(),
            metadata = metadata,
            defaultIndex = defaultIndex,
            responseFormat = responseFormat,
            responseType = responseType
    )
}