package cz.vutbr.fit.knot.enticing.testing.performance

import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.index.client.QueryTarget
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Disabled
class DuplicateDetectionTest {

    private val target = QueryTarget.IndexServerTarget("knot01.fit.vutbr.cz:5627", SimpleStdoutLoggerFactory)

    private val templateQuery = SearchQuery("water earth", textFormat = TextFormat.PLAIN_TEXT)

    @Test
    fun `all results for query water`() {
        val resultCountPerSnippetCount = mutableMapOf<Int, Int>()
        for (count in listOf(100, 200, 1000)) {
            val query = templateQuery.copy(snippetCount = count)
            val (results, duration) = target.getAll(query)
            resultCountPerSnippetCount[count] = results.size
        }
        resultCountPerSnippetCount.forEach { println(it) }
    }
}