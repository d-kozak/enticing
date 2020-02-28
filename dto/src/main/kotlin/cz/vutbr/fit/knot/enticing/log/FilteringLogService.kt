package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration

fun LogService.filtered(logTypes: Set<LogType>) = FilteringLogService(this, config, logTypes)

/**
 * Allows only specific types of messages to pass through
 */
class FilteringLogService(val next: LogService, override val config: LoggingConfiguration, val logTypes: Set<LogType>) : LogService {
    override fun debug(message: String) {
        if (LogType.DEBUG in logTypes) next.debug(message)
    }

    override fun info(message: String) {
        if (LogType.INFO in logTypes) next.info(message)
    }

    override fun perf(message: String) {
        if (LogType.PERF in logTypes) next.perf(message)
    }

    override fun warn(message: String) {
        if (LogType.WARN in logTypes) next.perf(message)
    }

    override fun error(message: String) {
        if (LogType.ERROR in logTypes) next.error(message)
    }
}