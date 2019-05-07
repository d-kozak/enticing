package cz.vutbr.fit.knot.enticing.webserver.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class UserEntity(
        @Id
        @GeneratedValue
        var id: Long = 0,
        var login: String = "",
        var encryptedPassword: String = "",
        var active: Boolean = true,
        @ElementCollection
        var roles: Set<String> = setOf(),
        var selectedSettings: Long? = null
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = roles
            .asSequence()
            .map { SimpleGrantedAuthority(it) }
            .toMutableSet()


    override fun isEnabled(): Boolean = active


    override fun getUsername(): String = login


    override fun getPassword(): String = encryptedPassword

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserEntity) return false

        if (login != other.login) return false

        return true
    }

    override fun hashCode(): Int {
        return login.hashCode()
    }


    override fun isCredentialsNonExpired(): Boolean = active

    override fun isAccountNonExpired(): Boolean = active


    override fun isAccountNonLocked(): Boolean = active

    override fun toString(): String {
        return "UserEntity(id=$id, login='$login', active=$active, roles=$roles, selectedSettings=$selectedSettings)"
    }
}