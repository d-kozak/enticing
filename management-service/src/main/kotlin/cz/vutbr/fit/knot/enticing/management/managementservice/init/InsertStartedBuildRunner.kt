package cz.vutbr.fit.knot.enticing.management.managementservice.init

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.service.BuildService
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ManagementUserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

/**
 * Inserts the build which was used to start the service into the database, if it is not there already
 */
@Component
@Order(3)
class InsertStartedBuildRunner(
        @Value("\${build.id}") private val buildId: String,
        private val buildService: BuildService,
        private val userService: ManagementUserService,
        loggerFactory: LoggerFactory
) : ApplicationRunner {

    private val logger = loggerFactory.logger { }

    override fun run(args: ApplicationArguments) {
        val systemDaemon = userService.getUser("SystemDaemon") ?: error("Failed to load the system daemon user")
        logger.info("Looking for build '$buildId'")
        if (buildService.isFree(buildId)) {
            buildService.save(buildId, systemDaemon)
            logger.info("Build inserted into the database")
        } else {
            logger.info("Build found, doing nothing")
        }
    }
}