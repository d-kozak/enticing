package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.NotEmpty

fun StaticServerInfo.toEntity() = ServerInfoEntity(0, fullAddress.split(":")[0], availableProcessors, totalPhysicalMemorySize, mutableListOf(), mutableListOf())

fun ServerInfoEntity.toServerInfo(status: ServerStatus?) = ServerInfo(id, address, availableProcessors, totalPhysicalMemorySize, status)

@Entity
class ServerInfoEntity(
        @field:Id
        @field:GeneratedValue
        var id: Long,
        @field:NotEmpty
        var address: String,
        val availableProcessors: Int,
        val totalPhysicalMemorySize: Long,
        @field:OneToMany(mappedBy = "server")
        @field:JsonIgnore
        var components: List<ComponentEntity>,
        @field:OneToMany(mappedBy = "server")
        @field:JsonIgnore
        val status: MutableList<ServerStatusEntity>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ServerInfoEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "ServerEntity(address=$address, availableProcessors=$availableProcessors, totalPhysicalMemorySize=$totalPhysicalMemorySize, status=$status)"
    }
}