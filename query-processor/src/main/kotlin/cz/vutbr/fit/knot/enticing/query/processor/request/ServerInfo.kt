package cz.vutbr.fit.knot.enticing.query.processor.request

import cz.vutbr.fit.knot.enticing.dto.query.Offset
import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegex
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern


data class ServerInfo(
        @field:NotEmpty
        @field:Pattern(regexp = urlRegex)
        val address: String,
        val offset: Offset = Offset(0, 0)
)
