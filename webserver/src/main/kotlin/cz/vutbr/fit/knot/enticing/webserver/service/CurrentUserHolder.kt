package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.webserver.dto.User
import cz.vutbr.fit.knot.enticing.webserver.dto.toUser
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CurrentUserHolder(loggerFactory: LoggerFactory) {

    val logger = loggerFactory.logger { }

    fun requireLoggedInUser() = getCurrentUser()
            ?: throw IllegalStateException("This operation require user to be logged in")

    fun getCurrentUser(): User? {
        val principal = SecurityContextHolder.getContext().authentication?.principal ?: return null
        return if (principal is UserEntity) principal.toUser() else {
            logger.info("Stored principal $principal in not of type UserEntity")
            null
        }
    }

}

