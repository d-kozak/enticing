package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.mg4j.compiler.ast.ErrorNode
import cz.vutbr.fit.knot.enticing.mg4j.compiler.dto.ParsedQuery
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import cz.vutbr.fit.knot.enticing.webserver.service.Mg4jCompilerService
import cz.vutbr.fit.knot.enticing.webserver.service.QueryService

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
internal class Mg4jCompilerControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Value("\${api.base.path}") val apiBasePath: String
) {

    @MockBean
    lateinit var compilerService: Mg4jCompilerService

    @MockBean
    lateinit var userService: EnticingUserService

    @MockBean
    lateinit var userRepository: UserRepository

    @MockBean
    lateinit var searchSettingsRepository: SearchSettingsRepository

    @MockBean
    lateinit var queryService: QueryService


    @Test
    fun `get calls compiler service`() {

        val dummyDto = ParsedQuery(ErrorNode("foo"))
        Mockito.`when`(compilerService.parseQuery("nertag:person (killed|visited)"))
                .thenReturn(dummyDto)

        val query = URLEncoder.encode("nertag:person (killed|visited)", "UTF-8")
        mockMvc.perform(MockMvcRequestBuilders.get("$apiBasePath/compiler?query=$query"))
                .andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(compilerService).parseQuery("nertag:person (killed|visited)")
        Mockito.clearInvocations(compilerService)
    }
}