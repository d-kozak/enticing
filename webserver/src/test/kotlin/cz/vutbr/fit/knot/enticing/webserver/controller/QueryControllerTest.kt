package cz.vutbr.fit.knot.enticing.webserver.controller


import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import cz.vutbr.fit.knot.enticing.webserver.service.Mg4jCompilerService
import cz.vutbr.fit.knot.enticing.webserver.service.QueryService
import cz.vutbr.fit.knot.enticing.webserver.service.mock.dummyDocument
import cz.vutbr.fit.knot.enticing.webserver.service.mock.firstResult
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import java.net.URLEncoder

@WebMvcTest
@ExtendWith(SpringExtension::class)
internal class QueryControllerTest(
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
    fun query() {
        val query = URLEncoder.encode("ahoj cau", "UTF-8")
        val selectedSettings = 1

        val dummyResult = listOf(firstResult)
        Mockito.`when`(queryService.query("ahoj cau", 1)).thenReturn(dummyResult)

        mockMvc.perform(MockMvcRequestBuilders.get("$apiBasePath/query?query=$query&settings=$selectedSettings"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(content().string(dummyResult.toJson()))
        Mockito.verify(queryService).query("ahoj cau", 1)
        Mockito.clearInvocations(queryService)
    }

    @Test
    fun context() {
        val docId = 1
        val size = 42
        val location = 201

        Mockito.`when`(queryService.context(1, 201, 42)).thenReturn(firstResult)

        mockMvc.perform(MockMvcRequestBuilders.get("$apiBasePath/query/context?docId=$docId&location=$location&size=$size"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(content().string(firstResult.toJson()))


        Mockito.verify(queryService).context(1, 201, 42)
        Mockito.clearInvocations(queryService)
    }

    @Test
    fun document() {
        val docId = 1

        Mockito.`when`(queryService.document(1)).thenReturn(dummyDocument)

        mockMvc.perform(MockMvcRequestBuilders.get("$apiBasePath/query/document?docId=$docId"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(content().string(dummyDocument.toJson()))


        Mockito.verify(queryService).document(1)
        Mockito.clearInvocations(queryService)
    }
}