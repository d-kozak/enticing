package cz.vutbr.fit.knot.enticing.log

interface RemoteLoggingApi {
    fun log(kind: String, message: String)
}