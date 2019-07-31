package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern


data class ServerInfo(
        @field:NotEmpty
        @field:Pattern(regexp = urlRegexStr)
        override val address: String,
        @field:Valid
        override val offset: Map<String,Offset> = emptyMap()
) : RequestData
