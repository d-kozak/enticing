package cz.vutbr.fit.knot.enticing.webserver.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
class UserEntity(
        id: Long = 0,
        login: String = "",
        encryptedPassword: String = "",
        active: Boolean = true,
        roles: Set<String> = setOf(),
        selectedSettings: Long? = null
) : UserDetails {

    @Id
    @GeneratedValue
    var id: Long = id
    @NotEmpty
    @Column(unique = true)
    var login: String = login
    @NotEmpty
    var encryptedPassword: String = encryptedPassword
    var active: Boolean = active
    @ElementCollection
    var roles: Set<String> = roles
    var selectedSettings: Long? = selectedSettings


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