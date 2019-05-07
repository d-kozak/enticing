package cz.vutbr.fit.knot.enticing.webserver.config

import com.fasterxml.jackson.databind.ObjectMapper
import cz.vutbr.fit.knot.enticing.webserver.dto.UserWithPassword
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@Import()
@ExtendWith(SpringExtension::class)
internal class SecurityConfigTest(
        @Autowired val mockMvc: MockMvc,
        @Autowired val encoder: PasswordEncoder,
        @Value("\${api.base.path}") val apiBasePath: String
) {

    @MockBean
    lateinit var userService: EnticingUserService

    @Nested
    inner class Login {

        @Test
        fun `Try to login with valid username`() {
            Mockito.`when`(userService.loadUserByUsername("dkozak")).thenReturn(UserEntity(login = "dkozak", encryptedPassword = encoder.encode("pass")))

            mockMvc.perform(post("$apiBasePath/login")
                    .param("username", "dkozak")
                    .param("password", "pass")
            ).andExpect(status().isOk)

            Mockito.verify(userService).loadUserByUsername("dkozak")
            Mockito.clearInvocations(userService)
        }

        @Test
        fun `Try to login with invalid credentials`() {
            Mockito.`when`(userService.loadUserByUsername("dkozak")).thenReturn(UserEntity(login = "dkozak", encryptedPassword = encoder.encode("different")))

            mockMvc.perform(post("$apiBasePath/login")
                    .param("username", "dkozak")
                    .param("password", "foo")
            ).andExpect(status().`is`(401))

            Mockito.verify(userService).loadUserByUsername("dkozak")
            Mockito.clearInvocations(userService)
        }


        @Test
        fun `Auth should fail when no credentials are sent`() {
            mockMvc.perform(post("$apiBasePath/login"))
                    .andExpect(status().`is`(401))
        }

        @Test
        fun `Protected urls should fail with 401`() {
            val urls = setOf("$apiBasePath/user-settings", "$apiBasePath/search-settings")
            for (url in urls)
                mockMvc.perform(post(url))
                        .andExpect(status().`is`(401))
        }
    }

    @Nested
    inner class PathAccessibility {


        @Test
        fun `Logout is accessible`() {
            mockMvc.perform(get("$apiBasePath/logout"))
                    .andExpect(status().isOk)
        }

        @Test
        fun `Root url is accessible`() {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk)
        }

        @Test
        fun `Icon url is accessible`() {
            mockMvc.perform(get("/favicon.ico"))
                    .andExpect(status().isOk)
        }

        @Test
        fun `static css folder is accessible`() {
            mockMvc.perform(get("/static/css"))
                    .andExpect(status().isOk)
        }

        @Test
        fun `static js folder is accessible`() {
            mockMvc.perform(get("/static/js"))
                    .andExpect(status().isOk)
        }

        @Nested
        inner class User {

            @Test
            fun `Signup is accessible`() {
                val user = UserWithPassword(login = "dkozak", password = "pass")
                mockMvc.perform(post("$apiBasePath/user")
                        .content(ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk)
            }

            @Test
            fun `UserSettings endpoint is not accessible if not logged in`() {
                mockMvc.perform(get("$apiBasePath/user-settings"))
                        .andExpect(status().`is`(401))
            }
        }
    }


}