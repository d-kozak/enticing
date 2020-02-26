package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LogMessage
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

fun LogMessage.toEntity() = LogEntity(0, text, logType, source, componentType, timestamp)

fun LogEntity.toDto() = LogMessage(text, logType, source, componentType, timestamp)

@Entity
class LogEntity(
        @field:Id
        @field:GeneratedValue
        var id: Long = 0,
        @field:NotEmpty
        var text: String = "",
        var logType: LogType = LogType.INFO,
        @field:NotEmpty
        val source: String,
        val componentType: ComponentType,
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
        return "LogEntity(id=$id, text='$text', logType=$logType, source='$source', componentType=$componentType, timestamp=$timestamp)"
    }


}