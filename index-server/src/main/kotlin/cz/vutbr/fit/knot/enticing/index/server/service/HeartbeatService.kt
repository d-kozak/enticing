package cz.vutbr.fit.knot.enticing.index.server.service

import cz.vutbr.fit.knot.enticing.api.ManagementServiceApi
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.HeartbeatConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.mx.ServerMonitoringService
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import java.time.Instant
import javax.annotation.PostConstruct

@Service
class HeartbeatService(val scheduler: TaskScheduler, val configuration: EnticingConfiguration, val monitoringService: ServerMonitoringService, val api: ManagementServiceApi, loggerFactory: LoggerFactory) {

    val logger = loggerFactory.logger { }

    @PostConstruct
    fun init() {
        val heartbeatConfiguration = configuration.managementServiceConfiguration.heartbeatConfiguration
        if (heartbeatConfiguration.isEnabled) {
            scheduler.schedule({ enableHeartbeat(heartbeatConfiguration) }, Instant.now().plusMillis(500))
        } else {
            logger.info("heartbeat not scheduled")
        }
    }

    fun enableHeartbeat(heartbeatConfiguration: HeartbeatConfiguration) {
        logger.info("Setting up heartbeat with period ${heartbeatConfiguration.period}")
        val info = monitoringService.getStaticServerInfo()
        var i = 1
        val limit = 10
        var success = false
        while (i <= limit && !success) {
            if (!api.register(info)) {
                logger.warn("failed to register to the management server, try no ${i}")
            } else {
                success = true
            }
            i++
            Thread.sleep(1_000)
        }
        if (success) {
            logger.info("Registration successful, starting periodic heartbeat with period ${heartbeatConfiguration.period}")
            scheduler.scheduleAtFixedRate(this::heartbeat, heartbeatConfiguration.period)
        } else {
            logger.error("Failed to register to the management server, heartbeat will not be enabled")
        }
    }

    private fun heartbeat() {
        api.heartbeat(monitoringService.getCurrentServerStatus())
    }

}