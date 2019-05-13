package cz.vutbr.fit.knot.enticing.webserver.controller

import com.fasterxml.jackson.databind.ObjectMapper
import cz.vutbr.fit.knot.enticing.webserver.dto.*
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import cz.vutbr.fit.knot.enticing.webserver.service.QueryService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(secure = false)
@ExtendWith(SpringExtension::class)
internal class UserControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Value("\${api.base.path}") val apiBasePath: String
) {

    @MockBean
    lateinit var userService: EnticingUserService

    @MockBean
    lateinit var userRepository: UserRepository

    @MockBean
    lateinit var searchSettingsRepository: SearchSettingsRepository

    @MockBean
    lateinit var queryService: QueryService

    @Test
    fun `Signup test`() {
        val user = UserCredentials("Pepa1", "12345")
        val serialized = ObjectMapper().writeValueAsString(user)
        mockMvc.perform(post("$apiBasePath/user")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        Mockito.verify(userService)
                .saveUser(user)
        Mockito.clearInvocations(userService)
    }

    @Test
    fun `Get user test`() {
        val dummyUser = User(login = "ferda")
        val serialized = ObjectMapper().writeValueAsString(dummyUser)
        Mockito.`when`(userService.currentUser).thenReturn(dummyUser)

        mockMvc.perform(get("$apiBasePath/user"))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(serialized))

        Mockito.verify(userService).currentUser
        Mockito.clearInvocations(userService)
    }

    @Test
    fun `Empty login for signup should fail`() {
        val user = UserCredentials("", "123")
        val serialized = ObjectMapper().writeValueAsString(user)
        mockMvc.perform(post("$apiBasePath/user")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(400))

        Mockito.verifyZeroInteractions(userService)
        Mockito.clearInvocations(userService)
    }

    @Test
    fun `Empty password for signup should fail`() {
        val user = UserCredentials("123", "")
        val serialized = ObjectMapper().writeValueAsString(user)
        mockMvc.perform(post("$apiBasePath/user")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(400))

        Mockito.verifyZeroInteractions(userService)
        Mockito.clearInvocations(userService)
    }


    @Test
    fun `Update user test`() {
        val user = User(10, "foo12", userSettings = UserSettings(11))
        val serialized = ObjectMapper().writeValueAsString(user)
        mockMvc.perform(put("$apiBasePath/user")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
        Mockito.verify(userService)
                .updateUser(user)
        Mockito.clearInvocations(userService)
    }

    @Test
    fun `Update user should fail for zero id`() {
        val user = User(0, "foo", userSettings = UserSettings(11))
        val serialized = ObjectMapper().writeValueAsString(user)
        mockMvc.perform(put("$apiBasePath/user")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(400))

        Mockito.verifyZeroInteractions(userService)
        Mockito.clearInvocations(userService)
    }

    @Test
    fun `Update user should fail for empty login`() {
        val user = User(11, "", userSettings = UserSettings(11))
        val serialized = ObjectMapper().writeValueAsString(user)
        mockMvc.perform(put("$apiBasePath/user")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(400))

        Mockito.verifyZeroInteractions(userService)
        Mockito.clearInvocations(userService)
    }


    @Test
    fun `Update user should fail for negative results per page`() {
        val user = User(11, "aaa", userSettings = UserSettings(-1))
        val serialized = ObjectMapper().writeValueAsString(user)
        mockMvc.perform(put("$apiBasePath/user")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(400))

        Mockito.verifyZeroInteractions(userService)
        Mockito.clearInvocations(userService)
    }


    @Test
    fun `Delete user test`() {
        mockMvc.perform(delete("$apiBasePath/user/11"))
                .andExpect(status().isOk)

        Mockito.verify(userService)
                .deleteUser(11)
        Mockito.clearInvocations(userService)
    }

    @Test
    fun `Change password test`() {
        val userCredentials = ChangePasswordCredentials("xxxxx", "oldPass", "newPass")
        val serialized = ObjectMapper().writeValueAsString(userCredentials)
        mockMvc.perform(put("$apiBasePath/user/password")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        Mockito.verify(userService)
                .changePassword(userCredentials)
        Mockito.clearInvocations(userService)
    }

    @Test
    fun `Change password test should pass for empty old password can can change it this way`() {
        val userCredentials = ChangePasswordCredentials("xxxxx", "NULL_PASS", "newPass")
        val serialized = ObjectMapper().writeValueAsString(userCredentials)
        mockMvc.perform(put("$apiBasePath/user/password")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        Mockito.verify(userService)
                .changePassword(userCredentials)
        Mockito.clearInvocations(userService)
    }

    @Test
    fun `Change password test should fail for empty login`() {
        val userCredentials = ChangePasswordCredentials("", "oldPass", "newPass")
        val serialized = ObjectMapper().writeValueAsString(userCredentials)
        mockMvc.perform(put("$apiBasePath/user/password")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(400))

    }

    @Test
    fun `Change password test should fail for empty new password`() {
        val userCredentials = ChangePasswordCredentials("login", "oldPass", "")
        val serialized = ObjectMapper().writeValueAsString(userCredentials)
        mockMvc.perform(put("$apiBasePath/user/password")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(400))

    }

    @Test
    fun `Get all users`() {
        val users = listOf(User(login = "ferda"), User(login = "brouk pytlik"), User(login = "krakonos"))
        val serialized = ObjectMapper().writeValueAsString(users)
        Mockito.`when`(userService.getAllUsers()).thenReturn(users)

        mockMvc.perform(get("$apiBasePath/user/all"))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(serialized))

        Mockito.verify(userService).getAllUsers()
        Mockito.clearInvocations(userService)
    }

    @Test
    fun `Create new user`() {
        val user = CreateUserRequest("john5", "foo12", setOf())
        val userDto = User(1, "john5", true, setOf())
        Mockito.`when`(userService.saveUser(user)).thenReturn(userDto)
        mockMvc.perform(post("$apiBasePath/user/add").content(user.toJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().string(userDto.toJson()))
        Mockito.verify(userService).saveUser(user)
        Mockito.clearInvocations(userService)
    }

    @Test
    fun `Create new user should fail for invalid login`() {
        val user = CreateUserRequest("john", "foo12", setOf())
        mockMvc.perform(post("$apiBasePath/user/add").content(user.toJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(400))
        Mockito.verifyZeroInteractions(userService)
        Mockito.clearInvocations(userService)

    }

    @Test
    fun `Create new user should fail for invalid password`() {
        val user = CreateUserRequest("john5", "foo1", setOf())
        mockMvc.perform(post("$apiBasePath/user/add").content(user.toJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(400))
        Mockito.verifyZeroInteractions(userService)
        Mockito.clearInvocations(userService)

    }
}