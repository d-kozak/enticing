package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.LoggingConfiguration
import cz.vutbr.fit.knot.enticing.dto.utils.MResult

/**
 * Logservice capable of measuring time
 */
class MeasuringLogService(next: LogService, config: LoggingConfiguration) : DelegatingLogService(next, config) {
    data class MeasurementHandle(val name: String, val startTime: Long = System.currentTimeMillis())

    fun <T> measure(name: String, block: () -> T): MResult<T> {
        val handle = startMeasurement(name)
        val res = MResult.runCatching(block)
        when {
            res.isSuccess -> reportSuccess(handle)
            else -> reportCrash(res.exception, handle)
        }
        return res
    }

    fun startMeasurement(name: String): MeasuringLogService.MeasurementHandle {
        next.perf("Started measurement '$name'")
        return MeasuringLogService.MeasurementHandle(name)
    }

    fun reportSuccess(handle: MeasuringLogService.MeasurementHandle) {
        next.perf("SUCCESS : it took ${calcDuration(handle)} ms")
    }

    fun reportCrash(ex: Throwable, handle: MeasuringLogService.MeasurementHandle) = reportCrash(ex.message!!, handle)

    fun reportCrash(msg: String, handle: MeasuringLogService.MeasurementHandle) {
        next.error("FAILURE - $msg : it took ${calcDuration(handle)} ms")
    }

    private fun calcDuration(handle: MeasuringLogService.MeasurementHandle): Long = System.currentTimeMillis() - handle.startTime
}

fun LogService.measuring(config: LoggingConfiguration): MeasuringLogService = MeasuringLogService(this, config)

