package cz.vutbr.fit.knot.enticing.log

interface LogService {
    fun measure(name: String)
    fun reportSuccess(msg: String, name: String)
    fun reportCrash(ex: Throwable, name: String? = null)
    fun reportCrash(msg: String, name: String? = null)
}