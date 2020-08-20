package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import cz.vutbr.fit.knot.enticing.dto.ExtendedComponentInfo
import cz.vutbr.fit.knot.enticing.log.LogDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.CommandDto

data class DashboardInfo(
        val servers: DashboardServersInfo,
        val components: DashboardComponentsInfo,
        val logs: DashboardLogsInfo,
        val commands: DashboardCommandInfo,
        val builds: DashboardBuildsInfo
)

data class DashboardServersInfo(
        val total: Int,
        val averageCpuUsage: Double,
        val averageRamUsage: Double
)

data class DashboardComponentsInfo(
        val total: Int,
        val unresponsive: List<ExtendedComponentInfo>
)

data class DashboardLogsInfo(
        val important: List<LogDto>
)

data class DashboardCommandInfo(
        val running: List<CommandDto>
)

data class DashboardBuildsInfo(
        val running: CommandDto?,
        val last: CommandDto?
)
