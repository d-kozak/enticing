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
    private val userService = EnticingUserService(userRepositoryMock)

    @Test
    fun `loadUserByUsername Test`() {
        every { userRepositoryMock.findByLogin("dkozak") } returns EnticingUser("dkozak")
        every { userRepositoryMock.findByLogin(not("dkozak")) } returns null

        assertThat(userService.loadUserByUsername("dkozak"))
                .isEqualTo(EnticingUser("dkozak"))
        assertThrows<UsernameNotFoundException> { userService.loadUserByUsername("fooo") }

        verify(exactly = 2) { userRepositoryMock.findByLogin(any()) }
    }


    @Test
    fun `saveNewUser test`() {

        every { userRepositoryMock.save(EnticingUser("cat123")) } returns EnticingUser()

        val newUser = EnticingUser("cat123")
        userService.saveUser(newUser)
        verify(exactly = 1) { userRepositoryMock.save(EnticingUser("cat123")) }
    }

    @Test
    fun `delete user test`() {
        every { userRepositoryMock.delete(EnticingUser("123")) } returns Unit

        val user = EnticingUser("123")
        userService.deleteUser(user)

        verify(exactly = 1) { userRepositoryMock.delete(EnticingUser("123")) }
    }

    @Test
    fun `update user test`() {
        every { userRepositoryMock.save(EnticingUser("abc")) } returns EnticingUser("abc")

        val user = EnticingUser("abc")
        userService.updateUser(user)

        verify(exactly = 1) { userRepositoryMock.save(EnticingUser("abc")) }
    }

}