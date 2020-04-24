package cz.vutbr.fit.knot.enticing.management.managementservice.init

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.UserEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AddAdminRunner(
        val userRepository: UserRepository,
        val encoder: PasswordEncoder,
        @Value("\${debug.runner.start}")
        private val runDebug: Boolean,
        loggerFactory: LoggerFactory
) : ApplicationRunner {

    private val logger = loggerFactory.logger { }

    override fun run(args: ApplicationArguments?) {
        if (!runDebug) {
            logger.info("Disabled")
            return
        }
        val admins = userRepository.findByRolesContains("ADMIN")
        if (admins.isEmpty()) {
            val rawPassword = "knot12"
            var login = "admin"
            while (userRepository.existsByLogin(login)) {
                login += "${randomInt()}"
            }
            val newAdmin = UserEntity(login = login, encryptedPassword = encoder.encode(rawPassword), roles = mutableSetOf("PLATFORM_MAINTAINER", "ADMIN"))
            userRepository.save(newAdmin)
            logger.warn("No admin user found in the database, creating new admin with login ${newAdmin.login} and password $rawPassword")
        }
    }

    private fun randomInt() = (Math.random() * 100).toInt()
}