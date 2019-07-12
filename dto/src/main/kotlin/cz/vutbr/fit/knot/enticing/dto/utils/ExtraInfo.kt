package cz.vutbr.fit.knot.enticing.dto.utils

import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

/**
 * Extra information used on the interface between frontend and webserver
 */
data class ExtraInfo(
        /**
         * Url of the index server
         */
        @field:NotBlank
        @field:Pattern(regexp = urlRegexStr)
        val host: String
) : Extension
