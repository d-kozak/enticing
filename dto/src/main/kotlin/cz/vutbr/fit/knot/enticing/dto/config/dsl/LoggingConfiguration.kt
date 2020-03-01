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
        var stdoutLogs: EnumSet<LogType> = EnumSet.allOf(LogType::class.java),
        var fileLogs: EnumSet<LogType> = EnumSet.of(LogType.INFO, LogType.PERF, LogType.WARN, LogType.ERROR),
        var managementLoggingConfiguration: ManagementLoggingConfiguration = ManagementLoggingConfiguration()
) : EnticingConfigurationUnit {

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitLoggingConfiguration(this)
    }

    fun managementLogs(block: ManagementLoggingConfiguration.() -> Unit) {
        managementLoggingConfiguration = ManagementLoggingConfiguration().apply(block)
    }

    fun stdoutLogs(vararg types: LogType) {
        stdoutLogs.clear()
        for (type in types) stdoutLogs.add(type)
    }

    fun fileLogs(vararg types: LogType) {
        fileLogs.clear()
        for (type in types) fileLogs.add(type)
    }
}


/**
 * Specifies what gets sent to the management service
 */
data class ManagementLoggingConfiguration(
        /**
         * what types of messages should be sent to the management service
         */
        var logTypes: EnumSet<LogType> = EnumSet.noneOf(LogType::class.java)
) {
    fun logTypes(vararg types: LogType) {
        for (type in types) logTypes.add(type)
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