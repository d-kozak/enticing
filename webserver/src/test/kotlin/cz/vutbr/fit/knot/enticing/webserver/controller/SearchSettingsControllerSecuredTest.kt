package cz.vutbr.fit.knot.enticing.webserver.controller

import com.fasterxml.jackson.databind.ObjectMapper
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@ExtendWith(SpringExtension::class)
class SearchSettingsControllerSecuredTest(
        @Autowired val mockMvc: MockMvc,
        @Value("\${api.base.path}") val apiBasePath: String
) {

    @MockBean
    lateinit var searchSettingsRepository: SearchSettingsRepository

    @MockBean
    lateinit var userService: EnticingUserService

    @MockBean
    lateinit var userRepository: UserRepository

    @Test
    fun `When not logged in only non private setting should be visible`() {
        val searchSettings = listOf(SearchSettings(1, "foo"), SearchSettings(2, "bar"), SearchSettings(3, "baz"))
        val serialized = ObjectMapper().writeValueAsString(searchSettings)

        Mockito.`when`(searchSettingsRepository.findByPrivateIsFalse())
                .thenReturn(searchSettings)

        mockMvc.perform(get("$apiBasePath/search-settings"))
                .andExpect(status().isOk)
                .andExpect(content().json(serialized))

        Mockito.verify(searchSettingsRepository)
                .findByPrivateIsFalse()
        Mockito.clearInvocations(searchSettingsRepository)

    }

    @Test
    @WithMockUser
    fun `For normal user non private setting should be visible`() {
        val searchSettings = listOf(SearchSettings(1, "foo"), SearchSettings(2, "bar"), SearchSettings(3, "baz"))
        val serialized = ObjectMapper().writeValueAsString(searchSettings)

        Mockito.`when`(searchSettingsRepository.findByPrivateIsFalse())
                .thenReturn(searchSettings)

        mockMvc.perform(get("$apiBasePath/search-settings"))
                .andExpect(status().isOk)
                .andExpect(content().json(serialized))

        Mockito.verify(searchSettingsRepository)
                .findByPrivateIsFalse()
        Mockito.clearInvocations(searchSettingsRepository)

    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `For admin all settings should be visible`() {
        val searchSettings = listOf(SearchSettings(1, "foo"), SearchSettings(2, "bar"), SearchSettings(3, "baz"))
        val serialized = ObjectMapper().writeValueAsString(searchSettings)

        Mockito.`when`(searchSettingsRepository.findAll())
                .thenReturn(searchSettings)

        mockMvc.perform(get("$apiBasePath/search-settings"))
                .andExpect(status().isOk)
                .andExpect(content().json(serialized))


        Mockito.verify(searchSettingsRepository)
                .findAll()
        Mockito.clearInvocations(searchSettingsRepository)

    }

}