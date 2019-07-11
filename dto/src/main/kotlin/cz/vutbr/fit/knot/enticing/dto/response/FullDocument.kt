package cz.vutbr.fit.knot.enticing.dto.response

import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

data class FullDocument(
        val id: UUID,
        @field:NotEmpty
        val title: String,
        @field:NotEmpty
        @field:Pattern(regexp = urlRegexStr)
        val url: String,
        val body: AnnotatedText
)