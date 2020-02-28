package cz.vutbr.fit.knot.enticing.log

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class PerfMessage(
        val className: String,
        val operationId: String,
        val arguments: String?,
        val duration: Long,
        val outcome: String,
        val timestamp: Long = System.currentTimeMillis()
)

fun PerfMessage.toPerfDto(componentId: String, componentType: ComponentType) = PerfDto(className, operationId, arguments, duration, outcome, componentId, componentType, timestamp)

data class PerfDto(
        @field:NotBlank
        val className: String,
        @field:NotBlank
        @field:Size(max = 2048)
        val operationId: String,
        @field:Size(max = 2048)
        val arguments: String?,
        val duration: Long,
        @field:NotEmpty
        val outcome: String,
        @field:NotBlank
        val componentId: String,
        val componentType: ComponentType,
        @field:Positive
        val timestamp: Long = System.currentTimeMillis()
)