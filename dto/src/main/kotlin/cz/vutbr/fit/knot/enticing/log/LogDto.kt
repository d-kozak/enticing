package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class LogMessage(
        val logType: LogType,
        val className: String,
        val message: String,
        val timestamp: LocalDateTime = LocalDateTime.now()
)

fun LogMessage.toLogDto(componentId: String, componentType: ComponentType) = LogDto(className, message, logType, componentId, componentType, timestamp)

/**
 * Log message to be transmitted
 */
data class LogDto(
        @field:NotEmpty
        val sourceClass: String,
        @field:NotEmpty
        @field:Size(max = 2048)
        val message: String,
        val logType: LogType,
        @field:NotEmpty
        val componentAddress: String,
        val componentType: ComponentType,
        val timestamp: LocalDateTime
)


enum class ComponentType {
    WEBSERVER,
    INDEX_SERVER,
    INDEX_BUILDER,
    CONSOLE_CLIENT
}