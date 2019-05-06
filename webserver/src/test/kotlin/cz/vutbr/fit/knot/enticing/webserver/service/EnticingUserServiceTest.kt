package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.webserver.entity.EnticingUser
import cz.vutbr.fit.knot.enticing.webserver.repository.EnticingUserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.core.userdetails.UsernameNotFoundException


internal class EnticingUserServiceTest {

    private val userRepositoryMock = mockk<EnticingUserRepository>()

    @Test
    fun loadUserByUsernameTest() {
        val userService = EnticingUserService(userRepositoryMock)
        every { userRepositoryMock.findByLogin("dkozak") } returns EnticingUser("dkozak")
        every { userRepositoryMock.findByLogin(not("dkozak")) } returns null

        assertThat(userService.loadUserByUsername("dkozak"))
                .isEqualTo(EnticingUser("dkozak"))
        assertThrows<UsernameNotFoundException> { userService.loadUserByUsername("fooo") }

        verify(exactly = 2) { userRepositoryMock.findByLogin(any()) }
    }
}