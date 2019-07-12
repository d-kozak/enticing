package cz.vutbr.fit.knot.enticing.index.server.config

import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.index.server.service.QueryService
import cz.vutbr.fit.knot.enticing.index.server.utils.*
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

@WebMvcTest
class SecurityConfigTest(
        @Autowired val mockMvc: MockMvc,
        @Value("\${api.base.path}") val apiBasePath: String
) {

    @MockBean
    lateinit var queryService: QueryService

    @Test
    fun `query endpoint should be always available`() {
        Mockito.`when`(queryService.processQuery(templateSearchQuery))
                .thenReturn(searchDummyResult)

        mockMvc.perform(post("$apiBasePath/query")
                .content(templateSearchQuery.toJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `context extension endpoint should be always available`() {
        mockMvc.perform(post("$apiBasePath/context")
                .content(templateContextExtensionQuery.toJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)

        Mockito.verify(queryService).extendContext(templateContextExtensionQuery)
    }

    @Test
    fun `document endpoint is always accessible`() {
        Mockito.`when`(queryService.getDocument(templateDocumentQuery))
                .thenReturn(documentDummyResult)

        mockMvc.perform(post("$apiBasePath/document")
                .contentJson(templateDocumentQuery))
                .andExpect(status().isOk)

        Mockito.verify(queryService).getDocument(templateDocumentQuery)
    }

    @AfterEach
    fun clearMocks() {
        Mockito.clearInvocations(queryService)
    }
}