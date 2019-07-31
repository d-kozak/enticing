package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * lambda to interface conversion
 */
internal fun dummyDispatcher(fn: (SearchQuery, RequestData<Map<CollectionName, Offset>>) -> MResult<IndexServer.SearchResult>): RequestDispatcher<SearchQuery,Map<CollectionName,Offset>, IndexServer.SearchResult> = object : RequestDispatcher<SearchQuery,Map<CollectionName,Offset>, IndexServer.SearchResult> {

    override suspend fun invoke(searchQuery: SearchQuery, requestData: RequestData<Map<CollectionName, Offset>>): MResult<IndexServer.SearchResult> = fn(searchQuery, requestData)

}

internal class QueryDispatcherTest {


    @Test
    fun `everything fails test`() {
        val servers = listOf(IndexServerRequestData("yahoo.com"), IndexServerRequestData("google.com"), IndexServerRequestData("foo.bar"))

        val fail: RequestDispatcher<SearchQuery,Map<CollectionName,Offset>, IndexServer.SearchResult> = dummyDispatcher { _, server -> MResult.failure(FailOnPurposeException(server.address)) }

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
        val servers = listOf(IndexServerRequestData("yahoo.com", mapOf("one" to Offset(1, 2))), IndexServerRequestData("google.com", mapOf("two" to Offset(3, 4))), IndexServerRequestData("foo.bar", mapOf("three" to Offset(5, 6))))

        val requestDispatcher: RequestDispatcher<SearchQuery,Map<CollectionName,Offset>, IndexServer.SearchResult> = dummyDispatcher { query, server ->
            when (server.address) {
                "yahoo.com" -> assertThat(server.offset)
                        .isEqualTo(mapOf("one" to Offset(1, 2)))
                "google.com" -> assertThat(server.offset)
                        .isEqualTo(mapOf("two" to Offset(3, 4)))
                "foo.bar" -> assertThat(server.offset)
                        .isEqualTo(mapOf("three" to Offset(5, 6)))

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
        val servers = listOf(IndexServerRequestData("yahoo.com"), IndexServerRequestData("google.com", offset = mapOf("one" to Offset(10, 20))), IndexServerRequestData("foo.bar"))

        val fail: RequestDispatcher<SearchQuery,Map<CollectionName,Offset>, IndexServer.SearchResult> = dummyDispatcher { _, server ->
            when {
                server.address != "google.com" -> MResult.failure(FailOnPurposeException(server.address))
                server.offset == mapOf("one" to Offset(10, 20)) -> MResult.success(googleFirstResult)
                server.offset == mapOf("one" to Offset(42, 84)) -> MResult.failure(FailOnPurposeException(server.address))
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
    @Incomplete("Finished")
    fun `first has to be called three times second has to be called twice`() {

    }
}