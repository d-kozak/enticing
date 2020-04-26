package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ComponentInfo
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Positive


fun ComponentEntity.toComponentInfo() = ComponentInfo(id, server.id, server.address, port, type, lastHeartbeat)

@Entity
class ComponentEntity(
        @field:Id
        @field:GeneratedValue
        var id: Long,
        @field:ManyToOne
        var server: ServerInfoEntity,
        @field:Positive
        var port: Int,
        var type: ComponentType,
        var lastHeartbeat: LocalDateTime,
        @field:OneToMany(mappedBy = "component")
        var logs: List<LogEntity>,
        @field:OneToMany(mappedBy = "component")
        var perfLog: List<PerfEntity>
) {

    val fullAddress: String
        get() = server.address + ":" + port


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ComponentEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "ComponentEntity(id=$id, type=$type)"
    }


}