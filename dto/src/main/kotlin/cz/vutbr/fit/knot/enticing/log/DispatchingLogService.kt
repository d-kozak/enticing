package cz.vutbr.fit.knot.enticing.log

/**
 * Dispatches messages to all child log services
 */
class DispatchingLogService(val children: List<LogService>) : LogService {
    constructor(vararg children: LogService) : this(children.toList())

    override fun debug(message: String) {
        children.forEach { it.debug(message) }
    }

    override fun info(message: String) {
        children.forEach { it.info(message) }
    }

    override fun perf(message: String) {
        children.forEach { it.perf(message) }
    }

    override fun error(message: String) {
        children.forEach { it.error(message) }
    }
}