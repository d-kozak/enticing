package cz.vutbr.fit.knot.enticing.api

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.log.LogMessage
import cz.vutbr.fit.knot.enticing.log.LogService
import cz.vutbr.fit.knot.enticing.log.RemoteLoggingApi
import cz.vutbr.fit.knot.enticing.log.logger

private const val API_BASE_PATH = "/api/v1"

/**
 * Wrapper around the api of the management service
 */
class ManagementServiceApi(address: String, logService: LogService)
    : EnticingComponentApi(address, logService), RemoteLoggingApi {

    private val logger = logService.logger { }

    override fun log(type: LogType, message: String) {
        httpPost("$API_BASE_PATH/log", LogMessage(message, type))
    }
}