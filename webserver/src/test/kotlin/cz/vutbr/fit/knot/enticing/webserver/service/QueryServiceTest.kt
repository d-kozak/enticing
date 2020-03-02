package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcherException
import cz.vutbr.fit.knot.enticing.webserver.dto.User
import cz.vutbr.fit.knot.enticing.webserver.dto.UserSettings
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.exception.InvalidSearchSettingsException
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.service.mock.*
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.mock.web.MockHttpSession
import java.util.*


internal class QueryServiceTest {

    @Nested
    inner class SearchQueryTest {

        private val dispatcher: QueryDispatcher<SearchQuery, Map<CollectionName, Offset>, IndexServer.IndexResultList> = mockk()
        private val searchSettingsRepository: SearchSettingsRepository = mockk()
        private val userService: EnticingUserService = mockk()
        private val userHolder: CurrentUserHolder = mockk()
        private val indexServerConnector: IndexServerConnector = mockk()
        private val compilerService: EqlCompilerService = mockk()
        private val corpusFormatService: CorpusFormatService = mockk()
        private val mockSession = MockHttpSession()

        @Test
        fun `valid request`() {

            val mockUser = User(login = "foo", userSettings = UserSettings(33))
            every { userHolder.getCurrentUser() } returns mockUser
            every { indexServerConnector.getFormat(any()) } returns CorpusFormat("dummy", emptyMap(), emptyMap())
            every { compilerService.validateOrFail("nertag:person", any()) } returns Unit

            val dummyServers = setOf("google", "amazon", "twitter")
            val mockSearchSettings = SearchSettings(42, private = false, servers = dummyServers)
            every { searchSettingsRepository.findById(42) } returns Optional.of(mockSearchSettings)
            every { corpusFormatService.loadFormat(mockSearchSettings) } returns CorpusFormat("mock", emptyMap(), emptyMap())

            val expected = WebServer.ResultList(listOf(firstResult))
            val dummyQuery = SearchQuery("nertag:person", snippetCount = 33)
            val foo = mapOf("server1" to listOf(MResult.success(IndexServer.IndexResultList(listOf(firstResult.toIndexServerFormat())))))
            every { dispatcher.dispatchQuery(dummyQuery, dummyServers.map { IndexServerRequestData(it) }) } returns foo

            val queryService = QueryService(dispatcher, searchSettingsRepository, userHolder, indexServerConnector, compilerService, corpusFormatService, SimpleStdoutLoggerFactory)

            val result = queryService.query(SearchQuery("nertag:person", 33), 42, mockSession)
            assertThat(result)
                    .isEqualTo(expected)

            verify(exactly = 1) { userHolder.getCurrentUser() }
            verify(exactly = 1) { searchSettingsRepository.findById(42) }
            verify(exactly = 1) { dispatcher.dispatchQuery(dummyQuery, dummyServers.map { IndexServerRequestData(it) }) }
        }

        @Test
        fun `private search settings user is admin so legal`() {
            val mockUser = User(roles = setOf("ADMIN"), login = "foo", userSettings = UserSettings(33))
            every { userHolder.getCurrentUser() } returns mockUser
            every { indexServerConnector.getFormat(any()) } returns CorpusFormat("dummy", emptyMap(), emptyMap())
            every { compilerService.validateOrFail("nertag:person", any()) } returns Unit

            val dummyServers = setOf("google", "amazon", "twitter")
            val mockSearchSettings = SearchSettings(42, name = "mock", private = true, servers = dummyServers)
            every { searchSettingsRepository.findById(42) } returns Optional.of(mockSearchSettings)

            every { corpusFormatService.loadFormat(mockSearchSettings) } returns CorpusFormat("mock", emptyMap(), emptyMap())

            val expected = WebServer.ResultList(listOf(firstResult))
            val dummyQuery = SearchQuery("nertag:person", snippetCount = 33)
            val foo = mapOf("server1" to listOf(MResult.success(IndexServer.IndexResultList(listOf(firstResult.toIndexServerFormat())))))
            every { dispatcher.dispatchQuery(dummyQuery, dummyServers.map { IndexServerRequestData(it) }) } returns foo

            val queryService = QueryService(dispatcher, searchSettingsRepository, userHolder, indexServerConnector, compilerService, corpusFormatService, SimpleStdoutLoggerFactory)

            val result = queryService.query(SearchQuery("nertag:person", 33), 42, mockSession)
            assertThat(result)
                    .isEqualTo(expected)

            verify(exactly = 1) { userHolder.getCurrentUser() }
            verify(exactly = 1) { searchSettingsRepository.findById(42) }
            verify(exactly = 1) { dispatcher.dispatchQuery(dummyQuery, dummyServers.map { IndexServerRequestData(it) }) }
        }

        @Test
        fun `private search settings user is not admin so fail`() {
            val mockUser = User(login = "foo", userSettings = UserSettings(33))
            every { userHolder.getCurrentUser() } returns mockUser

            val dummyServers = setOf("google", "amazon", "twitter")
            val mockSearchSettings = SearchSettings(42, private = true, servers = dummyServers)
            every { searchSettingsRepository.findById(42) } returns Optional.of(mockSearchSettings)

            val queryService = QueryService(dispatcher, searchSettingsRepository, userHolder, indexServerConnector, compilerService, corpusFormatService, SimpleStdoutLoggerFactory)

            assertThrows<InvalidSearchSettingsException> {
                queryService.query(SearchQuery("nertag:person", 33), 42, mockSession)
            }
        }

        @Test
        fun `private search settings user is not logged in so fail`() {
            every { userHolder.getCurrentUser() } returns null

            val dummyServers = setOf("google", "amazon", "twitter")
            val mockSearchSettings = SearchSettings(42, private = true, servers = dummyServers)
            every { searchSettingsRepository.findById(42) } returns Optional.of(mockSearchSettings)

            val queryService = QueryService(dispatcher, searchSettingsRepository, userHolder, indexServerConnector, compilerService, corpusFormatService, SimpleStdoutLoggerFactory)

            assertThrows<InvalidSearchSettingsException> {
                queryService.query(SearchQuery("nertag:person", 33), 42, mockSession)
            }
        }

        @Test
        fun `search settings not found so fail`() {
            every { userHolder.getCurrentUser() } returns null
            every { searchSettingsRepository.findById(42) } returns Optional.empty()

            val queryService = QueryService(dispatcher, searchSettingsRepository, userHolder, indexServerConnector, compilerService, corpusFormatService, SimpleStdoutLoggerFactory)

            assertThrows<InvalidSearchSettingsException> { queryService.query(SearchQuery("nertag:person", 33), 42, mockSession) }
        }

        @AfterEach
        fun clearMock() {
            clearMocks(dispatcher, searchSettingsRepository, userService, indexServerConnector)
        }
    }


