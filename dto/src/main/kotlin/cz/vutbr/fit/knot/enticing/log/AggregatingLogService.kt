package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration

/**
 * Aggregates all log types into one method onMessage, which receives the type as a parameter
 */
abstract class AggregatingLogService(config: LoggingConfiguration) : ConfiguredLogService(config) {

    abstract fun onMessage(kind: String, message: String)

    override fun debug(message: String) = onMessage("DEBUG", message)

    override fun info(message: String) = onMessage("INFO", message)

    override fun perf(message: String) = onMessage("PERF", message)

    override fun error(message: String) = onMessage("ERROR", message)

}