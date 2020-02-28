package cz.vutbr.fit.knot.enticing.webserver.init

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AddAdminRunner(
        val userRepository: UserRepository,
        val encoder: PasswordEncoder,
        loggerFactory: LoggerFactory
) : ApplicationRunner {

    private val logger = loggerFactory.logger { }

    override fun run(args: ApplicationArguments?) {
        val admins = userRepository.findAllAdmins()
        if (admins.isEmpty()) {
            val rawPassword = "knot12"
            var login = "admin"
            while (userRepository.existsByLogin(login)) {
                login += "${randomInt()}"
            }
            val newAdmin = UserEntity(login = login, encryptedPassword = encoder.encode(rawPassword), roles = setOf("ADMIN"))
            userRepository.save(newAdmin)
            logger.warn("No admin user found in the database, creating new admin with login ${newAdmin.login} and password $rawPassword")
        }
    }

    private fun randomInt() = (Math.random() * 100).toInt()
}