package cz.vutbr.fit.knot.enticing.index.server.controller

import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.index.server.service.QueryService
import cz.vutbr.fit.knot.enticing.index.server.utils.contextExtensionDummyResult
import cz.vutbr.fit.knot.enticing.index.server.utils.searchDummyResult
import cz.vutbr.fit.knot.enticing.index.server.utils.templateContextExtensionQuery
import cz.vutbr.fit.knot.enticing.index.server.utils.templateSearchQuery
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(secure = false)
internal class QueryControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Value("\${api.base.path}") val apiBasePath: String
) {

    @MockBean
    lateinit var queryService: QueryService


    @Test
    fun `valid search query`() {
        Mockito.`when`(queryService.processQuery(templateSearchQuery))
                .thenReturn(searchDummyResult)

        mockMvc.perform(post("$apiBasePath/query")
                .content(templateSearchQuery.toJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        Mockito.verify(queryService).processQuery(templateSearchQuery)

    }

    @Test
    fun `search query missing query`() {
        mockMvc.perform(post("$apiBasePath/query")
                .content(templateSearchQuery.copy(query = "  ").toJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().`is`(400))

        Mockito.verifyZeroInteractions(queryService)
    }

    @Test
    fun `valid context extension query`() {
        mockMvc.perform(post("$apiBasePath/context")
                .content(templateContextExtensionQuery.toJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        Mockito.verify(queryService).extendContext(templateContextExtensionQuery)
    }

    @Test
    fun `context extension collection is empty`() {
        mockMvc.perform(post("$apiBasePath/context")
                .content(templateContextExtensionQuery.copy(collection = "").toJson())
                .contentType(MediaType.APPLICATION_JSON))
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