package cz.vutbr.fit.knot.enticing.testing.index.server

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.testing.util.assertUniqueResults
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IndexServerQueryIntegrationTests {

    @BeforeAll
    fun indexData() {
        indexAll()
    }

    @Test
    fun `consistent total amount of results is returned when processing all documents no matter the snippet size in queries`() {
        val service = prepareTestQueryService()
        val results = mutableMapOf<String, Map<Int, Int>>()
        for (query in listOf("water", "is has")) {
            val queryResult = mutableMapOf<Int, Int>()
            for (snippetCount in listOf(10, 20, 30, 75, 100, 200, 300)) {
                val payloads = mutableListOf<IndexServer.SearchResult>()
                var res = service.processQuery(SearchQuery(query, snippetCount))
                assertThat(res.errors).isEmpty()
                payloads.addAll(res.searchResults)
                while (res.offset != null && res.offset!!.isNotEmpty()) {
                    res = service.processQuery(SearchQuery(query, snippetCount, offset = res.offset))
                    assertThat(res.errors).isEmpty()
                    payloads.addAll(res.searchResults)
                }
                assertUniqueResults(payloads)
                queryResult[snippetCount] = payloads.size
            }
            results[query] = queryResult
        }

        results.forEach { println(it) }

        val incorrectResults = results.mapValues { it.value.values.toSet() }
                .filter { it.value.size > 1 }
                .map { it.key }
        if (incorrectResults.isNotEmpty())
            fail { "Queries $incorrectResults did not return the same number of results every time" }
    }
}