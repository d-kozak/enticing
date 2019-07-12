package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.Offset
import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern


data class ServerInfo(
        @field:NotEmpty
        @field:Pattern(regexp = urlRegexStr)
        val address: String,
        @field:Valid
        val offset: Offset = Offset(0, 0)
)
