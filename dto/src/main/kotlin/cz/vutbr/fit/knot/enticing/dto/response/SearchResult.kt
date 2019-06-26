package cz.vutbr.fit.knot.enticing.dto.response

import cz.vutbr.fit.knot.enticing.dto.query.Offset
import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegex
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

data class SearchResult(
        val snippets: List<Snippet>,
        @field:Valid
        val offset: Offset
)

// todo add annotations

data class Snippet(
        @field:NotEmpty
        val collection: String,
        @field:Positive
        val document: Long,
        @field:Positive
        val location: Long,
        @field:Positive
        val size: Long,
        @field:NotEmpty
        @field:Pattern(regexp = urlRegex)
        val url: String,
        val snippet: String,
        val canExtend: Boolean
)