package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.webserver.dto.*
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import cz.vutbr.fit.knot.enticing.webserver.entity.UserSettings
import cz.vutbr.fit.knot.enticing.webserver.exception.InsufficientRoleException
import cz.vutbr.fit.knot.enticing.webserver.exception.InvalidPasswordException
import cz.vutbr.fit.knot.enticing.webserver.exception.ValueNotUniqueException
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.SelectedEntityMetadataRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.SelectedMetadataRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import cz.vutbr.fit.knot.enticing.webserver.utils.withAuthentication
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*


internal class EnticingUserServiceTest {

    private val searchSettingsRepositoryMock = mockk<SearchSettingsRepository>()
    private val userRepositoryMock = mockk<UserRepository>()
    private val encoder = BCryptPasswordEncoder(11)
    private val selectedMetadataRepositoryMock = mockk<SelectedMetadataRepository>()
    private val selectedEntityMetadataRepositoryMock = mockk<SelectedEntityMetadataRepository>()
    private val userHolder = CurrentUserHolder(SimpleStdoutLoggerFactory)
    private val userService = EnticingUserService(userRepositoryMock, selectedMetadataRepositoryMock, selectedEntityMetadataRepositoryMock, encoder, searchSettingsRepositoryMock, userHolder, SimpleStdoutLoggerFactory)

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
        every { userRepositoryMock.existsByLogin("cat123") } returns false

        val newUser = UserCredentials("cat123", "123")
        userService.saveUser(newUser)

