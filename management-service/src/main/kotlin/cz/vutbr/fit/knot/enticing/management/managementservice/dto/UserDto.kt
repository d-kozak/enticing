package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.UserEntity
import javax.validation.constraints.Size


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

data class User(
        @field:Size(min = 5, max = 64)
        val login: String,
        val roles: Set<String> = emptySet()
) {
    val isAdmin: Boolean
        get() = roles.contains("ADMIN")
}

/**
 * Selected settings not handled by this conversion, database access is necessary
 */
fun User.toEntity(): UserEntity = UserEntity(login, "", true, roles.toMutableSet())

fun UserEntity.toUser(): User = User(login, roles)