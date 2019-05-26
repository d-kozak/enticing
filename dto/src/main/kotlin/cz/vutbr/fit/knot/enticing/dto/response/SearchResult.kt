package cz.vutbr.fit.knot.enticing.dto.response

import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegex
import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

sealed class SearchResult(val snippets: List<Snippet>)

data class Snippet(
        @field:NotEmpty
        val host: String,
        @field:NotEmpty
        val collection: String,
        val docId: UUID,
        @field:Positive
        val location: Long,
        @field:Positive
        val size: Long,
        @field:NotEmpty
        @field:Pattern(regexp = urlRegex)
        val url: String,
        val snippet: AnnotatedText,
        val canExtend: Boolean
)