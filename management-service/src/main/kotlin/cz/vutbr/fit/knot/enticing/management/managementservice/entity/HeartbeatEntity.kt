package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.HeartbeatDto
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

fun HeartbeatDto.toEntity() = HeartbeatEntity(fullAddress, componentType, timestamp)

fun HeartbeatEntity.toDto(isAlive: Boolean) = HeartbeatDto(componentId, componentType, null, timestamp, isAlive)

@Entity
class HeartbeatEntity(
        @field:Id
        @field:NotEmpty
        val componentId: String,
        val componentType: ComponentType,
        @field:Positive
        var timestamp: Long
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