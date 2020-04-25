package cz.vutbr.fit.knot.enticing.management.managementservice.entity


import cz.vutbr.fit.knot.enticing.log.PerfDto
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.PositiveOrZero

fun PerfDto.toEntity(component: ComponentEntity) = PerfEntity(0, sourceClass, operationId, arguments, duration, result, component, timestamp)

fun PerfEntity.toDto() = PerfDto(sourceClass, operationId, arguments, duration, result, component.fullAddress, component.type, timestamp)

@Entity
class PerfEntity(
        @field:Id
        @field:GeneratedValue
        var id: Long = 0,
        @field:NotEmpty
        val sourceClass: String,
        @field:NotEmpty
        @field:Column(length = 512)
        val operationId: String,
        @field:Column(length = 2048)
        val arguments: String?,
        @field:PositiveOrZero
        val duration: Long,
        @field:NotEmpty
        val result: String,
        @field:ManyToOne
        val component: ComponentEntity,
        val timestamp: LocalDateTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LogEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "PerfEntity(id=$id, className='$sourceClass', operationId='$operationId', arguments=$arguments, duration=$duration, outcome='$result', timestamp=$timestamp)"
    }

}