package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration

/**
 * Aggregates all log types into one method onMessage, which receives the type as a parameter
 */
abstract class AggregatingLogService(config: LoggingConfiguration) : ConfiguredLogService(config) {

    abstract fun onMessage(type: LogType, message: String)

    override fun debug(message: String) = onMessage(LogType.DEBUG, message)

    override fun info(message: String) = onMessage(LogType.INFO, message)

    override fun perf(message: String) = onMessage(LogType.PERF, message)

    override fun warn(message: String) = onMessage(LogType.WARN, message)

    override fun error(message: String) = onMessage(LogType.ERROR, message)
}