package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import javax.validation.constraints.NotBlank

/**
 * Log message to be transmitted
 */
data class LogMessage(
        @field:NotBlank
        val text: String,
        val logType: LogType,
        val source: String,
        val componentType: ComponentType,
        val timestamp: Long = System.currentTimeMillis())


enum class ComponentType {
    WEBSERVER,
    INDEX_SERVER,
    INDEX_BUILDER
}