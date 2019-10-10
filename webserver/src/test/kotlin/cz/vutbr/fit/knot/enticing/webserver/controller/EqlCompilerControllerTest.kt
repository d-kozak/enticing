package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.dto.PureMgj4Node
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import cz.vutbr.fit.knot.enticing.webserver.service.EqlCompilerService
import cz.vutbr.fit.knot.enticing.webserver.service.QueryService
import cz.vutbr.fit.knot.enticing.webserver.service.SearchSettingsService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.net.URLEncoder


@WebMvcTest
@ExtendWith(SpringExtension::class)
internal class EqlCompilerControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Value("\${api.base.path}") val apiBasePath: String
) {

    @MockBean
    lateinit var compilerService: EqlCompilerService

    @MockBean
    lateinit var userService: EnticingUserService

    @MockBean
    lateinit var searchSettingsService: SearchSettingsService

    @MockBean
    lateinit var queryService: QueryService

    @Test
    fun `get calls compiler service`() {

        val dummyDto = ParsedQuery(PureMgj4Node(" "))
        Mockito.`when`(compilerService.parseQuery("nertag:person (killed|visited)", 5))
                .thenReturn(dummyDto)

        val query = URLEncoder.encode("nertag:person (killed|visited)", "UTF-8")
        mockMvc.perform(MockMvcRequestBuilders.get("$apiBasePath/compiler?query=$query&settings=5"))
                .andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(compilerService).parseQuery("nertag:person (killed|visited)", 5)
        Mockito.clearInvocations(compilerService)
    }
}