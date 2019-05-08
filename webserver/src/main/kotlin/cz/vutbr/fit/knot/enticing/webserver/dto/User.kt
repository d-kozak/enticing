package cz.vutbr.fit.knot.enticing.webserver.dto

import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class User(
        @field:Positive
        val id: Long = 0,
        @field:NotEmpty
        val login: String,
        val active: Boolean = true,
        val roles: Set<String> = emptySet(),
        val selectedSettings: Long? = null,
        @field:Valid
        val userSettings: UserSettings = UserSettings()
)

fun User.toEntity(): UserEntity = UserEntity(id, login, "", active, roles, selectedSettings, userSettings.toEmbeddable())
fun UserEntity.toUser(): User = User(
        id = this.id,
        login = this.login,
        roles = this.roles,
        active = this.active,
        selectedSettings = this.selectedSettings,
        userSettings = this.userSettings.toDto())

data class UserSettings(
        @field:Positive
        val resultsPerPage: Int = 20
)

fun UserSettings.toEmbeddable() = cz.vutbr.fit.knot.enticing.webserver.entity.UserSettings(resultsPerPage)
fun cz.vutbr.fit.knot.enticing.webserver.entity.UserSettings.toDto() = UserSettings(resultsPerPage)


data class UserCredentials(
        @field:NotEmpty
        val login: String,
        @field:NotEmpty
        val password: String
)

data class ChangePasswordCredentials(
        @field:NotEmpty
        val login: String,
        @field:NotEmpty
        val oldPassword: String,
        @field:NotEmpty
        val newPassword: String
)