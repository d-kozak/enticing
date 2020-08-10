package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.api.ManagementServiceApi
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.mx.ServerMonitoringService
import cz.vutbr.fit.knot.enticing.webserver.dto.QueryValidationReply
import cz.vutbr.fit.knot.enticing.webserver.dto.QueryValidationRequest
import cz.vutbr.fit.knot.enticing.webserver.service.*
import cz.vutbr.fit.knot.enticing.webserver.testconfig.LogServiceTestConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@WebMvcTest
@ExtendWith(SpringExtension::class)
@Import(LogServiceTestConfig::class)
internal class EqlCompilerControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Value("\${api.base.path}") val apiBasePath: String
) {

    @MockBean
    lateinit var compilerService: EqlCompilerService

    @MockBean
    lateinit var userService: EnticingUserService

    @MockBean
    lateinit var userHolder: CurrentUserHolder

    @MockBean
    lateinit var searchSettingsService: SearchSettingsService

    @MockBean
    lateinit var queryService: QueryService

    @MockBean
    lateinit var monitoringService: ServerMonitoringService

    @MockBean
    lateinit var managementServiceApi: ManagementServiceApi

    @Test
    fun `get calls compiler service`() {
        Mockito.`when`(queryService.validateQuery("nertag:person (killed|visited)", 5))
                .thenReturn(QueryValidationReply("foo", emptyList()))

        val query = QueryValidationRequest("nertag:person (killed|visited)", 5)
        mockMvc.perform(MockMvcRequestBuilders.post("$apiBasePath/compiler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query.toJson()))
                .andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(queryService).validateQuery("nertag:person (killed|visited)", 5)
        Mockito.clearInvocations(queryService)
    }
}