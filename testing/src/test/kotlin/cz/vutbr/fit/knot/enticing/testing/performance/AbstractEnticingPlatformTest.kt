package cz.vutbr.fit.knot.enticing.testing.performance

import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.measure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class AbstractEnticingPlatformTest(
        val fixture: EnticingTestFixture
) {
    val logger = SimpleStdoutLoggerFactory.logger { }

    @Test
    fun `queries from file`() {
        val counts = mutableMapOf<String, Int>()
        val queries = listOf(
                "person",
                "water",
                "horse",
                "nertag:person",
                "nertag:person nertag:person"
        )
        for (query in queries) {
            val result = fixture.sendQuery(SearchQuery(query, snippetCount = 10_000))
            counts[query] = result.searchResults.size
        }

        for ((query, count) in counts)
            println("'$query' => $count")
    }

    @Test
    fun `all results for example query`() {
        val counts = mutableMapOf<Int, Pair<Int, Int>>()
        val query = "a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url"
        for (count in listOf(50, 100, 200, 500, 1_000, 3_000, 10_000)) {
            var result = fixture.sendQuery(SearchQuery(query, snippetCount = count))
            var resultCount = result.searchResults.size
            var paginationCount = 1
            println("Round $paginationCount, got ${result.searchResults.size} new results")
            while (result.hasMore) {
                result = fixture.getMore()
                paginationCount++
                println("Round $paginationCount, got ${result.searchResults.size} new results")
                resultCount += result.searchResults.size
            }
            counts[count] = paginationCount to resultCount
        }
        for ((snippetCount, pair) in counts) {
            val (paginationCount, resultCount) = pair
            println("Query '$query',snippetCount $snippetCount => $resultCount in $paginationCount iterations")
        }
        // size 2222 => 32298
        // size 3333 => 32788
        // size 5131 => 31874
        // size 7342 => 31699
        // size 10_000 => 31190
    }


    @Test
    fun `collect all results`() {
        val counts = mutableMapOf<String, Pair<Int, Int>>()
        val queries = listOf(
                "person",
                "water",
                "horse",
                "nertag:person",
                "nertag:person nertag:person"
        )
        for (query in queries) {
            var result = fixture.sendQuery(SearchQuery(query, snippetCount = 20))
            var resultCount = result.searchResults.size
            var paginationCount = 1
            while (result.hasMore) {
                result = fixture.getMore()
                paginationCount++
                resultCount += result.searchResults.size
            }
            counts[query] = paginationCount to resultCount
        }

        for ((query, count) in counts) {
            val (pagination, result) = count
            println("Query '$query' => $result in $pagination iterations")
        }
    }

    @Test
    fun `ask simple query hello`() {
        logger.measure("query hello") {
            val result = fixture.sendQuery(SearchQuery("hello"))
            println(result)
        }
    }


    @Test
    fun `ask queries from file`() {
        val queries = File("../management-core/src/test/resources/queries.eql").readLines()
        for (query in queries) {
            logger.measure(query) {
                val result = fixture.sendQuery(query)
            }
        }
    }

    @Test
    fun `fuzz test`() = runBlocking(context = Dispatchers.IO) {
        val queries = File("../management-core/src/test/resources/queries.eql").readLines()
        for (query in queries) {
            repeat(10) {
                launch {
                    logger.measure(query) {
                        val result = fixture.sendQuery(query)
                    }
                }
            }
        }
    }

    @AfterAll
    fun close() {
        fixture.close()
    }

}