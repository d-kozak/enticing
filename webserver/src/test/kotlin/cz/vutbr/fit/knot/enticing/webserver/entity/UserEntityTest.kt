package cz.vutbr.fit.knot.enticing.webserver.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


internal class UserEntityTest {

    @Test
    fun `Constructor test`() {
        val user = UserEntity(login = "dkozak", encryptedPassword = "encrypted")
        assertThat(user.login).isEqualTo("dkozak")
        assertThat(user.password).isEqualTo("encrypted")
        assertThat(user.roles).isEmpty()
        assertThat(user.active).isTrue()
        assertThat(user.selectedSettings).isNull()
        assertThat(user.id).isZero()
    }

    @Test
    fun `Equals test`() {
        val dkozak = UserEntity(login = "dkozak")
        val dkozakAgain = UserEntity(login = "dkozak")
        val someoneElse = UserEntity(login = "someoneElse")
        assertThat(dkozak).isEqualTo(dkozak)
        assertThat(dkozak).isEqualTo(dkozakAgain)
        assertThat(dkozak).isNotEqualTo(someoneElse)
    }

    @Test
    fun `Hashcode test`() {
        val dkozak = UserEntity(login = "dkozak")
        val dkozakAgain = UserEntity(login = "dkozak")
        assertThat(dkozak.hashCode()).isEqualTo(dkozak.hashCode())
        assertThat(dkozak.hashCode()).isEqualTo(dkozakAgain.hashCode())
    }

    @Test
    fun `User details checks correct user properties test`() {
        val user = UserEntity(login = "dkozak", encryptedPassword = "encrypted", roles = mutableSetOf("USER"))
        val userDetails: UserDetails = user
        assertThat(userDetails.username).isEqualTo("dkozak")
        assertThat(userDetails.password).isEqualTo("encrypted")
        assertThat(userDetails.isEnabled).isTrue()
        assertThat(userDetails.authorities).isEqualTo(setOf(SimpleGrantedAuthority("ROLE_USER")))

        user.active = false
        assertThat(userDetails.isEnabled).isFalse()
    }
}