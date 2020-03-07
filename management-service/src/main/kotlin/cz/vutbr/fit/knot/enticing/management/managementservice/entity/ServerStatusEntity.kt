package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.Positive


fun ServerStatus.toEntity(server: ServerEntity, timestamp: Long) = ServerStatusEntity(0, freePhysicalMemorySize, processCpuLoad, systemCpuLoad, timestamp, server)

@Entity
class ServerStatusEntity(
        @field:GeneratedValue
        @field:Id
        @field:JsonIgnore
        val id: Long,
        val freePhysicalMemorySize: Long,
        val processCpuLoad: Double,
        val systemCpuLoad: Double,
        @field:Positive
        val timestamp: Long,
        @field:ManyToOne
        @field:JsonIgnore
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