package cz.vutbr.fit.knot.enticing.webserver.dto

import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import javax.validation.constraints.NotEmpty

data class User(
        val id: Long = 0,
        @field:NotEmpty
        val login: String,
        val active: Boolean = true,
        val roles: Set<String> = emptySet(),
        val selectedSettings: Long? = null
)


fun User.toEntity(): UserEntity = UserEntity(id, login, "", active, roles, selectedSettings)

fun UserEntity.toUser(): User = User(
        id = this.id,
        login = this.login,
        roles = this.roles,
        active = this.active,
        selectedSettings = this.selectedSettings)


data class UserCredentials(
        @field:NotEmpty
        val login: String,
        @field:NotEmpty
        val password: String
)