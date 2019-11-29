package cz.vutbr.fit.knot.enticing.log

import java.io.File
import java.io.PrintWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SimpleDirectoryBasedLogService(private val serviceName: String, private val logDirectory: String) : LogService, AutoCloseable {

    private var crashLogWriter: PrintWriter

    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

    private val measurements = mutableMapOf<String, Long>()

    override fun measure(name: String) {
        check(name !in measurements) { "Name '${name}' is already used" }
        measurements[name] = System.currentTimeMillis()
    }

    init {
        val rootLogDirectory = File(logDirectory)
        if (!rootLogDirectory.exists()) {
            require(rootLogDirectory.mkdir()) { "could not create log directory $rootLogDirectory" }
        }
        require(rootLogDirectory.isDirectory) { "logdir is not a directory" }

        val serviceLogDirectory = File("$logDirectory/$serviceName")
        if (!serviceLogDirectory.exists()) {
            require(serviceLogDirectory.mkdir()) { "could not create log directory ${serviceLogDirectory.path}" }
        }

        val crashLogFile = File("$logDirectory/$serviceName/crashlog")

        crashLogWriter = crashLogFile.printWriter()
    }


    override fun reportSuccess(msg: String, name: String) {
        crashLogWriter.println("${createTimestamp()} : SUCCESS : ${msg} , took ${calcDuration(name)} ms")
    }

    override fun reportCrash(ex: Throwable, name: String?) = reportCrash(ex.message!!, name)


    override fun reportCrash(msg: String, name: String?) = crashLogWriter.println(buildString {
        append(createTimestamp())
        append(" : FAIL : ")
        append(msg)
        if (name != null) {
            append("task took ${calcDuration(name)} ms")
        }
    })

    private fun calcDuration(name: String): Long {
        val start = measurements[name] ?: throw IllegalArgumentException("Task with name '${name}' was not started")
        return System.currentTimeMillis() - start
    }

    override fun close() {
        crashLogWriter.close()
    }

    private fun createTimestamp(): String = LocalDateTime.now().format(formatter)
}