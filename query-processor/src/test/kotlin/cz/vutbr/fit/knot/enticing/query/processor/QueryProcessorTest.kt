package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.query.*
import cz.vutbr.fit.knot.enticing.dto.response.Match
import cz.vutbr.fit.knot.enticing.dto.response.Payload
import cz.vutbr.fit.knot.enticing.dto.response.SearchResult
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.query.processor.request.ServerInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class QueryProcessorTest {

    data class FailOnPurposeException(val msg: String) : Exception()

    private val templateQuery = SearchQuery(
            "foo bar baz",
            20,
            Offset(0, 0),
            TextMetadata.Predefined("none"),
            ResponseType.SNIPPET,
            ResponseFormat.JSON
    )


    private val googleFirstResult = SearchResult(
            listOf(Match(
                    "col",
                    10,
                    10,
                    5,
                    "google.com",
                    "title",
                    Payload.Snippet.Html("texty text"),
                    false
            )),
            Offset(42, 84)
    )

    @Test
    fun `everything fails test`() {
        val servers = listOf(ServerInfo("yahoo.com"), ServerInfo("google.com"), ServerInfo("foo.bar"))

        val fail: ContactServer = { _, server -> MResult.failure(FailOnPurposeException(server.address)) }

        val result = process(templateQuery, servers, fail)
        val expected: Map<String, List<MResult<SearchResult>>> = mapOf(
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

        val contactServer: ContactServer = { query, server ->
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

        val result = process(templateQuery, servers, contactServer)
        val expected: Map<String, List<MResult<SearchResult>>> = mapOf(
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

        val fail: ContactServer = { _, server ->
            when {
                server.address != "google.com" -> MResult.failure(FailOnPurposeException(server.address))
                server.offset == Offset(10, 20) -> MResult.success(googleFirstResult)
                server.offset == Offset(42, 84) -> MResult.failure(FailOnPurposeException(server.address))
                else -> throw AssertionError("Should never get here, server $server")
            }
        }

        val result = process(templateQuery, servers, fail)
        val expected: Map<String, List<MResult<SearchResult>>> = mapOf(
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