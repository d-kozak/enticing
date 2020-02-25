package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.log.util.resolveName

/**
 * creates a new measuring logger and appends the prefixed logger immediately after it, reusing all the following loggers
 */
inline fun MeasuringLogService.logger(noinline func: () -> Unit): MeasuringLogService {
    val name = resolveName(func)
    return MeasuringLogService(PrefixedLogService(name, this.next), this.config)
}

inline fun LogService.logger(noinline func: () -> Unit): LogService {
    val name = resolveName(func)
    return PrefixedLogService(name, this)
}

/**
 * Logger which prefixes all messages
 */
class PrefixedLogService(val prefix: String, var next: LogService) : LogService {

    override fun debug(message: String) = next.debug("$prefix : $message")

    override fun info(message: String) = next.info("$prefix : $message")

    override fun perf(message: String) = next.perf("$prefix : $message")

    override fun warn(message: String) = next.warn("$prefix : $message")

    override fun error(message: String) = next.error("$prefix : $message")
}

