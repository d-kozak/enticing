package cz.vutbr.fit.knot.enticing.webserver.controller

import com.fasterxml.jackson.databind.ObjectMapper
import cz.vutbr.fit.knot.enticing.webserver.dto.UserWithPassword
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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
        val user = UserWithPassword(
                id = 0,
                login = "Pepa",
                password = "123",
                roles = mutableSetOf("ADMIN"),
                active = false,
                selectedSettings = 42
        )
        val serialized = ObjectMapper().writeValueAsString(user)
        mockMvc.perform(post("$apiBasePath/user")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        Mockito.verify(userService)
                .saveUser(user)
    }
}