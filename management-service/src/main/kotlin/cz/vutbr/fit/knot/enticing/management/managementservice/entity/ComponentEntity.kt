package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import cz.vutbr.fit.knot.enticing.log.ComponentType
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Positive

@Entity
class ComponentEntity(
        @field:Id
        @field:GeneratedValue
        var id: Long,
        @field:ManyToOne
        var server: ServerEntity,
        @field:Positive
        var port: Long,
        var type: ComponentType,
        @field:OneToMany(mappedBy = "component")
        @field:JsonIgnore
        var lastHeartbeat: LocalDateTime,
        @field:OneToMany(mappedBy = "component")
        @field:JsonIgnore
        var logs: List<LogEntity>,
        @field:OneToMany(mappedBy = "component")
        @field:JsonIgnore
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