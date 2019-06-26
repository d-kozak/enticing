package cz.vutbr.fit.knot.enticing.query.processor.request

import cz.vutbr.fit.knot.enticing.dto.query.Offset
import cz.vutbr.fit.knot.enticing.dto.response.SearchResult
import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegex
import javax.validation.Valid

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern


data class ServerInfo(
        @field:NotEmpty
        @field:Pattern(regexp = urlRegex)
        val address: String,
        val offset: Offset? = null
)

data class ServerResult(
        @field:NotEmpty
        @field:Pattern(regexp = urlRegex)
        val server: String,
        @field:Valid
        val result: SearchResult?,
        val error: String?
)