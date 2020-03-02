package cz.vutbr.fit.knot.enticing.webserver

import cz.vutbr.fit.knot.enticing.webserver.service.*
import cz.vutbr.fit.knot.enticing.webserver.testconfig.LogServiceTestConfig
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@WebMvcTest
@ExtendWith(SpringExtension::class)
@Import(LogServiceTestConfig::class)
internal class ApiBasePathTest(
        @Value("\${api.base.path}") val basePath: String) {

    @MockBean
    lateinit var compilerService: EqlCompilerService

    @MockBean
    lateinit var userService: EnticingUserService

    @MockBean
    lateinit var searchSettingsService: SearchSettingsService

    @MockBean
    lateinit var queryService: QueryService

    @MockBean
    lateinit var userHolder: CurrentUserHolder

    @Test
    fun `Property api base path is set`() {
        Assertions.assertThat(basePath)
                .isNotNull()
                .isNotBlank()
    }
}