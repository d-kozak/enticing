package cz.vutbr.fit.knot.enticing.webserver.dto

import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import javax.validation.Valid
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class User(
        @field:Positive
        val id: Long = 0,
        @field:Size(min = 5, max = 64)
        val login: String,
        val active: Boolean = true,
        val roles: Set<String> = emptySet(),
        val selectedSettings: Long? = null,
        @field:Valid
        val userSettings: UserSettings = UserSettings()
) {
    val isAdmin: Boolean
        get() = roles.contains("ADMIN")
}

/**
 * Selected settings not handled by this conversion, database access is necessary
 */
fun User.toEntity(searchSettings: SearchSettings? = null): UserEntity = UserEntity(id, login, "", active, roles, userSettings.toEmbeddable(), searchSettings)

fun UserEntity.toUser(): User = User(
        id = this.id,
        login = this.login,
        roles = this.roles,
        active = this.active,
        selectedSettings = this.selectedSettings?.id,
        userSettings = this.userSettings.toDto())

data class UserSettings(
        @field:Positive
        val resultsPerPage: Int = 20
)

fun UserSettings.toEmbeddable() = cz.vutbr.fit.knot.enticing.webserver.entity.UserSettings(resultsPerPage)
fun cz.vutbr.fit.knot.enticing.webserver.entity.UserSettings.toDto() = UserSettings(resultsPerPage)


data class UserCredentials(
        @field:Size(min = 5, max = 64)
        val login: String,
        @field:Size(min = 5, max = 64)
        val password: String
)

data class ChangePasswordCredentials(
        @field:Size(min = 5, max = 64)
        val login: String,
        @field:Size(min = 5, max = 64)
        val oldPassword: String,
        @field:Size(min = 5, max = 64)
        val newPassword: String
)

data class CreateUserRequest(
        @field:Size(min = 5, max = 64)
        val login: String,
        @field:Size(min = 5, max = 64)
        val password: String,
        val roles: Set<String>
)