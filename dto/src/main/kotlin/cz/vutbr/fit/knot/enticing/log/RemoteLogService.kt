package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration

class RemoteLogService(config: LoggingConfiguration, val api: RemoteLoggingApi) : AggregatingLogService(config) {
    override fun onMessage(type: LogType, message: String) {
        api.log(type, message)
    }
}