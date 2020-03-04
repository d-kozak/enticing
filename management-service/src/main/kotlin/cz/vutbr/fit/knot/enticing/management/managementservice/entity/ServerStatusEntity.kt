package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.Positive


@Entity
class ServerStatusEntity(
        @field:GeneratedValue
        @field:Id
        val id: Long,
        val freePhysicalMemorySize: Long,
        val processCpuLoad: Double,
        val systemCpuLoad: Double,
        @field:Positive
        val timestamp: Long,
        @ManyToOne
        val server: ServerEntity
) {


    override fun toString(): String {
        return "ServerStatusEntity(id=$id, freePhysicalMemorySize=$freePhysicalMemorySize, processCpuLoad=$processCpuLoad, systemCpuLoad=$systemCpuLoad, timestamp=$timestamp)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ServerStatusEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}