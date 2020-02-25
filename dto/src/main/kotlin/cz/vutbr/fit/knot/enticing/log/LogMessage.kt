package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType

/**
 * Log message to be transmitted
 */
data class LogMessage(
        val type: LogType,
        val text: String,
        val timestamp: Long = System.currentTimeMillis())