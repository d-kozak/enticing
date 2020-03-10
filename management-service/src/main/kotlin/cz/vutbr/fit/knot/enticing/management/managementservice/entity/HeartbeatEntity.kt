package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.HeartbeatDto
import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotEmpty

fun HeartbeatDto.toEntity() = HeartbeatEntity(fullAddress, componentType, timestamp)

fun HeartbeatEntity.toDto(isAlive: Boolean, status: ServerStatus?) = HeartbeatDto(componentId, componentType, status, timestamp, isAlive)

@Entity
class HeartbeatEntity(
        @field:Id
        @field:NotEmpty
        val componentId: String,
        val componentType: ComponentType,
        var timestamp: LocalDateTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HeartbeatEntity) return false

        if (componentId != other.componentId) return false

        return true
    }

    override fun hashCode(): Int {
        return componentId.hashCode()
    }

    override fun toString(): String {
        return "HearbBeatEntity(componentId='$componentId', componentType=$componentType, timeStamp=$timestamp)"
    }

}