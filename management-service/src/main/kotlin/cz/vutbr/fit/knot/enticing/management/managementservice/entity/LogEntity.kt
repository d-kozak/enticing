package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.log.LogMessage
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

fun LogMessage.toEntity() = LogEntity(
        id = 0, text = text, type = type, timestamp = timestamp
)

fun LogEntity.toDto() = LogMessage(type, text, timestamp)

@Entity
class LogEntity(
        @field:Id
        @field:GeneratedValue
        var id: Long = 0,
        @field:NotEmpty
        var text: String = "",
        var type: LogType = LogType.INFO,
        @field:Positive
        var timestamp: Long = System.currentTimeMillis()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LogEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "LogEntity(id=$id, text='$text', type=$type)"
    }
}