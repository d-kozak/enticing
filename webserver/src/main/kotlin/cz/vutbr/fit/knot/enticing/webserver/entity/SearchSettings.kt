package cz.vutbr.fit.knot.enticing.webserver.entity

import javax.persistence.*
import javax.validation.constraints.NotEmpty


@Entity
class SearchSettings(
        @field:Id
        @field:GeneratedValue
        var id: Long = 0,
        @field:NotEmpty
        @field:Column(unique = true)
        var name: String = "",
        @field:Column(name = "is_default")
        var default: Boolean = false,
        var private: Boolean = true,
        var annotationDataServer: String = "",
        var annotationServer: String = "",
        @field:ElementCollection(fetch = FetchType.EAGER)
        var servers: Set<String> = setOf()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SearchSettings) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "SearchSettings(id=$id, name='$name', default=$default, private=$private, annotationDataServer='$annotationDataServer', annotationServer='$annotationServer', servers=$servers)"
    }


}



