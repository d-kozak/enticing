package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.NotEmpty

fun StaticServerInfo.toEntity() = ServerEntity(fullAddress, componentType, availableProcessors, totalPhysicalMemorySize, mutableListOf())

fun ServerEntity.toServerInfo(status: ServerStatus?) = ServerInfo(componentId, componentType, availableProcessors, totalPhysicalMemorySize, status)

@Entity
class ServerEntity(
        @field:Id
        @field:NotEmpty
        val componentId: String,
        val componentType: ComponentType,
        val availableProcessors: Int,
        val totalPhysicalMemorySize: Long,
        @OneToMany(mappedBy = "server")
        @field:JsonIgnore
        val status: MutableList<ServerStatusEntity>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ServerEntity) return false

        if (componentId != other.componentId) return false

        return true
    }

    override fun hashCode(): Int {
        return componentId.hashCode()
    }

    override fun toString(): String {
        return "ServerEntity(componentId='$componentId', componentType=$componentType, availableProcessors=$availableProcessors, totalPhysicalMemorySize=$totalPhysicalMemorySize, status=$status)"
    }
}