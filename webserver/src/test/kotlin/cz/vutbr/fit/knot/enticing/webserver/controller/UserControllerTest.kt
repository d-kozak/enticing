package cz.vutbr.fit.knot.enticing.webserver.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(secure = false)
@ExtendWith(SpringExtension::class)
internal class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Value("\${api.base.path}")
    lateinit var apiBasePath: String

    @Test
    fun `Endpoint exists`() {
        mockMvc.perform(get("$apiBasePath/user"))
                .andExpect(status().isOk)
    }
}