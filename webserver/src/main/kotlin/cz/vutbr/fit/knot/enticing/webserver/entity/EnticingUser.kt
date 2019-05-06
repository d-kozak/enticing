package cz.vutbr.fit.knot.enticing.webserver.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class EnticingUser(login: String = "", password: String = "") : UserDetails {
    @Id
    @GeneratedValue
    var id: Long = 0
    var login: String = login
    var encryptedPassword: String = password
    var isActive: Boolean = true
    @ElementCollection
    var roles: MutableSet<String> = mutableSetOf()
    var selectedSettings: Long? = null


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = roles
            .asSequence()
            .map { SimpleGrantedAuthority(it) }
            .toMutableSet()


    override fun isEnabled(): Boolean = isActive

    override fun getUsername(): String = login

    override fun getPassword(): String = encryptedPassword

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EnticingUser) return false

        if (login != other.login) return false

        return true
    }

    override fun hashCode(): Int {
        return login.hashCode()
    }


    override fun isCredentialsNonExpired(): Boolean {
        throw NotImplementedError("An operation is not implemented")
    }

    override fun isAccountNonExpired(): Boolean {
        throw NotImplementedError("An operation is not implemented")
    }

    override fun isAccountNonLocked(): Boolean {
        throw NotImplementedError("An operation is not implemented")
    }

    override fun toString(): String {
        return "EnticingUser(id=$id, login='$login', isActive=$isActive, roles=$roles, selectedSettings=$selectedSettings)"
    }
}