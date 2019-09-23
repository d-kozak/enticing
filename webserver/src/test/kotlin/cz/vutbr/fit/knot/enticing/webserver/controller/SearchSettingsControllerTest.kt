package cz.vutbr.fit.knot.enticing.webserver.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.whenever
import cz.vutbr.fit.knot.enticing.webserver.dto.ImportedSearchSettings
import cz.vutbr.fit.knot.enticing.webserver.dto.toEntity
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
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
    lateinit var compilerService: EqlCompilerService

    @MockBean
    lateinit var searchSettingsService: SearchSettingsService

    @MockBean
    lateinit var userService: EnticingUserService

    @MockBean
    lateinit var queryService: QueryService

    @Test
    fun create() {
        val searchSettings = SearchSettings(1, "foo", annotationServer = "foo.baz", annotationDataServer = "baz.paz", servers = setOf("127.0.0.1"))
        Mockito.`when`(searchSettingsService.create(searchSettings)).thenReturn(searchSettings)
        mockMvc.perform(post("$apiBasePath/search-settings").content(ObjectMapper().writeValueAsString(searchSettings))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
        Mockito.verify(searchSettingsService).create(searchSettings)
        Mockito.clearInvocations(searchSettingsService)
    }



    @Test
    fun `read all`() {
        val searchSettings = listOf(SearchSettings(1, "foo"), SearchSettings(2, "bar"), SearchSettings(3, "baz"))
        whenever(searchSettingsService.getAll(anyOrNull())).thenReturn(searchSettings)
        mockMvc.perform(get("$apiBasePath/search-settings"))
                .andExpect(status().isOk)
                .andExpect(content().json(ObjectMapper().writeValueAsString(searchSettings)))
        Mockito.verify(searchSettingsService).getAll(anyOrNull())
        Mockito.clearInvocations(searchSettingsService)
    }

    @Test
    fun update() {
        val searchSettings = SearchSettings(1, "foo", annotationServer = "foo.baz", annotationDataServer = "baz.paz", servers = setOf("127.0.0.1"))
        Mockito.`when`(searchSettingsService.update(searchSettings)).thenReturn(searchSettings)
        mockMvc.perform(put("$apiBasePath/search-settings")
                .content(ObjectMapper().writeValueAsString(searchSettings))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
        Mockito.verify(searchSettingsService).update(searchSettings)
        Mockito.clearInvocations(searchSettingsService)
    }

    @Test
    fun delete() {
        mockMvc.perform(delete("$apiBasePath/search-settings/1"))
                .andExpect(status().isOk)
        Mockito.verify(searchSettingsService).delete(1)
        Mockito.clearInvocations(searchSettingsService)
    }

    @Test
    fun `Create and update should fail for invalid settings`() {
        val name = SearchSettings(name = "", annotationDataServer = "foo.com", annotationServer = "baz.com", servers = setOf("127.0.0.1:20"))
        val annotationDataServer = SearchSettings(name = "foo", annotationDataServer = "", annotationServer = "foo.baz", servers = setOf("127.0.0.1:20"))
        val annotationServer = SearchSettings(name = "foo", annotationDataServer = "foo.com", annotationServer = "", servers = setOf("127.0.0.1:20"))
        val servers = SearchSettings(name = "foo", annotationDataServer = "foo.com", annotationServer = "baz.com", servers = setOf())
        val invalidAnnotationDataServer = SearchSettings(name = "foo", annotationDataServer = "asdfdsa", annotationServer = "baz.com", servers = setOf("127.0.0.1:20"))
        val invalidAnnotationServer = SearchSettings(name = "foo", annotationDataServer = "foo.com", annotationServer = "afsdsa", servers = setOf("127.0.0.1:20"))
        val invalidServers = SearchSettings(name = "foo", annotationDataServer = "foo.com", annotationServer = "baz.com", servers = setOf("10.10."))
        val invalid = listOf(name, annotationDataServer, annotationServer, servers, invalidAnnotationDataServer, invalidAnnotationServer, invalidServers)

        whenever(searchSettingsService.create(any())).thenThrow(UnsupportedOperationException())

        for (settings in invalid) {
            println("Testing with $settings")
            println("POST")
            mockMvc.perform(post("$apiBasePath/search-settings")
                    .content(ObjectMapper().writeValueAsString(settings))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().`is`(400))
            Mockito.verifyZeroInteractions(searchSettingsService)
            println("PUT")
            mockMvc.perform(put("$apiBasePath/search-settings")
                    .content(ObjectMapper().writeValueAsString(settings))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().`is`(400))
            Mockito.verifyZeroInteractions(searchSettingsService)
            println("=====")
        }
        Mockito.clearInvocations(searchSettingsService)

    }

    @Test
    fun select() {
        mockMvc.perform(get("$apiBasePath/search-settings/select/33"))
                .andExpect(status().isOk)
        Mockito.verify(userService).selectSettings(33)
    }

    @Test
    fun `Import settings test`() {
        val importedSettings = ImportedSearchSettings("import", "foo.com", "baz.com", setOf("127.0.0.1:20"))
        Mockito.`when`(searchSettingsService.import(importedSettings)).thenReturn(importedSettings.toEntity())

        mockMvc.perform(post("$apiBasePath/search-settings/import")
                .content(ObjectMapper().writeValueAsString(importedSettings))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
        Mockito.verify(searchSettingsService).import(importedSettings)
        Mockito.clearInvocations(searchSettingsService)
    }

    @Test
    fun `Import settings test empty parameters should fail`() {
        val name = ImportedSearchSettings("", "foo.com", "baz.com", setOf("127.0.0.1:20"))
        val annotationDataServer = ImportedSearchSettings("foo", "", "foo.baz", setOf("127.0.0.1:20"))
        val annotationServer = ImportedSearchSettings("foo", "foo.com", "", setOf("127.0.0.1:20"))
        val roles = ImportedSearchSettings("foo", "foo.com", "baz.com", setOf())

        val invalidSettings = listOf(name, annotationDataServer, annotationServer, roles)
        for (settings in invalidSettings) {
            mockMvc.perform(post("$apiBasePath/search-settings/import")
                    .content(ObjectMapper().writeValueAsString(settings))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().`is`(400))
            Mockito.verifyZeroInteractions(searchSettingsService)
        }
        Mockito.clearInvocations(searchSettingsService)
    }
}