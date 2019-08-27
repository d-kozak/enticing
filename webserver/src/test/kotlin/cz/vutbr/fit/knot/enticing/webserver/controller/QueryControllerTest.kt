package cz.vutbr.fit.knot.enticing.webserver.controller


import cz.vutbr.fit.knot.enticing.dto.CorpusFormat
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.TextMetadata
import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import cz.vutbr.fit.knot.enticing.webserver.service.EqlCompilerService
import cz.vutbr.fit.knot.enticing.webserver.service.QueryService
import cz.vutbr.fit.knot.enticing.webserver.service.mock.dummyDocument
import cz.vutbr.fit.knot.enticing.webserver.service.mock.firstResult
import cz.vutbr.fit.knot.enticing.webserver.service.mock.snippetExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@WebMvcTest
@ExtendWith(SpringExtension::class)
internal class QueryControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Value("\${api.base.path}") val apiBasePath: String
) {

    @MockBean
    lateinit var compilerService: EqlCompilerService

    @MockBean
    lateinit var userService: EnticingUserService

    @MockBean
    lateinit var userRepository: UserRepository

    @MockBean
    lateinit var searchSettingsRepository: SearchSettingsRepository


    @MockBean
    lateinit var queryService: QueryService

    private val mockSession = MockHttpSession()

    @Test
    fun query() {
        val query = SearchQuery("ahoj cau")
        val selectedSettings = 1

        val dummyResult = WebServer.ResultList(listOf(firstResult))
        Mockito.`when`(queryService.query(query, 1, mockSession)).thenReturn(dummyResult)

        mockMvc.perform(MockMvcRequestBuilders.post("$apiBasePath/query?settings=$selectedSettings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query.toJson()))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(content().string(dummyResult.toJson()))
        Mockito.verify(queryService).query(query, 1, mockSession)
        Mockito.clearInvocations(queryService)
    }

    @Test
    fun context() {
        val query = WebServer.ContextExtensionQuery("foo.baz", "col1", 2, 201, 42, 10)

        Mockito.`when`(queryService.context(query)).thenReturn(snippetExtension)

        mockMvc.perform(MockMvcRequestBuilders.post("$apiBasePath/query/context")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query.toJson()))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(content().string(snippetExtension.toJson()))


        Mockito.verify(queryService).context(query)
        Mockito.clearInvocations(queryService)
    }

    @Test
    fun document() {
        val query = WebServer.DocumentQuery("google.com", "col1", 1, TextMetadata.Predefined("none"), query = "foo")

        Mockito.`when`(queryService.document(query)).thenReturn(dummyDocument)

        mockMvc.perform(MockMvcRequestBuilders.post("$apiBasePath/query/document")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query.toJson()))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(content().string(dummyDocument.toJson()))


        Mockito.verify(queryService).document(query)
        Mockito.clearInvocations(queryService)
    }

    @Test
    fun format() {
        val url = "$apiBasePath/query/format/42"
        val corpusFormat = CorpusFormat("dummy", emptyMap(), emptyMap())
        Mockito.`when`(queryService.format(42))
                .thenReturn(corpusFormat)

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(content().json(corpusFormat.toJson()))

        Mockito.verify(queryService).format(42)
        Mockito.clearInvocations(searchSettingsRepository)

    }
}