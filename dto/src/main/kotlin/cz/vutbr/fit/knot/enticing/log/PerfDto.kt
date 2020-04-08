package cz.vutbr.fit.knot.enticing.log

import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

data class PerfMessage(
        val className: String,
        val operationId: String,
        val arguments: String?,
        val duration: Long,
        val result: String,
        val timestamp: LocalDateTime = LocalDateTime.now()
)

fun PerfMessage.toPerfDto(componentId: String, componentType: ComponentType) = PerfDto(className, operationId, arguments, duration, result, componentId, componentType, timestamp)

data class PerfDto(
        @field:NotBlank
        val className: String,
        @field:NotBlank
        @field:Size(max = 512)
        val operationId: String,
        @field:Size(max = 2048)
        val arguments: String?,
        @field:PositiveOrZero
        val duration: Long,
        @field:NotEmpty
        val result: String,
        @field:NotBlank
        val componentId: String,
        val componentType: ComponentType,
        val timestamp: LocalDateTime
)