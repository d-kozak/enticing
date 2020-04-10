package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import java.time.LocalDateTime
import javax.persistence.*


fun ServerStatus.toEntity(server: ServerInfoEntity, timestamp: LocalDateTime) = ServerStatusEntity(0, freePhysicalMemorySize, processCpuLoad, systemCpuLoad, timestamp, server)

fun ServerStatusEntity.toDto() = ServerStatus(freePhysicalMemorySize, processCpuLoad, systemCpuLoad)

@Entity
@Table(indexes = [Index(name = "timestampIndex", columnList = "timestamp", unique = false)])
class ServerStatusEntity(
        @field:GeneratedValue
        @field:Id
        @field:JsonIgnore
        val id: Long,
        val freePhysicalMemorySize: Long,
        val processCpuLoad: Double,
        val systemCpuLoad: Double,
        val timestamp: LocalDateTime,
        @field:ManyToOne
        @field:JsonIgnore
        val server: ServerInfoEntity
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