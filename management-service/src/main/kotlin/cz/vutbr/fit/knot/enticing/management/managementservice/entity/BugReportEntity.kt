package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.converter.IntervalConverter
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

/**
 * Contains necessary information to replicate invalid match
 */
@Entity
data class BugReportEntity(
        @field:Id
        @field:GeneratedValue
        var id: Long = 0,
        @field:NotBlank
        var query: String = "",
        var filterOverlaps: Boolean = false,
        @field:NotBlank
        var host: String = "",
        @field:NotBlank
        var collection: String = "",
        @field:Positive
        var documentId: Int = 0,
        @field:NotBlank
        val documentTitle: String,
        @field:Valid
        @field:Convert(converter = IntervalConverter::class)
        var matchInterval: Interval = Interval.empty(),
        var description: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BugReportEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}