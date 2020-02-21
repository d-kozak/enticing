package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration

class StdoutLogService(config: LoggingConfiguration) : AggregatingLogService(config) {

    override fun onMessage(kind: String, message: String) {
        println("${timestamp()} : $kind : $message")
    }
}