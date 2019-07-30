package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.Offset
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * lambda to interface conversion
 */
internal fun dummyDispatcher(fn: (SearchQuery, ServerInfo) -> MResult<IndexServer.SearchResult>): RequestDispatcher<ServerInfo> = object : RequestDispatcher<ServerInfo> {
    override suspend fun invoke(searchQuery: SearchQuery, serverInfo: ServerInfo): MResult<IndexServer.SearchResult> = fn(searchQuery, serverInfo)

    override fun createRequestData(address: String, offset: Offset): ServerInfo = ServerInfo(address, offset)
}

internal class QueryDispatcherTest {


    @Test
    fun `everything fails test`() {
        val servers = listOf(ServerInfo("yahoo.com"), ServerInfo("google.com"), ServerInfo("foo.bar"))

        val fail: RequestDispatcher<ServerInfo> = dummyDispatcher { _, server -> MResult.failure(FailOnPurposeException(server.address)) }

        val dispatcher = QueryDispatcher(fail)
        val result = dispatcher.dispatchQuery(templateQuery, servers)
        val expected: Map<String, List<MResult<IndexServer.SearchResult>>> = mapOf(
                "yahoo.com" to listOf(MResult.failure(FailOnPurposeException("yahoo.com"))),
                "google.com" to listOf(MResult.failure(FailOnPurposeException("google.com"))),
                "foo.bar" to listOf(MResult.failure(FailOnPurposeException("foo.bar")))
        )
        assertThat(result)
                .isEqualTo(expected)
    }

    @Test
    fun `servers should be called with specific offsets`() {
        val servers = listOf(ServerInfo("yahoo.com", Offset(1, 2)), ServerInfo("google.com", Offset(3, 4)), ServerInfo("foo.bar", Offset(5, 6)))

        val requestDispatcher: RequestDispatcher<ServerInfo> = dummyDispatcher { query, server ->
            when (server.address) {
                "yahoo.com" -> assertThat(server.offset)
                        .isEqualTo(Offset(1, 2))
                "google.com" -> assertThat(server.offset)
                        .isEqualTo(Offset(3, 4))
                "foo.bar" -> assertThat(server.offset)
                        .isEqualTo(Offset(5, 6))

            }
            MResult.failure(FailOnPurposeException(server.address))
        }

        val dispatcher = QueryDispatcher(requestDispatcher)
        val result = dispatcher.dispatchQuery(templateQuery, servers)
        val expected: Map<String, List<MResult<IndexServer.SearchResult>>> = mapOf(
                "yahoo.com" to listOf(MResult.failure(FailOnPurposeException("yahoo.com"))),
                "google.com" to listOf(MResult.failure(FailOnPurposeException("google.com"))),
                "foo.bar" to listOf(MResult.failure(FailOnPurposeException("foo.bar")))
        )
        assertThat(result)
                .isEqualTo(expected)
    }

    @Test
    fun `first server is successful and is called twice`() {
        val servers = listOf(ServerInfo("yahoo.com"), ServerInfo("google.com", offset = Offset(10, 20)), ServerInfo("foo.bar"))

        val fail: RequestDispatcher<ServerInfo> = dummyDispatcher { _, server ->
            when {
                server.address != "google.com" -> MResult.failure(FailOnPurposeException(server.address))
                server.offset == Offset(10, 20) -> MResult.success(googleFirstResult)
                server.offset == Offset(42, 84) -> MResult.failure(FailOnPurposeException(server.address))
                else -> throw AssertionError("Should never get here, server $server")
            }
        }

        val dispatcher = QueryDispatcher(fail)
        val result = dispatcher.dispatchQuery(templateQuery, servers)
        val expected: Map<String, List<MResult<IndexServer.SearchResult>>> = mapOf(
                "yahoo.com" to listOf(MResult.failure(FailOnPurposeException("yahoo.com"))),
                "google.com" to listOf(MResult.success(googleFirstResult), MResult.failure(FailOnPurposeException("google.com"))),
                "foo.bar" to listOf(MResult.failure(FailOnPurposeException("foo.bar")))
        )
        assertThat(result)
                .isEqualTo(expected)
    }

    @Test
    fun `first has to be called three times second has to be called twice`() {

    }
}