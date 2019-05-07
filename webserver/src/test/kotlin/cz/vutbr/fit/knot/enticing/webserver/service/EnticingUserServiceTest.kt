package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.webserver.dto.User
import cz.vutbr.fit.knot.enticing.webserver.dto.UserWithPassword
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.core.userdetails.UsernameNotFoundException


internal class EnticingUserServiceTest {

    private val userRepositoryMock = mockk<UserRepository>()
    private val userService = EnticingUserService(userRepositoryMock)

    @Test
    fun `loadUserByUsername Test`() {
        every { userRepositoryMock.findByLogin("dkozak") } returns UserEntity(login = "dkozak")
        every { userRepositoryMock.findByLogin(not("dkozak")) } returns null

        assertThat(userService.loadUserByUsername("dkozak"))
                .isEqualTo(UserEntity(login = "dkozak"))
        assertThrows<UsernameNotFoundException> { userService.loadUserByUsername("fooo") }

        verify(exactly = 2) { userRepositoryMock.findByLogin(any()) }
    }


    @Test
    fun `saveNewUser test`() {

        every { userRepositoryMock.save(UserEntity(login = "cat123")) } returns UserEntity(login = "cat123")

        val newUser = UserWithPassword(0, "cat123", "123")
        userService.saveUser(newUser)
        verify(exactly = 1) { userRepositoryMock.save(UserEntity(login = "cat123")) }
    }

    @Test
    fun `delete user test`() {
        every { userRepositoryMock.delete(UserEntity(login = "123")) } returns Unit

        val user = User(0, "123")
        userService.deleteUser(user)

        verify(exactly = 1) { userRepositoryMock.delete(UserEntity(login = "123")) }
    }

    @Test
    fun `update user test`() {
        every { userRepositoryMock.save(UserEntity(login = "abc")) } returns UserEntity(login = "abc")

        val user = User(0, "abc")
        userService.updateUser(user)

        verify(exactly = 1) { userRepositoryMock.save(UserEntity(login = "abc")) }
    }

}