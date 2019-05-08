package cz.vutbr.fit.knot.enticing.webserver.controller

import com.fasterxml.jackson.databind.ObjectMapper
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(secure = false)
@ExtendWith(SpringExtension::class)
internal class SearchSettingsControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Value("\${api.base.path}") val apiBasePath: String
) {

    @MockBean
    lateinit var searchSettingsRepository: SearchSettingsRepository

    @MockBean
    lateinit var userService: EnticingUserService

    @Test
    fun create() {
        val searchSettings = SearchSettings(1, "foo")
        Mockito.`when`(searchSettingsRepository.save(searchSettings)).thenReturn(searchSettings)
        mockMvc.perform(post("$apiBasePath/search-settings").content(ObjectMapper().writeValueAsString(searchSettings))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
        Mockito.verify(searchSettingsRepository).save(searchSettings)
        Mockito.clearInvocations(searchSettingsRepository)
    }

    @Test
    fun `read all`() {
        val searchSettings = listOf(SearchSettings(1, "foo"), SearchSettings(2, "bar"), SearchSettings(3, "baz"))
        Mockito.`when`(searchSettingsRepository.findAll()).thenReturn(searchSettings)
        mockMvc.perform(get("$apiBasePath/search-settings"))
                .andExpect(status().isOk)
                .andExpect(content().json(ObjectMapper().writeValueAsString(searchSettings)))
        Mockito.verify(searchSettingsRepository).findAll()
        Mockito.clearInvocations(searchSettingsRepository)
    }

    @Test
    fun update() {
        val searchSettings = SearchSettings(1, "foo")
        Mockito.`when`(searchSettingsRepository.save(searchSettings)).thenReturn(searchSettings)
        mockMvc.perform(put("$apiBasePath/search-settings")
                .content(ObjectMapper().writeValueAsString(searchSettings))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
        Mockito.verify(searchSettingsRepository).save(searchSettings)
        Mockito.clearInvocations(searchSettingsRepository)
    }

    @Test
    fun delete() {
        val searchSettings = SearchSettings(1, "foo")
        mockMvc.perform(delete("$apiBasePath/search-settings")
                .content(ObjectMapper().writeValueAsString(searchSettings))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
        Mockito.verify(searchSettingsRepository).delete(searchSettings)
        Mockito.clearInvocations(searchSettingsRepository)
    }

    @Test
    fun select() {
        mockMvc.perform(get("$apiBasePath/search-settings/select/33"))
                .andExpect(status().isOk)
        Mockito.verify(userService).selectSettings(33)
    }
}