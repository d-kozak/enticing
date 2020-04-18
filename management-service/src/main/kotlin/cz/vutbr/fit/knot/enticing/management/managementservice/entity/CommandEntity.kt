package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandState
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandType
import java.time.LocalDateTime
import javax.persistence.*

data class CommandDto(
        var id: Long,
        var type: CommandType,
        var arguments: String,
        var submittedBy: String,
        var submittedAt: LocalDateTime,
        var startAt: LocalDateTime,
        var finishedAt: LocalDateTime
)


@Entity
class CommandEntity(
        @Id
        @GeneratedValue
        var id: Long,
        var type: CommandType,
        var state: CommandState,
        @Column(length = 4096)
        var arguments: String,
        @ManyToOne
        var submittedBy: UserEntity,
        var submittedAt: LocalDateTime = LocalDateTime.now(),
        var startAt: LocalDateTime? = null,
        var finishedAt: LocalDateTime? = null
) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CommandEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}