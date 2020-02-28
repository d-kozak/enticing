package cz.vutbr.fit.knot.enticing.api

import cz.vutbr.fit.knot.enticing.log.*

private const val API_BASE_PATH = "/api/v1"

/**
 * Wrapper around the api of the management service
 */
class ManagementServiceApi(remoteAddress: String, componentType: ComponentType, localAddress: String, loggerFactory: LoggerFactory)
    : EnticingComponentApi(remoteAddress, componentType, localAddress, loggerFactory), RemoteLoggingApi {

    override fun log(logMessage: LogMessage) {
        httpPost("$API_BASE_PATH/log", logMessage.toLogDto(localAddress, componentType))
    }

    override fun perf(perfMessage: PerfMessage) {
        httpPost("$API_BASE_PATH/perf", perfMessage.toPerfDto(localAddress, componentType))
    }
}