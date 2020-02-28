package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import javax.validation.constraints.NotBlank

data class PerfMessage(
        val className: String,
        val operationId: String,
        val arguments: String?,
        val duration: Long,
        val outcome: String,
        val timestamp: Long = System.currentTimeMillis()
)

data class LogMessage(
        val className: String,
        val message: String,
        val logType: LogType,
        val timestamp: Long = System.currentTimeMillis()
)

/**
 * Log message to be transmitted
 */
data class LogDto(
        @field:NotBlank
        val className: String,
        @field:NotBlank
        val message: String,
        val logType: LogType,
        @field:NotBlank
        val componentId: String,
        val componentType: ComponentType,
        val timestamp: Long = System.currentTimeMillis()
)


enum class ComponentType {
    WEBSERVER,
    INDEX_SERVER,
    INDEX_BUILDER
}