package cz.vutbr.fit.knot.enticing.webserver.entity

import javax.persistence.Embeddable
import javax.validation.constraints.Max
import javax.validation.constraints.Positive

@Embeddable
class UserSettings(
        @field:Positive
        @field:Max(50)
        var resultsPerPage: Int = 20
) {
    override fun toString(): String {
        return "UserSettings(resultsPerPage=$resultsPerPage)"
    }

    override fun equals(other: Any?): Boolean = throw UnsupportedOperationException("This is just embedded data, cannot be tested for equality without the parent user entity")

    override fun hashCode(): Int = throw UnsupportedOperationException("This is just embedded data, cannot be stored in a collection without the parent user entity")
}