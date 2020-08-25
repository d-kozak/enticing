package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class BuildEntity(
        @field:Id
        @field:NotBlank
        var name: String = ""
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BuildEntity) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}