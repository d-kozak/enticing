package cz.vutbr.fit.knot.enticing.webserver.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

val defaultSelectedMetadata = SelectedMetadata(indexes = listOf("token"))

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
        var entities: Map<String, EntityMetadata> = emptyMap(),
        var defaultIndex: String = "token"
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SelectedMetadata) return false

        if (indexes != other.indexes) return false
        if (entities != other.entities) return false
        if (defaultIndex != other.defaultIndex) return false

        return true
    }

    override fun hashCode(): Int {
        var result = indexes.hashCode()
        result = 31 * result + entities.hashCode()
        result = 31 * result + defaultIndex.hashCode()
        return result
    }

    override fun toString(): String {
        return "SelectedMetadata(id=$id, indexes=$indexes, entities=$entities, defaultIndex='$defaultIndex')"
    }
}

@Entity
class EntityMetadata(
        @field:JsonIgnore
        @field:Id
        @field:GeneratedValue
        var id: Long = 0,
        @field:ElementCollection(fetch = FetchType.EAGER)
        var attributes: List<String>,
        var color: String = "9900EF"
) {
    constructor(vararg attributes: String) : this(attributes = attributes.toList())


    override fun toString(): String {
        return "AttributeList(id=$id, attributes=$attributes)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EntityMetadata) return false

        if (attributes != other.attributes) return false

        return true
    }

    override fun hashCode(): Int {
        return attributes.hashCode()
    }


}