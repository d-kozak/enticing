package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import cz.vutbr.fit.knot.enticing.dto.BasicComponentInfo
import cz.vutbr.fit.knot.enticing.dto.ExtendedComponentInfo
import cz.vutbr.fit.knot.enticing.dto.Status
import cz.vutbr.fit.knot.enticing.log.ComponentType
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive


fun ComponentEntity.toComponentInfo() = ExtendedComponentInfo(id, server.id, server.address, port, type, buildId, lastHeartbeat, status)
fun ComponentEntity.toBasicComponentInfo() = BasicComponentInfo(id, server.address, port, type, buildId)

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
        @field:NotBlank
        var buildId: String,
        var lastHeartbeat: LocalDateTime?,
        @field:OneToMany(mappedBy = "component")
        var logs: List<LogEntity>,
        @field:OneToMany(mappedBy = "component")
        var perfLog: List<PerfEntity>,
        @Enumerated(EnumType.STRING)
        var status: Status = Status.RUNNING,
        /**
         * Since it is a set, be careful to set component ids before inserting
         */
        @ManyToMany
        var corpuses: MutableSet<CorpusEntity> = mutableSetOf()
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