package cz.vutbr.fit.knot.enticing.management.managementservice.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.validation.constraints.Size

@Entity
class UserEntity(
        @field:Id
        @field:Size(min = 5, max = 64)
        var login: String,
        var encryptedPassword: String,
        var active: Boolean = true,
        @field:ElementCollection(fetch = FetchType.EAGER)
        var roles: MutableSet<String> = mutableSetOf()

) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = roles
            .asSequence()
            .map { SimpleGrantedAuthority("ROLE_$it") }
            .toMutableSet()

    override fun isEnabled(): Boolean = active

    override fun getUsername(): String = login

    override fun isCredentialsNonExpired(): Boolean = active

    override fun getPassword(): String = encryptedPassword

    override fun isAccountNonExpired(): Boolean = active

    override fun isAccountNonLocked(): Boolean = active

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserEntity) return false

        if (login != other.login) return false

        return true
    }

    override fun hashCode(): Int {
        return login.hashCode()
    }

    override fun toString(): String {
        return "UserEntity(login='$login', encryptedPassword='$encryptedPassword', active=$active, roles=$roles)"
    }


}