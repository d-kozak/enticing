package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.dto.Webserver
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.webserver.dto.User
import cz.vutbr.fit.knot.enticing.webserver.dto.UserSettings
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.service.mock.firstResult
import cz.vutbr.fit.knot.enticing.webserver.service.mock.secondResult
import cz.vutbr.fit.knot.enticing.webserver.service.mock.thirdResult
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*


internal class QueryServiceTest {

    @Nested
    inner class SearchQueryTest {

        private val dispatcher: QueryDispatcher = mockk()
        private val searchSettingsRepository: SearchSettingsRepository = mockk()
        private val userService: EnticingUserService = mockk()

        @Test
        fun `valid request`() {

            val mockUser = User(login = "foo", userSettings = UserSettings(33))
            every { userService.currentUser } returns mockUser

            val dummyServers = setOf("google", "amazon", "twitter")
            val mockSearchSettings = SearchSettings(42, private = false, servers = dummyServers)
            every { searchSettingsRepository.findById(42) } returns Optional.of(mockSearchSettings)

            val expected = Webserver.SearchResult(listOf(firstResult))
            val dummyQuery = SearchQuery("nertag:person", snippetCount = 33)
            val foo = mapOf("server1" to listOf(MResult.success(IndexServer.SearchResult(listOf(firstResult.toIndexServerFormat())))))
            every { dispatcher.dispatchQuery(dummyQuery, dummyServers.map { ServerInfo(it) }) } returns foo

            val queryService = QueryService(dispatcher, searchSettingsRepository, userService)

            val result = queryService.query("nertag:person", 42)
            assertThat(result)
                    .isEqualTo(expected)

            verify(exactly = 1) { userService.currentUser }
            verify(exactly = 1) { searchSettingsRepository.findById(42) }
            verify(exactly = 1) { dispatcher.dispatchQuery(dummyQuery, dummyServers.map { ServerInfo(it) }) }
        }

        @Test
        fun `private search settings user is admin so legal`() {
            val mockUser = User(roles = setOf("ADMIN"), login = "foo", userSettings = UserSettings(33))
            every { userService.currentUser } returns mockUser

            val dummyServers = setOf("google", "amazon", "twitter")
            val mockSearchSettings = SearchSettings(42, private = true, servers = dummyServers)
            every { searchSettingsRepository.findById(42) } returns Optional.of(mockSearchSettings)

            val expected = Webserver.SearchResult(listOf(firstResult))
            val dummyQuery = SearchQuery("nertag:person", snippetCount = 33)
            val foo = mapOf("server1" to listOf(MResult.success(IndexServer.SearchResult(listOf(firstResult.toIndexServerFormat())))))
            every { dispatcher.dispatchQuery(dummyQuery, dummyServers.map { ServerInfo(it) }) } returns foo

            val queryService = QueryService(dispatcher, searchSettingsRepository, userService)

            val result = queryService.query("nertag:person", 42)
            assertThat(result)
                    .isEqualTo(expected)

            verify(exactly = 1) { userService.currentUser }
            verify(exactly = 1) { searchSettingsRepository.findById(42) }
            verify(exactly = 1) { dispatcher.dispatchQuery(dummyQuery, dummyServers.map { ServerInfo(it) }) }
        }

        @Test
        fun `private search settings user is not admin so fail`() {
            val mockUser = User(login = "foo", userSettings = UserSettings(33))
            every { userService.currentUser } returns mockUser

            val dummyServers = setOf("google", "amazon", "twitter")
            val mockSearchSettings = SearchSettings(42, private = true, servers = dummyServers)
            every { searchSettingsRepository.findById(42) } returns Optional.of(mockSearchSettings)

            val queryService = QueryService(dispatcher, searchSettingsRepository, userService)

            assertThrows<IllegalArgumentException> {
                queryService.query("foo", 42)
            }
        }

        @Test
        fun `private search settings user is not logged in so fail`() {
            every { userService.currentUser } returns null

            val dummyServers = setOf("google", "amazon", "twitter")
            val mockSearchSettings = SearchSettings(42, private = true, servers = dummyServers)
            every { searchSettingsRepository.findById(42) } returns Optional.of(mockSearchSettings)

            val queryService = QueryService(dispatcher, searchSettingsRepository, userService)

            assertThrows<IllegalArgumentException> {
                queryService.query("foo", 42)
            }
        }

        @Test
        fun `search settings not found so fail`() {
            every { userService.currentUser } returns null
            every { searchSettingsRepository.findById(42) } returns Optional.empty()

            val queryService = QueryService(dispatcher, searchSettingsRepository, userService)

            assertThrows<IllegalArgumentException> { queryService.query("nertag:person", 42) }
        }

        @AfterEach
        fun clearMock() {
            clearMocks(dispatcher, searchSettingsRepository, userService)
        }
    }


    @Nested
    inner class Flatten {

        @Test
        fun `no errors`() {
            val input = mapOf(
                    "server1" to listOf(MResult.success(IndexServer.SearchResult(listOf(firstResult.toIndexServerFormat())))),
                    "server2" to listOf(MResult.success(IndexServer.SearchResult(listOf(secondResult.toIndexServerFormat())))),
                    "server3" to listOf(MResult.success(IndexServer.SearchResult(listOf(thirdResult.toIndexServerFormat()))))
            )
            val actual = flatten(input)
            assertThat(actual)
                    .isEqualTo(
                            Webserver.SearchResult(listOf(firstResult, secondResult, thirdResult))
                    )
        }

        @Test
        fun `one error`() {
            val input = mapOf(
                    "server1" to listOf(MResult.success(IndexServer.SearchResult(listOf(firstResult.toIndexServerFormat())))),
                    "server2" to listOf(MResult.success(IndexServer.SearchResult(listOf(secondResult.toIndexServerFormat())))),
                    "server3" to listOf(MResult.failure(IllegalStateException("my secret reason to fail")))
            )
            val actual = flatten(input)
            assertThat(actual)
                    .isEqualTo(
                            Webserver.SearchResult(listOf(firstResult, secondResult), mapOf("server3" to "IllegalStateException:my secret reason to fail"))
                    )
        }

        @Test
        fun `two errors`() {
            val input = mapOf(
                    "server1" to listOf(MResult.success(IndexServer.SearchResult(listOf(firstResult.toIndexServerFormat()))), MResult.failure(NullPointerException("oh no...again? :("))),
                    "server2" to listOf(MResult.success(IndexServer.SearchResult(listOf(secondResult.toIndexServerFormat())))),
                    "server3" to listOf(MResult.failure(IllegalStateException("my secret reason to fail")))
            )
            val actual = flatten(input)
            assertThat(actual)
                    .isEqualTo(
                            Webserver.SearchResult(listOf(firstResult, secondResult),
                                    mapOf("server1" to "NullPointerException:oh no...again? :(",
                                            "server3" to "IllegalStateException:my secret reason to fail")
                            )
                    )
        }
    }


}