package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration

class StdoutLogService(config: LoggingConfiguration) : AggregatingLogService(config) {

    override fun onMessage(type: LogType, message: String) {
        println("${timestamp()} : $type : $message")
    }
}