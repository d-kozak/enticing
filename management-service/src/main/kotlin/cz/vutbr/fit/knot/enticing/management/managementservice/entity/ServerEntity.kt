package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.NotEmpty

fun StaticServerInfo.toEntity(fullAddress: String) = ServerEntity(fullAddress, availableProcessors, totalPhysicalMemorySize, mutableListOf())

@Entity
class ServerEntity(
        @field:Id
        @field:NotEmpty
        val componentId: String,
        val availableProcessors: Int,
        val totalPhysicalMemorySize: Long,
        @OneToMany(mappedBy = "server", fetch = FetchType.EAGER)
        val status: MutableList<ServerStatusEntity>
) {


    override fun toString(): String {
        return "ServerEntity(componentId='$componentId', availableProcessors=$availableProcessors, totalPhysicalMemorySize=$totalPhysicalMemorySize)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ServerEntity) return false

        if (componentId != other.componentId) return false

        return true
    }

    override fun hashCode(): Int {
        return componentId.hashCode()
    }
}