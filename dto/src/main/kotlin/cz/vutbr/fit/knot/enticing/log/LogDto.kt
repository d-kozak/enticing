package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class LogMessage(
        val className: String,
        val message: String,
        val logType: LogType,
        val timestamp: Long = System.currentTimeMillis()
)

fun LogMessage.toLogDto(componentId: String, componentType: ComponentType) = LogDto(className, message, logType, componentId, componentType, timestamp)

/**
 * Log message to be transmitted
 */
data class LogDto(
        @field:NotEmpty
        val className: String,
        @field:NotEmpty
        @field:Size(max = 2048)
        val message: String,
        val logType: LogType,
        @field:NotEmpty
        val componentId: String,
        val componentType: ComponentType,
        @field:Positive
        val timestamp: Long = System.currentTimeMillis()
)


enum class ComponentType {
    WEBSERVER,
    INDEX_SERVER,
    INDEX_BUILDER
}