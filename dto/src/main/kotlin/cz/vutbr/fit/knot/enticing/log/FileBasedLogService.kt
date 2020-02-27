package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

fun String.asLogger(config: LoggingConfiguration) = FileBasedLogService(this, config)

class FileBasedLogService(val logFile: String, config: LoggingConfiguration) : AggregatingLogService(config), AutoCloseable {

    private var writer: PrintWriter = File(logFile).printWriter()

    override fun onMessage(type: LogType, message: String) {
        FileWriter(logFile, true)
                .use {
                    it.appendln("${timestamp()} : $type : $message")
                    it.flush()
                }
//        writer.println("${timestamp()} : $type : $message")
//        writer.flush()
    }

    override fun close() {
        writer.close()
    }
}