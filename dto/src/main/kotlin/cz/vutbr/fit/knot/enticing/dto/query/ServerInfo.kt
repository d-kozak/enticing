package cz.vutbr.fit.knot.enticing.dto.query

import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern


data class ServerInfo(
        @field:NotEmpty
        @field:Pattern(regexp = urlRegexStr)
        val address: String,
        val offset: Offset = Offset(0, 0)
)
