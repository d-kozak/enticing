package cz.vutbr.fit.knot.enticing.management.managementservice.init

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CreateUserRequest
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ManagementUserService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
@Order(1)
@Incomplete("this implementation is bad bad, bad, load password from the environment instead?")
class AddSystemDemonRunner(
        private val userService: ManagementUserService,
        loggerFactory: LoggerFactory
) : ApplicationRunner {

    private val logger = loggerFactory.logger { }

    override fun run(args: ApplicationArguments?) {
        logger.info("Creating system daemon")
        userService.createNewUser(CreateUserRequest("SystemDaemon", rnd(), setOf("ADMIN", "PLATFORM_MAINTAINER", "USER")))
    }

    private fun rnd(): String = buildString {
        for (i in 0 until 42)
            append('a' + Random.nextInt(26))
    }
}