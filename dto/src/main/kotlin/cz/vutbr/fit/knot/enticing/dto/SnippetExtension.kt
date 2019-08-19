package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import javax.validation.Valid

/**
 * Extension for a snippet
 */
@Incomplete("match information missing, but until EQL is not necessary anyways")
data class SnippetExtension(
        /**
         * Text to include before
         */
        @field:Valid
        val prefix: ResultFormat.FullResponse,

        /**
         * Text to include after
         */
        @field:Valid
        val suffix: ResultFormat.FullResponse,
        /**
         * Is it possible to extend further?
         */
        val canExtend: Boolean
)
