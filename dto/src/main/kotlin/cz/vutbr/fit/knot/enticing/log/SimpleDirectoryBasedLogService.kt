package cz.vutbr.fit.knot.enticing.log

import java.io.File
import java.io.PrintWriter


class SimpleDirectoryBasedLogService(private val serviceName: String, private val logDirectory: String) : LogService, AutoCloseable {

    private var crashLogWriter: PrintWriter

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


}