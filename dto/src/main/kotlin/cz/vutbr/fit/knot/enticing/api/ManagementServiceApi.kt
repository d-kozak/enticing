package cz.vutbr.fit.knot.enticing.api

import cz.vutbr.fit.knot.enticing.log.LogMessage
import cz.vutbr.fit.knot.enticing.log.LogService
import cz.vutbr.fit.knot.enticing.log.RemoteLoggingApi

/**
 * Wrapper around the api of the management service
 */
class ManagementServiceApi(address: String, logService: LogService)
    : EnticingComponentApi(address, logService), RemoteLoggingApi {

    override fun log(kind: String, message: String) {
        httpPost("/log", LogMessage(kind, message))
    }
}