package cz.vutbr.fit.knot.enticing.webserver.entity

import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import cz.vutbr.fit.knot.enticing.webserver.entity.ipaddress.IpAddressCollection
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern


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
        @field:NotEmpty
        @field:Pattern(regexp = urlRegexStr)
        var annotationDataServer: String = "",
        @field:NotEmpty
        @field:Pattern(regexp = urlRegexStr)
        var annotationServer: String = "",
        @field:ElementCollection(fetch = FetchType.EAGER)
        @field:NotEmpty
        @field:IpAddressCollection
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



