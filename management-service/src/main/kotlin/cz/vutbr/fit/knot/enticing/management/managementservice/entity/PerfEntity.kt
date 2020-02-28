package cz.vutbr.fit.knot.enticing.management.managementservice.entity


import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.PerfDto
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

fun PerfDto.toEntity() = PerfEntity(0, className, operationId, arguments, duration, outcome, componentId, componentType, timestamp)

fun PerfEntity.toDto() = PerfDto(className, operationId, arguments, duration, outcome, componentId, componentType, timestamp)

@Entity
class PerfEntity(
        @field:Id
        @field:GeneratedValue
        var id: Long = 0,
        @field:NotEmpty
        val className: String,
        @field:NotEmpty
        @field:Column(length = 2048)
        val operationId: String,
        @field:Column(length = 2048)
        val arguments: String?,
        @field:Positive
        val duration: Long,
        @field:NotEmpty
        val outcome: String,
        @field:NotEmpty
        val componentId: String,
        val componentType: ComponentType,
        @field:Positive
        val timestamp: Long = System.currentTimeMillis()
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
        return "PerfEntity(id=$id, className='$className', operationId='$operationId', arguments=$arguments, duration=$duration, outcome='$outcome', componentId='$componentId', componentType=$componentType, timestamp=$timestamp)"
    }


}