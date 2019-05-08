package cz.vutbr.fit.knot.enticing.webserver.controller

import com.fasterxml.jackson.databind.ObjectMapper
import cz.vutbr.fit.knot.enticing.webserver.dto.ChangePasswordCredentials
import cz.vutbr.fit.knot.enticing.webserver.dto.User
import cz.vutbr.fit.knot.enticing.webserver.dto.UserCredentials
import cz.vutbr.fit.knot.enticing.webserver.dto.UserSettings
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(secure = false)
@ExtendWith(SpringExtension::class)
internal class UserControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Value("\${api.base.path}") val apiBasePath: String
) {

    @MockBean
    lateinit var userService: EnticingUserService

    @Test
    fun `Signup test`() {
        val user = UserCredentials("Pepa", "123")
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
        val user = User(10, "foo", userSettings = UserSettings(11))
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
        val user = User(11, "aaa")
        val serialized = ObjectMapper().writeValueAsString(user)
        mockMvc.perform(delete("$apiBasePath/user")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        Mockito.verify(userService)
                .deleteUser(user)
        Mockito.clearInvocations(userService)
    }

    @Test
    fun `Delete user test should fail for zero id`() {
        val user = User(0, "aaa")
        val serialized = ObjectMapper().writeValueAsString(user)
        mockMvc.perform(delete("$apiBasePath/user")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(400))

        Mockito.verifyZeroInteractions(userService)
        Mockito.clearInvocations(userService)
    }

    @Test
    fun `Change password test`() {
        val userCredentials = ChangePasswordCredentials("xxx", "oldPass", "newPass")
        val serialized = ObjectMapper().writeValueAsString(userCredentials)
        mockMvc.perform(put("$apiBasePath/user/password")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        Mockito.verify(userService)
                .changePassword(userCredentials)
        Mockito.clearInvocations(userService)
    }
}