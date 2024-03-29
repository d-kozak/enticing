package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandState
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandType
import java.time.LocalDateTime
import javax.persistence.*

data class CommandDto(
        var id: Long,
        var type: CommandType,
        var state: CommandState,
        var arguments: String,
        var submittedBy: String,
        var submittedAt: LocalDateTime,
        var startAt: LocalDateTime?,
        var finishedAt: LocalDateTime?
)

fun CommandEntity.toDto() = CommandDto(id, type, state, arguments, submittedBy.login, submittedAt, startAt, finishedAt)

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

    override fun toString(): String {
        return "CommandEntity(id=$id, type=$type, state=$state, arguments='$arguments', submittedBy=${submittedBy.login}, submittedAt=$submittedAt, startAt=$startAt, finishedAt=$finishedAt)"
    }
}