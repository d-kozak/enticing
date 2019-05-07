package cz.vutbr.fit.knot.enticing.webserver.repository

import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByLogin(login: String): UserEntity?
}