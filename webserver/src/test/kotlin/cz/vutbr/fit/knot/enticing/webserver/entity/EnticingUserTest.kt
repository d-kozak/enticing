package cz.vutbr.fit.knot.enticing.webserver.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


internal class EnticingUserTest {

    @Test
    fun `Constructor test`() {
        val user = EnticingUser("dkozak", "encrypted")
        assertThat(user.login).isEqualTo("dkozak")
        assertThat(user.password).isEqualTo("encrypted")
        assertThat(user.roles).isEmpty()
        assertThat(user.isActive).isTrue()
        assertThat(user.selectedSettings).isNull()
        assertThat(user.id).isZero()
    }

    @Test
    fun `Equals test`() {
        val dkozak = EnticingUser("dkozak")
        val dkozakAgain = EnticingUser("dkozak")
        val someoneElse = EnticingUser("someoneElse")
        assertThat(dkozak).isEqualTo(dkozak)
        assertThat(dkozak).isEqualTo(dkozakAgain)
        assertThat(dkozak).isNotEqualTo(someoneElse)
    }

    @Test
    fun `Hashcode test`() {
        val dkozak = EnticingUser("dkozak")
        val dkozakAgain = EnticingUser("dkozak")
        assertThat(dkozak.hashCode()).isEqualTo(dkozak.hashCode())
        assertThat(dkozak.hashCode()).isEqualTo(dkozakAgain.hashCode())
    }

    @Test
    fun `User details test`() {
        val user = EnticingUser("dkozak", "encrypted").apply { roles = mutableSetOf("USER") }
        val userDetails: UserDetails = user
        assertThat(userDetails.username).isEqualTo("dkozak")
        assertThat(userDetails.password).isEqualTo("encrypted")
        assertThat(userDetails.isEnabled).isTrue()
        assertThat(userDetails.authorities).isEqualTo(setOf(SimpleGrantedAuthority("USER")))

        user.isActive = false
        assertThat(userDetails.isEnabled).isFalse()
    }
}