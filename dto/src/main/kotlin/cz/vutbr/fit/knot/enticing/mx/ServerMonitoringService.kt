package cz.vutbr.fit.knot.enticing.mx

import com.sun.management.OperatingSystemMXBean
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import java.lang.management.ManagementFactory


data class StaticServerInfo(
        val fullAddress: String,
        val componentType: ComponentType,
        val availableProcessors: Int,
        val totalPhysicalMemorySize: Long
)

data class ServerStatus(
        val freePhysicalMemorySize: Long,
        val processCpuLoad: Double,
        val systemCpuLoad: Double
)

open class ServerMonitoringService(val fullAddress: String, val componentType: ComponentType, loggerFactory: LoggerFactory) {

    private val logger = loggerFactory.logger { }

    private val mxBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean::class.java)

    fun getCurrentServerStatus() = ServerStatus(mxBean.freePhysicalMemorySize, mxBean.processCpuLoad, mxBean.systemCpuLoad).also {
        logger.info("Current server status are $it")
    }

    fun getServerInfo() = StaticServerInfo(fullAddress, componentType, Runtime.getRuntime().availableProcessors(), mxBean.totalPhysicalMemorySize)
            .also {
                logger.info("Static server info requested $it")
            }
}