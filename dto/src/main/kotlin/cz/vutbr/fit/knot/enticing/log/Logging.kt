package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration
import cz.vutbr.fit.knot.enticing.log.util.resolveName
import java.io.File
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

interface Logger {
    fun debug(message: String)
    fun info(message: String)
    fun warn(message: String)
    fun error(message: String)
    fun perf(operationId: String, arguments: String?, duration: Long, outcome: String)
}

class SimpleStdoutLogger : Logger {

    companion object {
        private val FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    }

    override fun debug(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun info(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun warn(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun error(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun perf(operationId: String, arguments: String?, duration: Long, outcome: String) {
        println(PerfMessage())
    }

    private fun onLog(logMessage: LogMessage) {
        println(logMessage.toPrintableString(FORMATTER))
    }

}

private fun LogMessage.toPrintableString(formatter: DateTimeFormatter) = "${readableTimestamp(timestamp, formatter)} : $className :$logType : $message"

private fun PerfMessage.toPrintableString(formatter: DateTimeFormatter) = "${readableTimestamp(timestamp, formatter)} : $className : PERF : Operation '$operationId' : ${readableDuration(duration)} : $outcome "

private fun readableDuration(millis: Long) = Duration.ofMillis(millis).toString().substring(2)

private fun readableTimestamp(timestamp: Long, formatter: DateTimeFormatter): String = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).format(formatter)

fun Logger.error(ex: Exception) {
    this.error("${ex::class} ${ex.message}")
}

inline fun <T> Logger.measure(operationId: String, arguments: String, block: () -> T): T {
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

inline fun LoggerFactory.logger(noinline func: () -> Unit): Logger = namedLogger(resolveName(func))

interface LoggerFactory {
    fun namedLogger(name: String): Logger
}


private class NamedLogger(val componentName: String, val pipelines: List<LoggerPipeLineNode>) : Logger {
    override fun debug(message: String) = onLog(LogType.DEBUG, System.currentTimeMillis(), message)

    override fun info(message: String) = onLog(LogType.INFO, System.currentTimeMillis(), message)

    override fun warn(message: String) = onLog(LogType.WARN, System.currentTimeMillis(), message)

    override fun error(message: String) = onLog(LogType.ERROR, System.currentTimeMillis(), message)

    private fun onLog(logType: LogType, timestamp: Long, message: String) {
        pipelines.forEach { it.log(LogMessage(componentName, message, logType, timestamp)) }
    }

    override fun perf(operationId: String, arguments: String?, duration: Long, outcome: String) {
        val timestamp = System.currentTimeMillis()
        pipelines.forEach { it.perf(componentName, operationId, arguments, timestamp, duration, outcome) }
    }
}


private interface LoggerPipeLineNode {
    fun log(logMessage: LogMessage)
    fun perf(componentName: String, operationId: String, arguments: String?, timestamp: Long, duration: Long, outcome: String)
}

private class FilteringLoggerNode(val filter: EnumSet<LogType>, val next: LoggerPipeLineNode) : LoggerPipeLineNode {
    override fun log(logMessage: LogMessage) {
        if (logMessage.logType in filter) next.log(logMessage)
    }

    override fun perf(componentName: String, operationId: String, arguments: String?, timestamp: Long, duration: Long, outcome: String) {
        if (LogType.PERF in filter) next.perf(componentName, operationId, arguments, timestamp, duration, outcome)
    }
}

abstract class ConfiguredLoggerNode(config: LoggingConfiguration) : LoggerPipeLineNode {
    protected val formatter = DateTimeFormatter.ofPattern(config.pattern)
}

private class StdoudLoggerNode(config: LoggingConfiguration) : ConfiguredLoggerNode(config) {

    override fun log(logMessage: LogMessage) {
        println(logMessage.toPrintableString(formatter))
    }

    override fun perf(componentName: String, operationId: String, arguments: String?, timestamp: Long, duration: Long, outcome: String) {
        println()
    }
}


private class FileBasedLoggerNode(val file: File, config: LoggingConfiguration) : ConfiguredLoggerNode(config) {
    override fun log(logMessage: LogMessage) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun perf(componentName: String, operationId: String, arguments: String?, timestamp: Long, duration: Long, outcome: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


interface RemoteLoggingApi {
    fun log(logMessage: LogMessage)
}

private class RemoteLoggerNode(config: LoggingConfiguration) : ConfiguredLoggerNode(config) {
    override fun log(logMessage: LogMessage) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun perf(componentName: String, operationId: String, arguments: String?, timestamp: Long, duration: Long, outcome: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

data class RemoteLoggingConfiguration(val localAddress: String, val managementAddress: String, val componentType: ComponentType)


fun LoggingConfiguration.loggerFactoryFor(serviceId: String, remoteLoggingConfiguration: RemoteLoggingConfiguration? = null): LoggerFactory {
    val stdoutNode = StdoudLoggerNode(this)
    val pipelines = mutableListOf(stdoutNode)
    return object : LoggerFactory {
        override fun namedLogger(name: String): Logger = NamedLogger(name, pipelines)
    }
}