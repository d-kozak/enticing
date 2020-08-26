package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.dto.Status
import javax.persistence.*

@Entity
data class CorpusEntity(
        @field:Id
        @field:GeneratedValue
        var id: Long,
        @Column(unique = true)
        var name: String,
        var status: Status,
        /**
         * Since it is a set, be careful to set componentEntity ids before inserting
         */
        @ManyToMany(fetch = FetchType.EAGER)
        var components: MutableSet<ComponentEntity>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CorpusEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}