        verify(exactly = 1) { userRepositoryMock.save(UserEntity(login = "cat123")) }
        verify(exactly = 1) { userRepositoryMock.existsByLogin("cat123") }
        assertThat(userEntityCapture.isCaptured).isTrue()
        val capturedEntity = userEntityCapture.captured
        assertThat(encoder.matches("123", capturedEntity.encryptedPassword)).isTrue()
    }

    @Test
    fun `saveNewUser test should fail login not unique`() {
        every { userRepositoryMock.existsByLogin("cat123") } returns true

        val newUser = UserCredentials("cat123", "123")
        assertThrows<ValueNotUniqueException> { userService.saveUser(newUser) }

        verify(exactly = 1) { userRepositoryMock.existsByLogin("cat123") }
    }


    @Test
    fun `update user test`() {
        val userEntityCapture = slot<UserEntity>()

        val selectedSettings = SearchSettings(id = 20, name = "config1")
        val originalEntity = UserEntity(id = 123, login = "baz", active = true, encryptedPassword = "abc", roles = setOf("R"), selectedSettings = selectedSettings, userSettings = UserSettings(33))
        every { userRepositoryMock.findById(123) } returns Optional.of(originalEntity)
        every { userRepositoryMock.save(capture(userEntityCapture)) } returns UserEntity(login = "abc")

        val user = User(123, "abc", active = false, roles = setOf("R2"), selectedSettings = 20, userSettings = cz.vutbr.fit.knot.enticing.webserver.dto.UserSettings(22))
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
        assertThat(user.selectedSettings).isEqualTo(capturedEntity.selectedSettings?.id)
        assertThat(user.userSettings.resultsPerPage).isEqualTo(capturedEntity.userSettings.resultsPerPage)
        assertThat("abc").isEqualTo(capturedEntity.encryptedPassword)
    }


    @Test
    fun `update user test cannot update another user because not logged in`() {
        assertThrows<IllegalStateException> {
            val user = User(10, "donald", active = false, roles = setOf("R2"), selectedSettings = 11, userSettings = cz.vutbr.fit.knot.enticing.webserver.dto.UserSettings(22))
            userService.updateUser(user)
        }
    }

    @Test
    fun `update user test cannot update another user because not admin`() {
        val originalEntity = UserEntity(id = 123, login = "baz", active = true, encryptedPassword = "abc", roles = setOf("R"), selectedSettings = null, userSettings = UserSettings(33))

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
        val originalEntity = UserEntity(id = 123, login = "baz", active = true, encryptedPassword = "abc", roles = setOf("ADMIN"), selectedSettings = null, userSettings = UserSettings(33))

        every { userRepositoryMock.findById(10) } returns Optional.of(originalEntity)
        every { userRepositoryMock.save(capture(userEntityCapture)) } returns UserEntity(login = "abc")

        val user = User(10, "donald", active = false, roles = setOf("R2"), selectedSettings = null, userSettings = cz.vutbr.fit.knot.enticing.webserver.dto.UserSettings(22))
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
        every { userRepositoryMock.findById(123) } returns Optional.of(userEntity)

        withAuthentication(userEntity) {
            userService.deleteUser(123)
        }

        verify(exactly = 1) { userRepositoryMock.delete(userEntity) }
        verify(exactly = 1) { userRepositoryMock.findById(123) }
    }

    @Test
    fun `delete user test cannot delete user because not logged in`() {
        val user = User(0, "123")
        every { userRepositoryMock.findById(0) } returns Optional.of(user.toEntity())
        assertThrows<IllegalStateException> {
            userService.deleteUser(0)
        }
    }

    @Test
    fun `delete user test cannot delete user because not admin`() {
        val user = User(0, "123")
        val auth = UserEntity(id = 2, login = "john")
        every { userRepositoryMock.findById(0) } returns Optional.of(user.toEntity())
        withAuthentication(auth) {
            assertThrows<InsufficientRoleException> {
                userService.deleteUser(0)
            }
        }
    }

    @Test
    fun `delete user test can delete different user because is admin`() {
        val admin = UserEntity(id = 123, login = "123", roles = setOf("ADMIN"))
        val ruda = User(id = 2, login = "ruda")
        every { userRepositoryMock.findById(2) } returns Optional.of(ruda.toEntity())
        every { userRepositoryMock.delete(ruda.toEntity()) } returns Unit


        withAuthentication(admin) {
            userService.deleteUser(2)
        }

        verify(exactly = 1) { userRepositoryMock.delete(ruda.toEntity()) }
        verify(exactly = 1) { userRepositoryMock.findById(2) }
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
        assertThrows<IllegalStateException> {
            userService.changePassword(ChangePasswordCredentials("testLogin", "oldPass", "newPass"))
        }
    }


    @Test
    fun `change password invalid old password should fail`() {
        val userEntityCapture = slot<UserEntity>()

        val userEntity = UserEntity(login = "testLogin", encryptedPassword = encoder.encode("oldPass"))
        every { userRepositoryMock.findByLogin("testLogin") } returns userEntity
        every { userRepositoryMock.save(capture(userEntityCapture)) } returns UserEntity(login = "testLogin")

        withAuthentication(userEntity) {
            assertThrows<InvalidPasswordException> {
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
            assertThat(userHolder.getCurrentUser())
                    .isEqualTo(dummyUserEntity.toUser())
        }
    }

    @Test
    fun `Select settings test`() {
        val userCapture = slot<UserEntity>()
        val user = UserEntity(id = 11, login = "pepa")
        every { searchSettingsRepositoryMock.findById(42) } returns Optional.of(SearchSettings(id = 42))
        every { userRepositoryMock.findById(11) } returns Optional.of(user)
        every { userRepositoryMock.save(capture(userCapture)) } returns user
        withAuthentication(user) {
            userService.selectSettings(42)
        }
        verify(exactly = 1) { searchSettingsRepositoryMock.findById(42) }
        verify(exactly = 1) { userRepositoryMock.findById(11) }
        verify(exactly = 1) { userRepositoryMock.save(UserEntity(login = "pepa")) }
        assertThat(user.selectedSettings).isNotNull()
    }

    @Test
    fun `Current user should be null if the entity is not of type UserEntity`() {
        val authentication: Authentication = UsernamePasswordAuthenticationToken("someString", "foo")
        assertThat(SecurityContextHolder.getContext().authentication).isNull()
        SecurityContextHolder.getContext().authentication = authentication
        try {
            val user = userHolder.getCurrentUser()
        } finally {
            SecurityContextHolder.getContext().authentication = null
        }
    }

    @Test
    fun `Get all users`() {
        val users = listOf(UserEntity(login = "ferda"), UserEntity(login = "brouk pytlik"), UserEntity(login = "krakonos"))
        every { userRepositoryMock.findAll() } returns users

        assertThat(userService.getAllUsers())
                .isEqualTo(users.map(UserEntity::toUser))

        verify(exactly = 1) { userRepositoryMock.findAll() }
    }

    @Test
    fun `Create new user test should create admin`() {
        val user = CreateUserRequest("john5", "foo12", setOf("ADMIN"))
        val userCapture = slot<UserEntity>()
        every { userRepositoryMock.save(capture(userCapture)) } returns UserEntity(login = "foooo")
        every { userRepositoryMock.existsByLogin("john5") } returns false

        userService.saveUser(user)

        verify(exactly = 1) { userRepositoryMock.save(UserEntity(login = "john5")) }
        verify(exactly = 1) { userRepositoryMock.existsByLogin("john5") }
        assertThat(userCapture.isCaptured).isTrue()
        assertThat(encoder.matches("foo12", userCapture.captured.encryptedPassword)).isTrue()
        assertThat(userCapture.captured.roles.contains("ADMIN")).isTrue()
    }

    @Test
    fun `Create new user test should fail because login is taken`() {
        val user = CreateUserRequest("john5", "foo12", setOf("ADMIN"))

        every { userRepositoryMock.existsByLogin("john5") } returns true
        assertThrows<ValueNotUniqueException> { userService.saveUser(user) }


        verify(exactly = 1) { userRepositoryMock.existsByLogin("john5") }

    }

    @Test
    fun `Create new user test should create normal user`() {
        val user = CreateUserRequest("john5", "foo12", setOf())
        val userCapture = slot<UserEntity>()
        every { userRepositoryMock.save(capture(userCapture)) } returns UserEntity(login = "foooo")
        every { userRepositoryMock.existsByLogin("john5") } returns false

        userService.saveUser(user)

        verify(exactly = 1) { userRepositoryMock.save(UserEntity(login = "john5")) }
        verify(exactly = 1) { userRepositoryMock.existsByLogin("john5") }
        assertThat(userCapture.isCaptured).isTrue()
        assertThat(encoder.matches("foo12", userCapture.captured.encryptedPassword)).isTrue()
        assertThat(userCapture.captured.roles.contains("ADMIN")).isFalse()
    }

}