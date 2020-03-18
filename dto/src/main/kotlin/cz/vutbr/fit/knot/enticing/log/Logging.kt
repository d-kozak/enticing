package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration
import cz.vutbr.fit.knot.enticing.log.util.resolveName
import java.io.File
import java.io.FileWriter
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

interface Logger {
    fun debug(message: String)
    fun info(message: String)
    fun warn(message: String)
    fun error(message: String)
    fun perf(operationId: String, arguments: String?, duration: Long, outcome: String)
}

object SimpleStdoutLoggerFactory : LoggerFactory {

    private val config = LoggingConfiguration().apply {
        pattern = "dd-MM-yyyy HH:mm:ss"
        stdoutLogs.remove(LogType.DEBUG)
    }


    override fun namedLogger(name: String): Logger = NamedLogger(name, listOf(FilteringLoggerNode(config.stdoutLogs, StdoutLoggerNode(config))))

    override fun addRemoteApi(remoteLoggingApi: RemoteLoggingApi) {
        throw UnsupportedOperationException("adding loggers not supported here")
    }

    override fun close() {

    }
}

fun Logger.error(ex: Exception) {
    this.error("${ex::class} ${ex.message}")
}

inline fun <T> Logger.measure(operationId: String, arguments: String? = null, block: () -> T): T {
    val start = System.currentTimeMillis()
    return try {
        val res = block()
        perf(operationId, arguments, System.currentTimeMillis() - start, "SUCCESS")
        res
    } catch (ex: Throwable) {
        perf(operationId, arguments, System.currentTimeMillis() - start, "FAILURE: ${ex::class} ${ex.message}")
        throw ex
    }
}

private fun LogMessage.toPrintableString(formatter: DateTimeFormatter) = "${readableTimestamp(timestamp, formatter)} : $className : $logType \n message:  $message"

private fun PerfMessage.toPrintableString(formatter: DateTimeFormatter) = "${readableTimestamp(timestamp, formatter)} : $className : PERF : Operation '$operationId' : $outcome : ${readableDuration(duration)}  \n arguments $arguments"

private fun readableDuration(millis: Long) = Duration.ofMillis(millis).toString().substring(2)

private fun readableTimestamp(timestamp: LocalDateTime, formatter: DateTimeFormatter): String = timestamp.format(formatter)


inline fun LoggerFactory.logger(noinline func: () -> Unit): Logger = namedLogger(resolveName(func))

interface LoggerFactory : AutoCloseable {
    fun namedLogger(name: String): Logger

    fun addRemoteApi(remoteLoggingApi: RemoteLoggingApi)
}


private class NamedLogger(val componentName: String, val pipelines: List<LoggerPipeLineNode>) : Logger {
    override fun debug(message: String) = onLog(LogMessage(componentName, message, LogType.DEBUG))

    override fun info(message: String) = onLog(LogMessage(componentName, message, LogType.INFO))

    override fun warn(message: String) = onLog(LogMessage(componentName, message, LogType.WARN))

    override fun error(message: String) = onLog(LogMessage(componentName, message, LogType.ERROR))

    private fun onLog(logMessage: LogMessage) {
        pipelines.forEach { it.log(logMessage) }
    }

    override fun perf(operationId: String, arguments: String?, duration: Long, outcome: String) {
        val perfMessage = PerfMessage(componentName, operationId, arguments, duration, outcome)
        pipelines.forEach { it.perf(perfMessage) }
    }
}


interface LoggerPipeLineNode {
    fun log(logMessage: LogMessage)
    fun perf(perfMessage: PerfMessage)
}

private class FilteringLoggerNode(val filter: EnumSet<LogType>, val next: LoggerPipeLineNode) : LoggerPipeLineNode {
    override fun log(logMessage: LogMessage) {
        if (logMessage.logType in filter) next.log(logMessage)
    }

    override fun perf(perfMessage: PerfMessage) {
        if (LogType.PERF in filter) next.perf(perfMessage)
    }
}

abstract class ConfiguredLoggerNode(config: LoggingConfiguration) : LoggerPipeLineNode {
    protected val formatter = DateTimeFormatter.ofPattern(config.pattern)
}

private class StdoutLoggerNode(config: LoggingConfiguration) : ConfiguredLoggerNode(config) {

    override fun log(logMessage: LogMessage) {
        println(logMessage.toPrintableString(formatter))
    }

    override fun perf(perfMessage: PerfMessage) {
        println(perfMessage.toPrintableString(formatter))
    }
}


private class FileBasedLoggerNode(val file: File, config: LoggingConfiguration) : ConfiguredLoggerNode(config), AutoCloseable {

    private val writer = FileWriter(file, true)

    override fun log(logMessage: LogMessage) {
        writer.appendln(logMessage.toPrintableString(formatter))
    }

    override fun perf(perfMessage: PerfMessage) {
        writer.appendln(perfMessage.toPrintableString(formatter))
    }

    override fun close() {
        writer.close()
    }
}


interface RemoteLoggingApi : LoggerPipeLineNode


fun LoggingConfiguration.loggerFactoryFor(serviceId: String, remoteLoggingApi: RemoteLoggingApi? = null): LoggerFactory {
    val stdoutLogger = FilteringLoggerNode(stdoutLogs, StdoutLoggerNode(this))
    val fileLogger = FileBasedLoggerNode(File("${this.rootDirectory}/$serviceId.log"), this)
    val filteredFileLogger = FilteringLoggerNode(fileLogs, fileLogger)
    val pipelines = mutableListOf<LoggerPipeLineNode>(stdoutLogger, filteredFileLogger)
    if (remoteLoggingApi != null) {
        pipelines.add(FilteringLoggerNode(managementLoggingConfiguration.logTypes, remoteLoggingApi))
    }
    return object : LoggerFactory {
        override fun namedLogger(name: String): Logger = NamedLogger(name, pipelines)

        override fun addRemoteApi(remoteLoggingApi: RemoteLoggingApi) {
            pipelines.add(remoteLoggingApi)
        }

        override fun close() {
            fileLogger.close()
        }
    }
}