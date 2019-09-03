package cz.vutbr.fit.knot.enticing.webserver.controller


import com.nhaarman.mockitokotlin2.*
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

    @Test
    fun query() {
        val query = SearchQuery("ahoj cau")
        val selectedSettings = 1

        val mockSession = MockHttpSession()

        val dummyResult = WebServer.ResultList(listOf(firstResult))
        whenever(queryService.query(eq(query), eq(1), any())).thenReturn(dummyResult)

        mockMvc.perform(MockMvcRequestBuilders.post("$apiBasePath/query?settings=$selectedSettings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query.toJson()))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(content().string(dummyResult.toJson()))

        verify(queryService).query(eq(query), eq(1), any())
        clearInvocations(queryService)
    }

    @Test
    fun context() {
        val query = WebServer.ContextExtensionQuery("foo.baz", "col1", 2, 201, 42, 10)

        whenever(queryService.context(query)).thenReturn(snippetExtension)

        mockMvc.perform(MockMvcRequestBuilders.post("$apiBasePath/query/context")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query.toJson()))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(content().string(snippetExtension.toJson()))


        verify(queryService).context(query)
        clearInvocations(queryService)
    }

    @Test
    fun document() {
        val query = WebServer.DocumentQuery("google.com", "col1", 1, TextMetadata.Predefined("none"), query = "foo")

        whenever(queryService.document(query)).thenReturn(dummyDocument)

        mockMvc.perform(MockMvcRequestBuilders.post("$apiBasePath/query/document")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query.toJson()))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(content().string(dummyDocument.toJson()))


        verify(queryService).document(query)
        clearInvocations(queryService)
    }

    @Test
    fun format() {
        val url = "$apiBasePath/query/format/42"
        val corpusFormat = CorpusFormat("dummy", emptyMap(), emptyMap())
        whenever(queryService.format(42))
                .thenReturn(corpusFormat)

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(content().json(corpusFormat.toJson()))

        verify(queryService).format(42)
        clearInvocations(searchSettingsRepository)

    }
}