package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor
import java.util.*

/**
 * Specifies how the log files should be structured
 * Each component writes it's own local logs about important events  and specified subsets of events are
 * sent to the manager service which stores them for the administrator.
 */
data class LoggingConfiguration(
        /**
         * Directory into which all logs will be written
         */
        var rootDirectory: String = "",
        /**
         * what types of messages should be written to the log files
         */
        var pattern: String = "dd-MM-yyyy HH:mm:ss",
        var messageTypes: EnumSet<LogType> = EnumSet.noneOf(LogType::class.java),
        var managementLoggingConfiguration: ManagementLoggingConfiguration = ManagementLoggingConfiguration()
) : EnticingConfigurationUnit {

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitLoggingConfiguration(this)
    }

    fun managementLogs(block: ManagementLoggingConfiguration.() -> Unit) {
        managementLoggingConfiguration = ManagementLoggingConfiguration().apply(block)
    }

    fun messageTypes(vararg types: LogType) {
        for (type in types) messageTypes.add(type)
    }
}


/**
 * Specifies what gets sent to the management service
 */
data class ManagementLoggingConfiguration(
        /**
         * what types of messages should be sent to the management service
         */
        var messageTypes: EnumSet<LogType> = EnumSet.noneOf(LogType::class.java)
) {
    fun messageTypes(vararg types: LogType) {
        for (type in types) messageTypes.add(type)
    }
}


/**
 * Types of log messages that can be sent
 */
enum class LogType {
    /**
     * low level information tracing the execution
     */
    DEBUG,
    /**
     * information about important events
     */
    INFO,
    /**
     * performance related information
     * how much time each operation took
     */
    PERF,
    /**
     * something has gone wrong, but it is not critical
     */
    WARN,
    /**
     * something has gone seriously wrong
     */
    ERROR,
}