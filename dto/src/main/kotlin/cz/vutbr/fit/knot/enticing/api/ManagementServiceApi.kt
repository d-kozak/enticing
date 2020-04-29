package cz.vutbr.fit.knot.enticing.api

import cz.vutbr.fit.knot.enticing.log.*
import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


/**
 * Wrapper around the api of the management service
 */
class ManagementServiceApi(private val remoteAddress: String, private val componentType: ComponentType, private val localAddress: String, loggerFactory: LoggerFactory)
    : EnticingComponentApi(loggerFactory), RemoteLoggingApi, AutoCloseable {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun log(logMessage: LogMessage) {
        scope.launch { httpPost<Unit>(remoteAddress, "/log", logMessage.toLogDto(localAddress, componentType)) }
    }

    override fun perf(perfMessage: PerfMessage) {
        scope.launch { httpPost<Unit>(remoteAddress, "/perf", perfMessage.toPerfDto(localAddress, componentType)) }
    }

    fun register(info: StaticServerInfo): Boolean = httpPost<Any>(remoteAddress, "/server", info) != null

    fun heartbeat(status: ServerStatus) {
        httpPost<Unit>(remoteAddress, "/heartbeat", HeartbeatDto(localAddress, componentType, status))
    }

    override fun close() {
        scope.cancel()
    }
}