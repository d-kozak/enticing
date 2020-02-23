package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration
import java.time.Duration

/**
 * Logservice capable of measuring time
 */
class MeasuringLogService(next: LogService, config: LoggingConfiguration) : DelegatingLogService(next, config) {
    data class MeasurementHandle(val name: String, val startTime: Long = System.currentTimeMillis())

    inline fun <T> measure(name: String, block: () -> T): T {
        val handle = startMeasurement(name)
        return try {
            val res = block()
            reportSuccess(handle)
            res
        } catch (ex: Throwable) {
            reportCrash(ex, handle)
            throw ex
        }
    }

    fun startMeasurement(name: String): MeasurementHandle {
        next.perf("Started measurement '$name'")
        return MeasurementHandle(name)
    }

    fun reportSuccess(handle: MeasurementHandle) {
        next.perf("'${handle.name}' : SUCCESS : it took ${calcDuration(handle)} ms")
    }

    fun reportCrash(ex: Throwable, handle: MeasurementHandle) = reportCrash(ex.message!!, handle)

    fun reportCrash(msg: String, handle: MeasurementHandle) {
        next.error("'${handle.name}' : FAILURE - $msg : it took ${calcDuration(handle)} ms")
    }

    private fun calcDuration(handle: MeasurementHandle): String = Duration.ofMillis(System.currentTimeMillis() - handle.startTime).toString().substring(2)
}

fun LogService.measuring(config: LoggingConfiguration): MeasuringLogService = MeasuringLogService(this, config)

