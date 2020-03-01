package cz.vutbr.fit.knot.enticing.api

import cz.vutbr.fit.knot.enticing.log.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

private const val API_BASE_PATH = "/api/v1"

/**
 * Wrapper around the api of the management service
 */
class ManagementServiceApi(remoteAddress: String, componentType: ComponentType, localAddress: String, loggerFactory: LoggerFactory)
    : EnticingComponentApi(remoteAddress, componentType, localAddress, loggerFactory), RemoteLoggingApi, AutoCloseable {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun log(logMessage: LogMessage) {
        scope.launch { httpPost("$API_BASE_PATH/log", logMessage.toLogDto(localAddress, componentType)) }
    }

    override fun perf(perfMessage: PerfMessage) {
        scope.launch { httpPost("$API_BASE_PATH/perf", perfMessage.toPerfDto(localAddress, componentType)) }
    }

    fun heartbeat() {
        httpPost("$API_BASE_PATH/heartbeat", HeartbeatDto(localAddress, componentType))
    }

    override fun close() {
        scope.cancel()
    }
}