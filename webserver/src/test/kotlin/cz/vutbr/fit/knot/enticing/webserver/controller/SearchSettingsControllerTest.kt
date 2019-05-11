package cz.vutbr.fit.knot.enticing.webserver.controller

import com.fasterxml.jackson.databind.ObjectMapper
import cz.vutbr.fit.knot.enticing.webserver.dto.ImportedSearchSettings
import cz.vutbr.fit.knot.enticing.webserver.dto.toEntity
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
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
import java.util.*


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
        val searchSettings = SearchSettings(1, "foo", annotationServer = "foo.baz", annotationDataServer = "baz.paz", servers = setOf("127.0.0.1"))
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
        val searchSettings = SearchSettings(1, "foo", annotationServer = "foo.baz", annotationDataServer = "baz.paz", servers = setOf("127.0.0.1"))
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
        mockMvc.perform(delete("$apiBasePath/search-settings/1"))
                .andExpect(status().isOk)
        Mockito.verify(searchSettingsRepository).deleteById(1)
        Mockito.clearInvocations(searchSettingsRepository)
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

        Mockito.`when`(searchSettingsRepository.save<SearchSettings>(any())).thenThrow(UnsupportedOperationException())

        for (settings in invalid) {
            println("Testing with $settings")
            println("POST")
            mockMvc.perform(post("$apiBasePath/search-settings")
                    .content(ObjectMapper().writeValueAsString(settings))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().`is`(400))
            Mockito.verifyZeroInteractions(searchSettingsRepository)
            println("PUT")
            mockMvc.perform(put("$apiBasePath/search-settings")
                    .content(ObjectMapper().writeValueAsString(settings))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().`is`(400))
            Mockito.verifyZeroInteractions(searchSettingsRepository)
            println("=====")
        }
        Mockito.clearInvocations(searchSettingsRepository)

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
        Mockito.`when`(searchSettingsRepository.save(importedSettings.toEntity())).thenReturn(importedSettings.toEntity())

        mockMvc.perform(post("$apiBasePath/search-settings/import")
                .content(ObjectMapper().writeValueAsString(importedSettings))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
        Mockito.verify(searchSettingsRepository).save(importedSettings.toEntity())
        Mockito.clearInvocations(searchSettingsRepository)
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
            Mockito.verifyZeroInteractions(searchSettingsRepository)
        }
        Mockito.clearInvocations(searchSettingsRepository)
    }

    @Test
    fun `Change default no previous default`() {
        val searchSettings = SearchSettings(id = 1, name = "foo")
        val capture = ArgumentCaptor.forClass(SearchSettings::class.java)
        Mockito.`when`(searchSettingsRepository.findByDefaultIsTrue())
                .thenReturn(null)
        Mockito.`when`(searchSettingsRepository.findById(1))
                .thenReturn(Optional.of(searchSettings))
        Mockito.`when`(searchSettingsRepository.save(capture.capture()))
                .thenReturn(searchSettings)

        mockMvc.perform(put("$apiBasePath/search-settings/default/1"))
                .andExpect(status().`is`(200))

        Mockito.verify(searchSettingsRepository).findByDefaultIsTrue()
        Mockito.verify(searchSettingsRepository).findById(1)
        Mockito.verify(searchSettingsRepository).save(searchSettings)

        assertThat(searchSettings.default)
                .isTrue()
        assertThat(capture)
                .extracting { it.value.default }
                .isEqualTo(true)
        assertThat(capture)
                .extracting { it.value.name }
                .isEqualTo("foo")

    }

    @Test
    fun `Change default with previous`() {
        val previousDefault = SearchSettings(id = 3, name = "prevDefault", default = true)
        val searchSettings = SearchSettings(id = 1, name = "foo")
        val capture = ArgumentCaptor.forClass(SearchSettings::class.java)
        Mockito.`when`(searchSettingsRepository.findByDefaultIsTrue())
                .thenReturn(previousDefault)
        Mockito.`when`(searchSettingsRepository.findById(1))
                .thenReturn(Optional.of(searchSettings))
        Mockito.`when`(searchSettingsRepository.save(capture.capture()))
                .thenReturn(searchSettings)

        mockMvc.perform(put("$apiBasePath/search-settings/default/1"))
                .andExpect(status().`is`(200))

        Mockito.verify(searchSettingsRepository).findByDefaultIsTrue()
        Mockito.verify(searchSettingsRepository).findById(1)
        Mockito.verify(searchSettingsRepository).save(previousDefault)
        Mockito.verify(searchSettingsRepository).save(searchSettings)

        assertThat(searchSettings.default)
                .isTrue()
        assertThat(capture.allValues)
                .hasSize(2)
        val old = capture.allValues[0]
        assertThat(old.default)
                .isFalse()
        assertThat(old.name)
                .isEqualTo("prevDefault")

        val new = capture.allValues[1]
        assertThat(new.default)
                .isTrue()
        assertThat(new.name)
                .isEqualTo("foo")

    }

}