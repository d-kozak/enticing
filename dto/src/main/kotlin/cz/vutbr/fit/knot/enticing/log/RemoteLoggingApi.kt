package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType

interface RemoteLoggingApi {
    fun log(type: LogType, message: String)
}