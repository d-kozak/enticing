package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.webserver.dto.ChangePasswordCredentials
import cz.vutbr.fit.knot.enticing.webserver.dto.User
import cz.vutbr.fit.knot.enticing.webserver.dto.UserCredentials
import cz.vutbr.fit.knot.enticing.webserver.dto.toUser
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import cz.vutbr.fit.knot.enticing.webserver.entity.UserSettings
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*


internal class EnticingUserServiceTest {

    private val userRepositoryMock = mockk<UserRepository>()
    private val encoder = BCryptPasswordEncoder(11)
    private val userService = EnticingUserService(userRepositoryMock, encoder)

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
        val userEntityCapture = slot<UserEntity>()
        every { userRepositoryMock.save(capture(userEntityCapture)) } returns UserEntity()

        val newUser = UserCredentials("cat123", "123")
        userService.saveUser(newUser)

        verify(exactly = 1) { userRepositoryMock.save(UserEntity(login = "cat123")) }
        assertThat(userEntityCapture.isCaptured).isTrue()
        val capturedEntity = userEntityCapture.captured
        assertThat(encoder.matches("123", capturedEntity.encryptedPassword)).isTrue()
    }

    @Test
    fun `update user test`() {
        val userEntityCapture = slot<UserEntity>()

        val originalEntity = UserEntity(id = 123, login = "baz", active = true, encryptedPassword = "abc", roles = setOf("R"), selectedSettings = 10, userSettings = UserSettings(33))
        every { userRepositoryMock.findById(123) } returns Optional.of(originalEntity)
        every { userRepositoryMock.save(capture(userEntityCapture)) } returns UserEntity(login = "abc")

        val user = User(123, "abc", active = false, roles = setOf("R2"), selectedSettings = 11, userSettings = cz.vutbr.fit.knot.enticing.webserver.dto.UserSettings(22))
        userService.updateUser(user)

        verify(exactly = 1) { userRepositoryMock.findById(123) }
        verify(exactly = 1) { userRepositoryMock.save(UserEntity(login = "abc")) }
        assertThat(userEntityCapture.isCaptured).isTrue()
        val capturedEntity = userEntityCapture.captured
        assertThat(user.id).isEqualTo(capturedEntity.id)
        assertThat(user.login).isEqualTo(capturedEntity.login)
        assertThat(user.active).isEqualTo(capturedEntity.active)
        assertThat(user.roles).isEqualTo(capturedEntity.roles)
        assertThat(user.selectedSettings).isEqualTo(capturedEntity.selectedSettings)
        assertThat(user.userSettings.resultsPerPage).isEqualTo(capturedEntity.userSettings.resultsPerPage)
        assertThat("abc").isEqualTo(capturedEntity.encryptedPassword)
    }

    @Test
    fun `delete user test`() {
        every { userRepositoryMock.delete(UserEntity(id = 123, login = "123")) } returns Unit

        val user = User(0, "123")
        userService.deleteUser(user)

        verify(exactly = 1) { userRepositoryMock.delete(UserEntity(login = "123")) }
    }

    @Test
    fun `change password test`() {
        val userEntityCapture = slot<UserEntity>()

        every { userRepositoryMock.findByLogin("testLogin") } returns UserEntity(login = "testLogin", encryptedPassword = encoder.encode("oldPass"))
        every { userRepositoryMock.save(capture(userEntityCapture)) } returns UserEntity(login = "testLogin")


        userService.changePassword(ChangePasswordCredentials("testLogin", "oldPass", "newPass"))

        verify(exactly = 1) { userRepositoryMock.findByLogin("testLogin") }
        verify(exactly = 1) { userRepositoryMock.save(UserEntity(login = "testLogin")) }


        assertThat(userEntityCapture.isCaptured).isTrue()
        val capturedEntity = userEntityCapture.captured
        assertThat(capturedEntity.login).isEqualTo("testLogin")
        assertThat(encoder.matches("newPass", capturedEntity.encryptedPassword)).isTrue()
    }


    @Test
    fun `change password invalid old password should fail`() {
        val userEntityCapture = slot<UserEntity>()

        every { userRepositoryMock.findByLogin("testLogin") } returns UserEntity(login = "testLogin", encryptedPassword = encoder.encode("oldPass"))
        every { userRepositoryMock.save(capture(userEntityCapture)) } returns UserEntity(login = "testLogin")

        assertThrows<IllegalArgumentException> {
            userService.changePassword(ChangePasswordCredentials("testLogin", "invalid", "newPass"))
        }

        verify(exactly = 1) { userRepositoryMock.findByLogin("testLogin") }
    }


    @Test
    fun `Get user test`() {
        val dummyUserEntity = UserEntity(login = "Honza")
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(dummyUserEntity, "A")
        assertThat(userService.getCurrentUser())
                .isEqualTo(dummyUserEntity.toUser())
        SecurityContextHolder.getContext().authentication = null

    }
}