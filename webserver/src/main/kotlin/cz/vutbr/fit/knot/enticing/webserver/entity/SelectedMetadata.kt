package cz.vutbr.fit.knot.enticing.webserver.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*


/**
 * Describes the subset of all corpusFormat metadata that should be returned from index servers
 */
@Entity
class SelectedMetadata(
        @field:JsonIgnore
        @field:Id
        @field:GeneratedValue
        var id: Long = 0,
        @field:ElementCollection(fetch = FetchType.EAGER)
        var indexes: List<String> = emptyList(),
        @field:ElementCollection(fetch = FetchType.EAGER)
        var entities: Map<String, AttributeList> = emptyMap()
) {


    override fun toString(): String {
        return "SelectedMetadata(id=$id, indexes=$indexes, entities=$entities)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SelectedMetadata) return false

        if (indexes != other.indexes) return false
        if (entities != other.entities) return false

        return true
    }

    override fun hashCode(): Int {
        var result = indexes.hashCode()
        result = 31 * result + entities.hashCode()
        return result
    }


}

@Entity
class AttributeList(
        @field:JsonIgnore
        @field:Id
        @field:GeneratedValue
        var id: Long = 0,
        @field:ElementCollection(fetch = FetchType.EAGER)
        var attributes: List<String>
) {
    constructor(vararg attributes: String) : this(attributes = attributes.toList())


    override fun toString(): String {
        return "AttributeList(id=$id, attributes=$attributes)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AttributeList) return false

        if (attributes != other.attributes) return false

        return true
    }

    override fun hashCode(): Int {
        return attributes.hashCode()
    }


}