package cz.vutbr.fit.knot.enticing.log

interface LogService {
    fun reportCrash(ex: Throwable)
    fun reportCrash(msg: String)
}