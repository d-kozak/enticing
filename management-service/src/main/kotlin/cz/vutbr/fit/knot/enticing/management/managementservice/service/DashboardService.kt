package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.*
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DashboardService(
        val serverInfoRepository: ServerInfoRepository,
        val lastServerStatusRepository: LastServerStatusRepository,
        val componentRepository: ComponentRepository,
        val logRepository: LogRepository,
        val commandRepository: CommandRepository
) {

    fun getInfo() = DashboardInfo(
            servers = dashboardInfo(),
            components = componentInfo(),
            logs = logInfo(),
            builds = buildsInfo(),
            commands = commandsInfo()
    )

    private fun commandsInfo(): DashboardCommandInfo {
        return DashboardCommandInfo(commandRepository.findByState(CommandState.RUNNING).map { it.toDto() })
    }

    private fun buildsInfo(): DashboardBuildsInfo {
        val running = commandRepository.findTopByTypeAndStateOrderBySubmittedAtDesc(CommandType.LOCAL_TEST, CommandState.RUNNING)?.toDto()
        val last = commandRepository.findTopByTypeAndStateInOrderBySubmittedAtDesc(CommandType.LOCAL_TEST, listOf(CommandState.FINISHED))?.toDto()
        return DashboardBuildsInfo(running, last)
    }

    private fun logInfo(): DashboardLogsInfo {
        return DashboardLogsInfo(logRepository.findTop5ByLogTypeInOrderByTimestampDesc(listOf(LogType.ERROR, LogType.WARN)).map { it.toDto() })
    }

    private fun componentInfo(): DashboardComponentsInfo {
        val total = componentRepository.count().toInt()
        val unresponsive = componentRepository.findByLastHeartbeatBefore(LocalDateTime.now().minusMinutes(2)).map { it.toComponentInfo() }
        return DashboardComponentsInfo(total, unresponsive)
    }

    private fun dashboardInfo(): DashboardServersInfo {
        val total = serverInfoRepository.count().toInt()
        val avg = lastServerStatusRepository.computeAverage()
        return DashboardServersInfo(total, avg.cpu ?: 0.0, avg.ram ?: 0.0)
    }
}


