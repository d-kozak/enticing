package cz.vutbr.fit.knot.enticing.api

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.log.*

private const val API_BASE_PATH = "/api/v1"

/**
 * Wrapper around the api of the management service
 */
class ManagementServiceApi(componentAddress: String, componentType: ComponentType, localAddress: String, logService: LogService)
    : EnticingComponentApi(componentAddress, componentType, localAddress, logService), RemoteLoggingApi {

    private val logger = logService.logger { }

    override fun log(logType: LogType, message: String) {
        httpPost("$API_BASE_PATH/log", LogMessage(message, logType, localAddress, componentType))
    }
}