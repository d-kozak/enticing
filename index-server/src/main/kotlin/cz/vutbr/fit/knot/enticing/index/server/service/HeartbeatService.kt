package cz.vutbr.fit.knot.enticing.index.server.service

import cz.vutbr.fit.knot.enticing.api.ManagementServiceApi
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class HeartbeatService(val scheduler: TaskScheduler, val configuration: EnticingConfiguration, val api: ManagementServiceApi, loggerFactory: LoggerFactory) {

    val logger = loggerFactory.logger { }

    @PostConstruct
    fun init() {
        val hearthBeatConfiguration = configuration.managementServiceConfiguration.hearthBeatConfiguration
        if (hearthBeatConfiguration.isEnabled) {
            logger.info("Setting up heartbeat with period ${hearthBeatConfiguration.period}")
            scheduler.scheduleAtFixedRate(api::heartbeat, hearthBeatConfiguration.period)
        } else {
            logger.info("hearthbeat not scheduled")
        }
    }
}