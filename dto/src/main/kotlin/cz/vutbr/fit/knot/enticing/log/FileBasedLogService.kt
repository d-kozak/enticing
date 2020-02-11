package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.LoggingConfiguration
import java.io.File
import java.io.PrintWriter

fun String.asLogger(config: LoggingConfiguration) = FileBasedLogService(this, config)

class FileBasedLogService(logFile: String, config: LoggingConfiguration) : ConfiguredLogService(config), AutoCloseable {

    private var writer: PrintWriter = File(logFile).printWriter()

    override fun debug(message: String) = message("DEBUG", message)

    override fun info(message: String) = message("INFO", message)

    override fun perf(message: String) = message("PERF", message)

    override fun error(message: String) = message("ERROR", message)

    private fun message(prefix: String, message: String) {
        writer.print(timestamp())
        writer.print(" : ")
        writer.print(prefix)
        writer.print(" : ")
        writer.println(message)
        writer.flush()
    }

    override fun close() {
        writer.close()
    }
}