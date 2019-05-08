package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.webserver.dto.*
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import cz.vutbr.fit.knot.enticing.webserver.entity.UserSettings
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import cz.vutbr.fit.knot.enticing.webserver.utils.withAuthentication
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        withAuthentication(originalEntity) {
            userService.updateUser(user)
        }

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
    fun `update user test cannot update another user because not logged in`() {
        assertThrows<InsufficientRoleException> {
            val user = User(10, "donald", active = false, roles = setOf("R2"), selectedSettings = 11, userSettings = cz.vutbr.fit.knot.enticing.webserver.dto.UserSettings(22))
            userService.updateUser(user)
        }
    }

    @Test
    fun `update user test cannot update another user because not admin`() {
        val originalEntity = UserEntity(id = 123, login = "baz", active = true, encryptedPassword = "abc", roles = setOf("R"), selectedSettings = 10, userSettings = UserSettings(33))

        withAuthentication(originalEntity) {
            assertThrows<InsufficientRoleException> {
                val user = User(10, "donald", active = false, roles = setOf("R2"), selectedSettings = 11, userSettings = cz.vutbr.fit.knot.enticing.webserver.dto.UserSettings(22))
                userService.updateUser(user)
            }
        }

    }

    @Test
    fun `update user test can update another entity when admin`() {
        val userEntityCapture = slot<UserEntity>()
        val originalEntity = UserEntity(id = 123, login = "baz", active = true, encryptedPassword = "abc", roles = setOf("ADMIN"), selectedSettings = 10, userSettings = UserSettings(33))

        every { userRepositoryMock.findById(10) } returns Optional.of(originalEntity)
        every { userRepositoryMock.save(capture(userEntityCapture)) } returns UserEntity(login = "abc")

        val user = User(10, "donald", active = false, roles = setOf("R2"), selectedSettings = 11, userSettings = cz.vutbr.fit.knot.enticing.webserver.dto.UserSettings(22))
        withAuthentication(originalEntity) {
            userService.updateUser(user)
        }

        verify(exactly = 1) { userRepositoryMock.findById(10) }
        verify(exactly = 1) { userRepositoryMock.save(UserEntity(login = "donald")) }
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
        val userEntity = UserEntity(id = 123, login = "123")
        every { userRepositoryMock.delete(userEntity) } returns Unit

        withAuthentication(userEntity) {
            val user = userEntity.toUser()
            userService.deleteUser(user)
        }

        verify(exactly = 1) { userRepositoryMock.delete(userEntity) }
    }

    @Test
    fun `delete user test cannot delete user with different id because not logged in`() {
        val user = User(0, "123")
        assertThrows<InsufficientRoleException> {
            userService.deleteUser(user)
        }
    }

    @Test
    fun `delete user test cannot delete user because not admin`() {
        val user = User(0, "123")
        val auth = UserEntity(id = 2, login = "john")
        withAuthentication(auth) {
            assertThrows<InsufficientRoleException> {
                userService.deleteUser(user)
            }
        }
    }

    @Test
    fun `delete user test can delete different user because is admin`() {
        val admin = UserEntity(id = 123, login = "123", roles = setOf("ADMIN"))
        val ruda = User(id = 2, login = "ruda")
        every { userRepositoryMock.delete(ruda.toEntity()) } returns Unit


        withAuthentication(admin) {
            userService.deleteUser(ruda)
        }

        verify(exactly = 1) { userRepositoryMock.delete(ruda.toEntity()) }
    }


    @Test
    fun `change password test`() {
        val userEntityCapture = slot<UserEntity>()

        val userEntity = UserEntity(id = 7, login = "testLogin", encryptedPassword = encoder.encode("oldPass"))
        every { userRepositoryMock.findByLogin("testLogin") } returns userEntity
        every { userRepositoryMock.save(capture(userEntityCapture)) } returns UserEntity(login = "testLogin")

        withAuthentication(userEntity) {
            userService.changePassword(ChangePasswordCredentials("testLogin", "oldPass", "newPass"))
        }

        verify(exactly = 1) { userRepositoryMock.findByLogin("testLogin") }
        verify(exactly = 1) { userRepositoryMock.save(UserEntity(login = "testLogin")) }


        assertThat(userEntityCapture.isCaptured).isTrue()
        val capturedEntity = userEntityCapture.captured
        assertThat(capturedEntity.login).isEqualTo("testLogin")
        assertThat(encoder.matches("newPass", capturedEntity.encryptedPassword)).isTrue()
    }

    @Test
    fun `change password should fail not logged in`() {
        every { userRepositoryMock.findByLogin("testLogin") } returns UserEntity(login = "testLogin", encryptedPassword = encoder.encode("oldPass"))
        assertThrows<InsufficientRoleException> {
            userService.changePassword(ChangePasswordCredentials("testLogin", "oldPass", "newPass"))
        }
        verify(exactly = 1) { userRepositoryMock.findByLogin("testLogin") }
    }


    @Test
    fun `change password invalid old password should fail`() {
        val userEntityCapture = slot<UserEntity>()

        val userEntity = UserEntity(login = "testLogin", encryptedPassword = encoder.encode("oldPass"))
        every { userRepositoryMock.findByLogin("testLogin") } returns userEntity
        every { userRepositoryMock.save(capture(userEntityCapture)) } returns UserEntity(login = "testLogin")

        withAuthentication(userEntity) {
            assertThrows<IllegalArgumentException> {
                userService.changePassword(ChangePasswordCredentials("testLogin", "invalid", "newPass"))
            }
        }

        verify(exactly = 1) { userRepositoryMock.findByLogin("testLogin") }
    }

    @Test
    fun `change password test admin can change password even without knowing the original one`() {
        val userEntityCapture = slot<UserEntity>()

        val auth = UserEntity(id = 7, login = "testLogin", roles = setOf("ADMIN"), encryptedPassword = encoder.encode("oldPass"))
        every { userRepositoryMock.findByLogin("anotherUser") } returns UserEntity(id = 10, login = "anotherUser")
        every { userRepositoryMock.save(capture(userEntityCapture)) } returns UserEntity(login = "testLogin")

        withAuthentication(auth) {
            userService.changePassword(ChangePasswordCredentials("anotherUser", "oldPass", "newPass"))
        }

        verify(exactly = 1) { userRepositoryMock.findByLogin("anotherUser") }
        verify(exactly = 1) { userRepositoryMock.save(UserEntity(login = "anotherUser")) }


        assertThat(userEntityCapture.isCaptured).isTrue()
        val capturedEntity = userEntityCapture.captured
        assertThat(capturedEntity.login).isEqualTo("anotherUser")
        assertThat(encoder.matches("newPass", capturedEntity.encryptedPassword)).isTrue()
    }


    @Test
    fun `Get user test`() {
        val dummyUserEntity = UserEntity(login = "Honza")
        withAuthentication(dummyUserEntity) {
            assertThat(userService.currentUser)
                    .isEqualTo(dummyUserEntity.toUser())
        }
    }
}