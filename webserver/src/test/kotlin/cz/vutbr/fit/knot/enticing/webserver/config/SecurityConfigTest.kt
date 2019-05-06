package cz.vutbr.fit.knot.enticing.webserver.config

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@ExtendWith(SpringExtension::class)
class SecurityConfigTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Value("\${api.base.path}")
    lateinit var apiBasePath: String

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
        fun `User endpoint is not accessible if not logged in`() {
            mockMvc.perform(get("$apiBasePath/user"))
                    .andExpect(status().`is`(403))
        }
    }
}