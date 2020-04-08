package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.log.LogDto
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotEmpty

fun LogDto.toEntity(component: ComponentEntity) = LogEntity(0, className, message, logType, component, timestamp)

fun LogEntity.toDto() = LogDto(classname, message, logType, component.fullAddress, component.type, timestamp)

@Entity
class LogEntity(
        @field:Id
        @field:GeneratedValue
        var id: Long = 0,
        @field:NotEmpty
        var classname: String,
        @field:NotEmpty
        @field:Column(length = 2048)
        var message: String = "",
        var logType: LogType = LogType.INFO,
        @field:ManyToOne
        val component: ComponentEntity,
        var timestamp: LocalDateTime
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
        return "LogEntity(id=$id, classname='$classname', message='$message', logType=$logType, timestamp=$timestamp)"
    }


}