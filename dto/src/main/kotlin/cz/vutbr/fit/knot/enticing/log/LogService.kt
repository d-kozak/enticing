package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Basic logging interface
 */
interface LogService {
    fun debug(message: String)
    fun info(message: String)
    fun perf(message: String)
    fun error(message: String)
}

/**
 * Configuration aware log service
 */
abstract class ConfiguredLogService(val config: LoggingConfiguration) : LogService {
    protected val formatter = DateTimeFormatter.ofPattern(config.pattern)
    protected fun timestamp(): String = LocalDateTime.now().format(formatter)
}

/**
 * Logservice delegating all messages to the next logger
 * subclasses can override it's methods to perform something special only for a specific method
 */
abstract class DelegatingLogService(var next: LogService, config: LoggingConfiguration) : ConfiguredLogService(config) {
    override fun debug(message: String) = next.debug(message)
    override fun info(message: String) = next.info(message)
    override fun perf(message: String) = next.perf(message)
    override fun error(message: String) = next.error(message)
}



