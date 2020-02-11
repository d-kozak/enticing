package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.LoggingConfiguration

class StdoutLogService(config: LoggingConfiguration) : ConfiguredLogService(config) {

    override fun debug(message: String) = message("DEBUG", message)

    override fun info(message: String) = message("INFO", message)

    override fun perf(message: String) = message("PERF", message)

    override fun error(message: String) = message("ERROR", message)

    private fun message(prefix: String, message: String) {
        print(timestamp())
        print(" : ")
        print(prefix)
        print(" : ")
        println(message)
    }
}