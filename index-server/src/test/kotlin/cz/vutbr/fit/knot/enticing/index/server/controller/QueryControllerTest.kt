package cz.vutbr.fit.knot.enticing.index.server.controller

import cz.vutbr.fit.knot.enticing.dto.CorpusFormat
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.index.server.service.QueryService
import cz.vutbr.fit.knot.enticing.index.server.testconfig.LogServiceTestConfig
import cz.vutbr.fit.knot.enticing.index.server.utils.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Import(LogServiceTestConfig::class)
@WebMvcTest(secure = false)
internal class QueryControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Value("\${api.base.path}") val apiBasePath: String
) {

    @MockBean
    lateinit var queryService: QueryService


    @Test
    fun `get format`() {
        val format = CorpusFormat("corp1", mapOf("lemma" to "lemma"), emptyMap())

        Mockito.`when`(queryService.loadCorpusFormat())
                .thenReturn(format)

        mockMvc.perform(get("$apiBasePath/format"))
                .andExpect(status().isOk)
                .andExpect(content().json(format.toJson()))

        Mockito.verify(queryService).loadCorpusFormat()
    }

    @Test
    fun `valid document query`() {
        Mockito.`when`(queryService.getDocument(templateDocumentQuery))
                .thenReturn(documentDummyResult)

        mockMvc.perform(post("$apiBasePath/document")
                .contentJson(templateDocumentQuery))
                .andExpect(status().isOk)
                .andExpect(content().json(documentDummyResult.toJson()))

        Mockito.verify(queryService).getDocument(templateDocumentQuery)
    }

    @Test
    fun `valid search query`() {
        Mockito.`when`(queryService.processQuery(templateSearchQuery))
                .thenReturn(searchDummyResult)

        mockMvc.perform(post("$apiBasePath/query")
                .contentJson(templateSearchQuery))
                .andExpect(status().isOk)
                .andExpect(content().json(searchDummyResult.toJson()))

        Mockito.verify(queryService).processQuery(templateSearchQuery)

    }

    @Test
    fun `search query missing query`() {
        mockMvc.perform(post("$apiBasePath/query")
                .contentJson(templateSearchQuery.copy(query = "  ")))
                .andExpect(status().`is`(400))

        Mockito.verifyZeroInteractions(queryService)
    }

    @Test
    fun `valid context extension query`() {
        Mockito.`when`(queryService.extendContext(templateContextExtensionQuery))
                .thenReturn(contextExtensionDummyResult)

        mockMvc.perform(post("$apiBasePath/context")
                .contentJson(templateContextExtensionQuery))
                .andExpect(status().isOk)
                .andExpect(content().json(contextExtensionDummyResult.toJson()))

        Mockito.verify(queryService).extendContext(templateContextExtensionQuery)
    }

    @Test
    fun `context extension collection is empty`() {
        mockMvc.perform(post("$apiBasePath/context")
                .contentJson(templateContextExtensionQuery.copy(collection = "")))
                .andExpect(status().`is`(400))

        Mockito.verifyZeroInteractions(queryService)
    }

    @Test
    fun `context extension query missing size`() {
        Mockito.`when`(queryService.extendContext(templateContextExtensionQuery))
                .thenReturn(contextExtensionDummyResult)

        val serialized = templateContextExtensionQuery.toJson().replace("size", "len")
                .also { println(it) }
        mockMvc.perform(post("$apiBasePath/context")
                .content(serialized)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(400))

        Mockito.verifyZeroInteractions(queryService)
    }

    @AfterEach
    fun clearMocks() {
        Mockito.clearInvocations(queryService)
    }
}