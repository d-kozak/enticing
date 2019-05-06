package cz.vutbr.fit.knot.enticing.webserver.repository

import cz.vutbr.fit.knot.enticing.webserver.entity.EnticingUser
import org.springframework.data.jpa.repository.JpaRepository

interface EnticingUserRepository : JpaRepository<EnticingUser, Long> {
    fun findByLogin(login: String): EnticingUser?
}