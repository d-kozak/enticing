package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration

/**
 * Dispatches messages to all child log services
 */
class DispatchingLogService(override val config: LoggingConfiguration, val children: List<LogService>) : LogService {
    constructor(config: LoggingConfiguration, vararg children: LogService) : this(config, children.toList())

    override fun debug(message: String) {
        children.forEach { it.debug(message) }
    }

    override fun info(message: String) {
        children.forEach { it.info(message) }
    }

    override fun perf(message: String) {
        children.forEach { it.perf(message) }
    }

    override fun warn(message: String) {
        children.forEach { it.warn(message) }
    }

    override fun error(message: String) {
        children.forEach { it.error(message) }
    }
}