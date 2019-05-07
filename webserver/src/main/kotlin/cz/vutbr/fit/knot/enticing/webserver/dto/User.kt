package cz.vutbr.fit.knot.enticing.webserver.dto

import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity

open class User(
        val id: Long = 0,
        val login: String = "",
        val active: Boolean = true,
        val roles: Set<String> = emptySet(),
        val selectedSettings: Long? = null
) {

    open fun toEntity(): UserEntity = UserEntity(id, login, "", active, roles, selectedSettings)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false
        if (login != other.login) return false
        if (active != other.active) return false
        if (roles != other.roles) return false
        if (selectedSettings != other.selectedSettings) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + login.hashCode()
        result = 31 * result + active.hashCode()
        result = 31 * result + roles.hashCode()
        result = 31 * result + (selectedSettings?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "User(id=$id, login='$login', active=$active, roles=$roles, selectedSettings=$selectedSettings)"
    }

}

class UserWithPassword(id: Long = 0,
                       login: String = "",
                       val password: String = "",
                       active: Boolean = true,
                       roles: Set<String> = emptySet(),
                       selectedSettings: Long? = null) : User(id, login, active, roles, selectedSettings) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserWithPassword) return false
        if (!super.equals(other)) return false

        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserWithPassword(id=$id, login='$login',password='$password',active=$active, roles=$roles, selectedSettings=$selectedSettings)"
    }

    override fun toEntity(): UserEntity = super.toEntity().also { it.encryptedPassword = this.password }
}

fun UserEntity.toUser(): User = User(
        id = this.id,
        login = this.login,
        roles = this.roles,
        active = this.active,
        selectedSettings = this.selectedSettings
)