    @Nested
    inner class DocumentRetrieval {

        private val dispatcher: QueryDispatcher<SearchQuery, Map<CollectionName, Offset>, IndexServer.IndexResultList> = mockk()
        private val searchSettingsRepository: SearchSettingsRepository = mockk()
        private val userService: EnticingUserService = mockk()
        private val userHolder: CurrentUserHolder = mockk()
        private val indexServerConnector: IndexServerConnector = mockk()
        private val compilerService: EqlCompilerService = mockk()
        private val corpusFormatService: CorpusFormatService = mockk()

        @Test
        fun `valid request`() {
            val queryService = QueryService(dispatcher, searchSettingsRepository, userHolder, indexServerConnector, compilerService, corpusFormatService, SimpleStdoutLoggerFactory)
            val query = WebServer.DocumentQuery("google", "col1", 21, query = "nertag:person")
            val expected = dummyDocument.copy(host = "google", collection = "col1", documentId = 21, query = "nertag:person")

            every { indexServerConnector.getDocument(query) } returns expected.toIndexFormat()
            val actual = queryService.document(query)
            assertThat(actual)
                    .isEqualTo(expected)

        }

        @Test
        fun `exception propagated`() {
            val queryService = QueryService(dispatcher, searchSettingsRepository, userHolder, indexServerConnector, compilerService, corpusFormatService, SimpleStdoutLoggerFactory)
            val query = WebServer.DocumentQuery("google", "col1", 21)

            every { indexServerConnector.getDocument(any()) } throws IllegalArgumentException("booom!")
            assertThrows<IllegalArgumentException> {
                queryService.document(query)
            }
        }

        @AfterEach
        fun clearMock() {
            clearMocks(dispatcher, searchSettingsRepository, userService, indexServerConnector)
        }
    }

