package cz.vutbr.fit.knot.enticing.index.server.service

import cz.vutbr.fit.knot.enticing.api.ManagementServiceApi
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.mx.ServerMonitoringService
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class HeartbeatService(val scheduler: TaskScheduler, val configuration: EnticingConfiguration, val monitoringService: ServerMonitoringService, val api: ManagementServiceApi, loggerFactory: LoggerFactory) {

    val logger = loggerFactory.logger { }

    @PostConstruct
    fun init() {
        val heartbeatConfiguration = configuration.managementServiceConfiguration.heartbeatConfiguration
        if (heartbeatConfiguration.isEnabled) {
            logger.info("Setting up heartbeat with period ${heartbeatConfiguration.period}")
            scheduler.scheduleAtFixedRate(this::heartbeat, heartbeatConfiguration.period)
        } else {
            logger.info("hearthbeat not scheduled")
        }
    }

    private fun heartbeat() {
        api.heartbeat(monitoringService.getCurrentServerStatus())
    }

}