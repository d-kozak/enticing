package cz.vutbr.fit.knot.enticing.webserver.config

import com.fasterxml.jackson.databind.ObjectMapper
import cz.vutbr.fit.knot.enticing.dto.TextMetadata
import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.DummyRoot
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import cz.vutbr.fit.knot.enticing.webserver.dto.*
import cz.vutbr.fit.knot.enticing.webserver.entity.AttributeList
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.entity.SelectedMetadata
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import cz.vutbr.fit.knot.enticing.webserver.service.EnticingUserService
import cz.vutbr.fit.knot.enticing.webserver.service.EqlCompilerService
import cz.vutbr.fit.knot.enticing.webserver.service.QueryService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.net.URLEncoder
import java.util.*

@WebMvcTest
@Import(PasswordEncoderConfiguration::class)
@ExtendWith(SpringExtension::class)
internal class SecurityConfigTest(
        @Autowired val mockMvc: MockMvc,
        @Autowired val encoder: PasswordEncoder,
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

    @Nested
    inner class Login {

        @Test
        fun `Try to login with valid username`() {
            val userEntity = UserEntity(login = "dkozak", encryptedPassword = encoder.encode("pass"))
            Mockito.`when`(userService.loadUserByUsername("dkozak")).thenReturn(userEntity)

            mockMvc.perform(post("$apiBasePath/login")
                    .param("username", "dkozak")
                    .param("password", "pass"))
                    .andExpect(status().`is`(302))
                    .andDo {
                        val locationHeader = it.response.getHeader("Location")
                                ?: throw AssertionError("Location header not set")
                        assertThat(locationHeader).endsWith("$apiBasePath/user")
                    }

            Mockito.verify(userService).loadUserByUsername("dkozak")
            Mockito.clearInvocations(userService)
        }

        @Test
        fun `Try to login with invalid credentials`() {
            Mockito.`when`(userService.loadUserByUsername("dkozak")).thenReturn(UserEntity(login = "dkozak", encryptedPassword = encoder.encode("different")))

            mockMvc.perform(post("$apiBasePath/login")
                    .param("username", "dkozak")
                    .param("password", "foo")
            ).andExpect(status().`is`(401))

            Mockito.verify(userService).loadUserByUsername("dkozak")
            Mockito.clearInvocations(userService)
        }


        @Test
        fun `Auth should fail when no credentials are sent`() {
            mockMvc.perform(post("$apiBasePath/login"))
                    .andExpect(status().`is`(401))
        }

        @Test
        fun `Protected urls should fail with 401`() {
            val urls = setOf("$apiBasePath/user-settings", "$apiBasePath/search-settings")
            for (url in urls)
                mockMvc.perform(post(url))
                        .andExpect(status().`is`(401))
        }
    }

    @Nested
    inner class PathAccessibility {


        @Test
        fun `Logout is accessible`() {
            mockMvc.perform(get("$apiBasePath/logout"))
                    .andExpect(status().isOk)
        }

        @Test
        fun `Root url is accessible`() {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk)
        }

        @Test
        fun `Icon url is accessible`() {
            mockMvc.perform(get("/favicon.ico"))
                    .andExpect(status().isOk)
        }

        @Test
        fun `static css folder is accessible`() {
            mockMvc.perform(get("/static/css"))
                    .andExpect(status().isOk)
        }

        @Test
        fun `static js folder is accessible`() {
            mockMvc.perform(get("/static/js"))
                    .andExpect(status().isOk)
        }

        @Nested
        inner class User {

            @Test
            fun `Signup is accessible`() {
                val user = UserCredentials("dkozak", "pass12")
                mockMvc.perform(post("$apiBasePath/user")
                        .content(ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk)
            }


            @Test
            @WithMockUser
            fun `Get is accessible when logged in`() {
                mockMvc.perform(get("$apiBasePath/user"))
                        .andExpect(status().isOk)
            }

            @Test
            fun `Get is not accessible when not logged in`() {
                mockMvc.perform(get("$apiBasePath/user"))
                        .andExpect(status().`is`(401))
            }

            @Test
            @WithMockUser
            fun `Update is accessible when logged in`() {
                val userEntity = UserEntity(id = 42, login = "login")
                val userDto = userEntity.toUser()

                mockMvc.perform(put("$apiBasePath/user")
                        .content(ObjectMapper().writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk)

                Mockito.verify(userService).updateUser(userDto)
                Mockito.clearInvocations(userService)
            }

            @Test
            fun `Update is not accessible when not logged in`() {
                val userEntity = UserEntity(id = 42, login = "login")
                val userDto = userEntity.toUser()

                mockMvc.perform(put("$apiBasePath/user")
                        .content(ObjectMapper().writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().`is`(401))
                Mockito.verifyZeroInteractions(userService)
                Mockito.clearInvocations(userService)
            }

            @Test
            fun `Delete is not accessible when not logged in`() {
                mockMvc.perform(delete("$apiBasePath/user/1"))
                        .andExpect(status().`is`(401))
            }

            @Test
            @WithMockUser
            fun `Delete is accessible when logged in`() {
                mockMvc.perform(delete("$apiBasePath/user/1"))
                        .andExpect(status().isOk)
            }


            @Test
            fun `Load wanted metadata is not accessible when not logged in`() {
                mockMvc.perform(get("$apiBasePath/user/text-metadata/42"))
                        .andExpect(status().`is`(401))
            }

            @Test
            @WithMockUser
            fun `Load wanted metadata is accessible when logged in`() {
                mockMvc.perform(get("$apiBasePath/user/text-metadata/42"))
                        .andExpect(status().isOk)
            }

            @Test
            @WithMockUser
            fun `save wanted metadata is accessible when looged in`() {
                val metadata = SelectedMetadata(10, listOf("word", "lemma"), mapOf("person" to AttributeList("name", "age")))
                Mockito.`when`(userService.loadSelectedMetadata(42))
                        .thenReturn(metadata)
                mockMvc.perform(post("$apiBasePath/user/text-metadata/42")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metadata.toJson()))
                        .andExpect(status().isOk)
            }
        }

        @Nested
        inner class Admin {

            @Test
            fun `Get all users not accessible when not logged in`() {
                mockMvc.perform(get("$apiBasePath/user/all"))
                        .andExpect(status().`is`(401))
            }

            @Test
            @WithMockUser
            fun `Get all users not accessible when not admin`() {
                mockMvc.perform(get("$apiBasePath/user/all"))
                        .andExpect(status().`is`(403))
            }

            @Test
            @WithMockUser(roles = ["ADMIN"])
            fun `Get all users accessible for admin`() {
                mockMvc.perform(get("$apiBasePath/user/all"))
                        .andExpect(status().`is`(200))
            }

            @Test
            fun `Create new user is not when not logged in`() {
                val user = CreateUserRequest("john5", "foo12", setOf())
                mockMvc.perform(post("$apiBasePath/user/add")
                        .content(user.toJson())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().`is`(401))
            }

            @Test
            @WithMockUser
            fun `Create new user is not accessible for not admin`() {
                val user = CreateUserRequest("john5", "foo12", setOf())
                mockMvc.perform(post("$apiBasePath/user/add")
                        .content(user.toJson())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().`is`(403))
            }

            @Test
            @WithMockUser(roles = ["ADMIN"])
            fun `Create new user is accessible for admin`() {
                val user = CreateUserRequest("john5", "foo12", setOf())
                mockMvc.perform(post("$apiBasePath/user/add").content(user.toJson())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk)
                Mockito.clearInvocations(userService)
            }

        }

        @Nested
        inner class SearchSettingsTest {

            @Test
            fun `Get is accessible`() {
                mockMvc.perform(get("$apiBasePath/search-settings"))
                        .andExpect(status().isOk)
                Mockito.clearInvocations(searchSettingsRepository)
            }

            @Test
            fun `Post put delete require auth`() {
                val methods: List<(String) -> MockHttpServletRequestBuilder> = listOf(
                        { path: String -> post(path) },
                        { path: String -> put(path) },
                        { path: String -> delete(path) }
                )
                for (method in methods) {
                    mockMvc.perform(method("$apiBasePath/search-settings"))
                            .andExpect(status().`is`(401))
                }
            }

            @WithMockUser
            @Test
            fun `Post put delete require admin role failure`() {
                val methods: List<(String) -> MockHttpServletRequestBuilder> = listOf(
                        { path: String -> post(path) },
                        { path: String -> put(path) },
                        { path: String -> delete(path) }
                )
                for (method in methods) {
                    mockMvc.perform(method("$apiBasePath/search-settings"))
                            .andExpect(status().`is`(403))
                }
            }

            @WithMockUser(roles = ["ADMIN"])
            @Test
            fun `Importing is accessible for admin`() {
                val importedSettings = ImportedSearchSettings("import", "foo.com", "baz.com", setOf("127.0.0.1:20"))
                val searchSettings = importedSettings.toEntity()
                Mockito.`when`(searchSettingsRepository.save(searchSettings)).thenReturn(searchSettings)
                mockMvc.perform(post("$apiBasePath/search-settings/import")
                        .content(ObjectMapper().writeValueAsString(importedSettings))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk)

                Mockito.clearInvocations(searchSettingsRepository)
            }

            @Test
            fun `Importing is not accessible when not logged in`() {
                val importedSettings = ImportedSearchSettings("import", "foo.com", "baz.com", setOf("127.0.0.1:20"))

                mockMvc.perform(post("$apiBasePath/search-settings/import")
                        .content(ObjectMapper().writeValueAsString(importedSettings))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().`is`(401))

                Mockito.verifyZeroInteractions(searchSettingsRepository)
                Mockito.clearInvocations(searchSettingsRepository)
            }


            @WithMockUser
            @Test
            fun `Importing is not accessible for not admin`() {
                val importedSettings = ImportedSearchSettings("import", "foo.com", "baz.com", setOf("127.0.0.1:20"))

                mockMvc.perform(post("$apiBasePath/search-settings/import")
                        .content(ObjectMapper().writeValueAsString(importedSettings))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().`is`(403))

                Mockito.verifyZeroInteractions(searchSettingsRepository)
                Mockito.clearInvocations(searchSettingsRepository)
            }

            @WithMockUser(roles = ["ADMIN"])
            @Test
            fun `Post put delete require admin role success`() {
                val searchSettings = SearchSettings(1, "foo", annotationServer = "foo.baz", annotationDataServer = "baz.paz", servers = setOf("127.0.0.1"))
                Mockito.`when`(searchSettingsRepository.save(searchSettings)).thenReturn(searchSettings)
                Mockito.`when`(searchSettingsRepository.findById(1)).thenReturn(Optional.of(searchSettings))

                mockMvc.perform(post("$apiBasePath/search-settings")
                        .content(ObjectMapper().writeValueAsString(searchSettings))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().`is`(200))

                mockMvc.perform(put("$apiBasePath/search-settings")
                        .content(ObjectMapper().writeValueAsString(searchSettings))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().`is`(200))

                mockMvc.perform(delete("$apiBasePath/search-settings/1"))
                        .andExpect(status().`is`(200))
                Mockito.clearInvocations(searchSettingsRepository)
            }

            @Test
            fun `Make default not accessible when not logged in`() {
                mockMvc.perform(put("$apiBasePath/search-settings/default/1"))
                        .andExpect(status().`is`(401))
            }

            @Test
            @WithMockUser
            fun `Make default not accessible when not admin`() {
                mockMvc.perform(put("$apiBasePath/search-settings/default/1"))
                        .andExpect(status().`is`(403))
            }

            @Test
            @WithMockUser(roles = ["ADMIN"])
            fun `Make default accessible for admin`() {
                val dummy = SearchSettings(0, "foo", annotationServer = "foo.baz", annotationDataServer = "baz.paz", servers = setOf("127.0.0.1"), default = true)
                Mockito.`when`(searchSettingsRepository.findById(1))
                        .thenReturn(Optional.of(dummy))

                mockMvc.perform(put("$apiBasePath/search-settings/default/1"))
                        .andExpect(status().`is`(200))

                Mockito.clearInvocations(searchSettingsRepository)
            }

            @Test
            fun `corpus format is always accessible`() {
                val url = "$apiBasePath/query/format/42"

                mockMvc.perform(get(url))
                        .andExpect(status().isOk)

                Mockito.clearInvocations(searchSettingsRepository)
            }
        }

        @Test
        fun `Selecting is not accessible when not logged in`() {
            mockMvc.perform(get("$apiBasePath/search-settings/select/1"))
                    .andExpect(status().`is`(401))
        }

        @WithMockUser
        @Test
        fun `Selecting is accessible when logged in`() {
            mockMvc.perform(get("$apiBasePath/search-settings/select/1"))
                    .andExpect(status().`is`(200))
        }

    }

    @Nested
    inner class Query {

        @Test
        fun `Query is always accessible`() {
            val query = URLEncoder.encode("ahoj cau", "UTF-8")
            val selectedSettings = 1
            mockMvc.perform(get("$apiBasePath/query?query=$query&settings=$selectedSettings"))
                    .andExpect(status().isOk)
        }

        @Test
        fun `Context is always accessible`() {
            val query = WebServer.ContextExtensionQuery("foo.baz", "col1", 2, 201, 42, 10)
            mockMvc.perform(post("$apiBasePath/query/context")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(query.toJson()))
                    .andExpect(status().isOk)
        }

        @Test
        fun `Document is always accessible`() {
            val query = WebServer.DocumentQuery("google.com", "col1", 1, TextMetadata.Predefined("none"), query = "foo")
            mockMvc.perform(post("$apiBasePath/query/document")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(query.toJson()))
                    .andExpect(status().isOk)
        }
    }

    @Nested
    inner class Eql {
        @Test
        fun `Parser endpoint is always accessible`() {
            val query = "nertag:person (killed|visited)"
            val encodedQuery = URLEncoder.encode(query, "UTF-8")
            Mockito.`when`(compilerService.parseQuery(query)).thenReturn(ParsedQuery(DummyRoot()))

            mockMvc.perform(get("$apiBasePath/compiler?query=$encodedQuery"))
                    .andExpect(status().isOk)

            Mockito.clearInvocations(compilerService)
        }
    }
}


