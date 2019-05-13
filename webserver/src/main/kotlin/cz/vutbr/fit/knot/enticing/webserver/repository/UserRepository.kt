package cz.vutbr.fit.knot.enticing.webserver.repository

import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByLogin(login: String): UserEntity?
    @Query("select user from UserEntity user where 'ADMIN' member of user.roles")
    fun findAllAdmins(): List<UserEntity>
    @Modifying(clearAutomatically = true)
    @Query("update UserEntity u set u.selectedSettings=null where u.selectedSettings = :settings")
    fun detachSettingsFromAllUsers(@Param("settings") settings: SearchSettings)

    fun existsByLogin(s: String): Boolean
}