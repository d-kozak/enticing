package cz.vutbr.fit.knot.enticing.mx

import com.sun.management.OperatingSystemMXBean
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import java.lang.management.ManagementFactory
import java.time.LocalDateTime


data class StaticServerInfo(
        val fullAddress: String,
        val componentType: ComponentType,
        val availableProcessors: Int,
        val totalPhysicalMemorySize: Long,
        val timestamp: LocalDateTime = LocalDateTime.now()
)

data class ServerStatus(
        val freePhysicalMemorySize: Double,
        val processCpuLoad: Double,
        val systemCpuLoad: Double,
        val timestamp: LocalDateTime = LocalDateTime.now()
)

open class ServerMonitoringService(val fullAddress: String, val componentType: ComponentType, loggerFactory: LoggerFactory) {

    private val logger = loggerFactory.logger { }

    private val probe = ServerProbe()

    fun getCurrentServerStatus(): ServerStatus {
        val info = probe.scan()
        val status = ServerStatus(info.ramSize.toDouble() / info.freeRam.toDouble(), info.processCpuLoad, info.systemCpuLoad)
        logger.debug("Current server status are $status")
        return status
    }

    fun getStaticServerInfo(): StaticServerInfo {
        val info = probe.scan()
        val dto = StaticServerInfo(fullAddress, componentType, info.processorCount, info.ramSize)
        logger.debug("Static server info requested $dto")
        return dto
    }
}


class ServerProbe {

    data class Info(
            val processorCount: Int,
            val ramSize: Long,
            val freeRam: Long,
            val processCpuLoad: Double,
            val systemCpuLoad: Double
    )

    private val mxBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean::class.java)

    fun scan() = Info(
            processorCount = Runtime.getRuntime().availableProcessors(),
            ramSize = mxBean.totalPhysicalMemorySize,
            freeRam = mxBean.freePhysicalMemorySize,
            systemCpuLoad = mxBean.systemCpuLoad,
            processCpuLoad = mxBean.processCpuLoad
    )

}