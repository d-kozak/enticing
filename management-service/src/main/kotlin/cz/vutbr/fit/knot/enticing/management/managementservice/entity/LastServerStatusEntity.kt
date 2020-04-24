package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

data class Average(
        val cpu: Double?,
        val ram: Double?
)

@Entity
class LastServerStatusEntity(
        @Id
        var serverId: Long,
        val freePhysicalMemorySize: Double,
        val processCpuLoad: Double,
        val systemCpuLoad: Double,
        val timestamp: LocalDateTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LastServerStatusEntity) return false

        if (serverId != other.serverId) return false

        return true
    }

    override fun hashCode(): Int {
        return serverId.hashCode()
    }

    override fun toString(): String {
        return "LastServerStatusEntity(serverId=$serverId, freePhysicalMemorySize=$freePhysicalMemorySize, processCpuLoad=$processCpuLoad, systemCpuLoad=$systemCpuLoad, timestamp=$timestamp)"
    }
}