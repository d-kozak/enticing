package cz.vutbr.fit.knot.enticing.testing.util

import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.index.client.QueryTarget
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import org.junit.jupiter.api.fail
import kotlin.time.ExperimentalTime

private val DEFAULT_QUERIES = listOf(
        "water",
        "is has",
        "a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url"
)

private val DEFAULT_SNIPPET_COUNTS = listOf(10, 20, 30, 75, 100, 200, 300, 1000, 2000, 5000, 10_000)

private val TEMPLATE_QUERY = SearchQuery("", textFormat = TextFormat.PLAIN_TEXT)

@ExperimentalTime
fun QueryTarget.checkPagination(query: String, snippetCounts: List<Int> = DEFAULT_SNIPPET_COUNTS) = checkPagination(listOf(query), snippetCounts)

@ExperimentalTime
fun QueryTarget.checkPagination(queries: List<String> = DEFAULT_QUERIES, snippetCounts: List<Int> = DEFAULT_SNIPPET_COUNTS): Int {
    val logger = SimpleStdoutLoggerFactory.logger { }
    val errors = mutableListOf<String>()
    var cnt = -1
    for (query in queries) {
        val resultCountPerSnippetCount = mutableMapOf<Int, Int>()
        for (snippetCount in snippetCounts) {
            val (results, duration) = this.getAll(TEMPLATE_QUERY.copy(query = query, snippetCount = snippetCount))
            logger.info("Query '$query' snippetCount $snippetCount: returned ${results.size} results, took $duration")
            resultCountPerSnippetCount[snippetCount] = results.size
            cnt = results.size
        }
        logger.info("Total results for query '$query':")
        resultCountPerSnippetCount.forEach { println(it) }
        if (resultCountPerSnippetCount.values.toSet().size != 1) {
            errors.add("Query '$query' had different resultCount: $resultCountPerSnippetCount")
        }
    }
    if (errors.isNotEmpty()) {
        fail(errors.joinToString("\n"))
    }
    return cnt
}