package cz.vutbr.fit.knot.enticing.webserver

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@WebMvcTest
@ExtendWith(SpringExtension::class)
class ApiBasePathTest {

    @Value("\${api.base.path}")
    lateinit var basePath: String

    @Test
    fun `Property api base path is set`() {
        Assertions.assertThat(basePath)
                .isNotNull()
                .isNotBlank()
    }
}