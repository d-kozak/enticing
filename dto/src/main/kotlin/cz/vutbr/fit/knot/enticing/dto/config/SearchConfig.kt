package cz.vutbr.fit.knot.enticing.dto.config

import cz.vutbr.fit.knot.enticing.dto.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.TextFormat
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
        var resultFormat: ResultFormat = ResultFormat.SNIPPET,
        var textFormat: TextFormat = TextFormat.STRING_WITH_METADATA,
        @field:NotEmpty
        var defaultIndex: String = "token"
) {

    fun toTemplateQuery(): SearchQuery = SearchQuery(
            query = "",
            snippetCount = snippetCount,
            offset = emptyMap(),
            metadata = metadata,
            defaultIndex = defaultIndex,
            textFormat = textFormat,
            resultFormat = resultFormat
    )
}