package cz.vutbr.fit.knot.enticing.webserver.integration

import com.fasterxml.jackson.databind.ObjectMapper
import cz.vutbr.fit.knot.enticing.webserver.dto.UserCredentials
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTests(
        @Value("\${api.base.path}") val apiBasePath: String,
        @Autowired val userRepository: UserRepository,
        @Autowired val encoder: PasswordEncoder,
        @Autowired context: WebApplicationContext,
        @Autowired val userService: EnticingUserService

) {

    val mockMvc = MockMvcBuilders.webAppContextSetup(context).build()

    @Test
    fun `sign up test`() {
        val user = UserCredentials("Pepa", "123")
        val serialized = ObjectMapper().writeValueAsString(user)

        mockMvc.perform(post("$apiBasePath/user")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        val userEntity = userRepository.findByLogin("Pepa") ?: throw AssertionError("user not found in the database")
        assertThat(userEntity.active).isTrue()
        assertThat(encoder.matches("123", userEntity.encryptedPassword))
        assertThat(userService.getCurrentUser()).isNull()

    }

}