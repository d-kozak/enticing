package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ComponentInfo
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Positive

enum class ComponentStatus {
    ALIVE,
    DEAD
}

fun ComponentEntity.toComponentInfo() = ComponentInfo(id, server.id, server.address, port, type, lastHeartbeat, status)

@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "one component at one address", columnNames = ["server_id", "port"])])
data class ComponentEntity(
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
        var perfLog: List<PerfEntity>,
        @Enumerated(EnumType.STRING)
        var status: ComponentStatus = ComponentStatus.ALIVE
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

}