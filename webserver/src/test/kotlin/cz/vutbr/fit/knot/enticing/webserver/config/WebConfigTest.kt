package cz.vutbr.fit.knot.enticing.webserver.config

import cz.vutbr.fit.knot.enticing.mx.ServerMonitoringService
import cz.vutbr.fit.knot.enticing.webserver.service.*
import cz.vutbr.fit.knot.enticing.webserver.testconfig.LogServiceTestConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@WebMvcTest
@ExtendWith(SpringExtension::class)
@Import(LogServiceTestConfig::class)
internal class WebConfigTest(@Autowired val mockMvc: MockMvc) {

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

    @MockBean
    lateinit var monitoringService: ServerMonitoringService

    @Test
    fun `Paths that should be handled by frontend are delegated to index`() {
        val paths = listOf("/", "/search", "/settings", "/users", "/search-settings")
        for (path in paths) {
            mockMvc.perform(MockMvcRequestBuilders.get(path))
                    .andExpect(MockMvcResultMatchers.status().isOk)
        }
    }

}