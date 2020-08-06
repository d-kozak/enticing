package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

/**
 * Contains necessary information to replicate invalid match
 */
data class BugReport(
        @field:NotBlank
        val query: String,
        val filterOverlaps: Boolean,
        @field:NotBlank
        val host: String,
        @field:NotBlank
        val collection: String,
        @field:Positive
        val documentId: Int,
        @field:NotBlank
        val documentTitle: String,
        @field:Valid
        val matchInterval: Interval,
        val description: String
)