    @Nested
    inner class ContextExtension {

        private val dispatcher: QueryDispatcher<SearchQuery, Map<CollectionName, Offset>, IndexServer.IndexResultList> = mockk()
        private val searchSettingsRepository: SearchSettingsRepository = mockk()
        private val userService: EnticingUserService = mockk()
        private val userHolder: CurrentUserHolder = mockk()
        private val indexServerConnector: IndexServerConnector = mockk()
        private val compilerService: EqlCompilerService = mockk()
        private val corpusFormatService: CorpusFormatService = mockk()

        @Test
        fun `valid request`() {
            val queryService = QueryService(dispatcher, searchSettingsRepository, userHolder, indexServerConnector, compilerService, corpusFormatService, SimpleStdoutLoggerFactory)
            val query = WebServer.ContextExtensionQuery("google", "col1", 21, 30, 20, 40, query = "foo")
            every { indexServerConnector.contextExtension(query) } returns snippetExtension
            val actual = queryService.context(query)
            assertThat(actual)
                    .isEqualTo(snippetExtension)

        }

        @Test
        fun `exception propagated`() {
            val queryService = QueryService(dispatcher, searchSettingsRepository, userHolder, indexServerConnector, compilerService, corpusFormatService, SimpleStdoutLoggerFactory)
            val query = WebServer.ContextExtensionQuery("google", "col1", 21, 30, 20, 40, query = "foo")

            every { indexServerConnector.contextExtension(any()) } throws IllegalArgumentException("booom!")
            assertThrows<IllegalArgumentException> {
                queryService.context(query)
            }
        }

        @AfterEach
        fun clearMock() {
            clearMocks(dispatcher, searchSettingsRepository, userService, indexServerConnector)
        }
    }

    @Nested
    inner class Flatten {

        private val dispatcher: QueryDispatcher<SearchQuery, Map<CollectionName, Offset>, IndexServer.IndexResultList> = mockk()
        private val searchSettingsRepository: SearchSettingsRepository = mockk()
        private val userService: EnticingUserService = mockk()
        private val userHolder: CurrentUserHolder = mockk()
        private val indexServerConnector: IndexServerConnector = mockk()
        private val compilerService: EqlCompilerService = mockk()
        private val corpusFormatService: CorpusFormatService = mockk()

        val queryService = QueryService(dispatcher, searchSettingsRepository, userHolder, indexServerConnector, compilerService, corpusFormatService, SimpleStdoutLoggerFactory)

        @Test
        fun `no errors`() {
            val input = mapOf(
                    "server1" to listOf(MResult.success(IndexServer.IndexResultList(listOf(firstResult.toIndexServerFormat())))),
                    "server2" to listOf(MResult.success(IndexServer.IndexResultList(listOf(secondResult.toIndexServerFormat())))),
                    "server3" to listOf(MResult.success(IndexServer.IndexResultList(listOf(thirdResult.toIndexServerFormat()))))
            )
            val actual = queryService.flatten("dummy", input)
            assertThat(actual)
                    .isEqualTo(
                            WebServer.ResultList(listOf(firstResult, secondResult, thirdResult))
                                    to emptyMap<String, Map<String, Offset>>()
                    )
        }

        @Test
        fun `one error`() {
            val input = mapOf(
                    "server1" to listOf(MResult.success(IndexServer.IndexResultList(listOf(firstResult.toIndexServerFormat())))),
                    "server2" to listOf(MResult.success(IndexServer.IndexResultList(listOf(secondResult.toIndexServerFormat())))),
                    "server3" to listOf(MResult.failure(QueryDispatcherException("my secret reason to fail")))
            )
            val actual = queryService.flatten("dummy", input)
            assertThat(actual)
                    .isEqualTo(
                            WebServer.ResultList(listOf(firstResult, secondResult), mapOf("server3" to "QueryDispatcherException:my secret reason to fail"))
                                    to emptyMap<String, Map<String, Offset>>()
                    )
        }

        @Test
        fun `two errors`() {
            val input = mapOf(
                    "server1" to listOf(MResult.success(IndexServer.IndexResultList(listOf(firstResult.toIndexServerFormat()))), MResult.failure(QueryDispatcherException("oh no...again? :("))),
                    "server2" to listOf(MResult.success(IndexServer.IndexResultList(listOf(secondResult.toIndexServerFormat())))),
                    "server3" to listOf(MResult.failure(QueryDispatcherException("my secret reason to fail")))
            )
            val actual = queryService.flatten("dummy", input)
            assertThat(actual)
                    .isEqualTo(
                            WebServer.ResultList(listOf(firstResult, secondResult),
                                    mapOf("server1" to "QueryDispatcherException:oh no...again? :(",
                                            "server3" to "QueryDispatcherException:my secret reason to fail")
                            ) to emptyMap<String, Map<String, Offset>>()
                    )
        }
    }


}