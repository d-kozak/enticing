package cz.vutbr.fit.knot.enticing.testing.pagination

import cz.vutbr.fit.knot.enticing.index.client.QueryTarget
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.log.stdoutLogger
import cz.vutbr.fit.knot.enticing.testing.util.checkPagination
import org.junit.jupiter.api.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class PaginationTests(private val target: QueryTarget) {

    class Knot01 : PaginationTests(QueryTarget.IndexServerTarget("knot01.fit.vutbr.cz:5627", SimpleStdoutLoggerFactory))

    private val queryResultCount = ConcurrentHashMap<String, Int>()

    private val logger = stdoutLogger { }

    @Test
    @DisplayName("horse")
    fun one() {
        val query = "horse"
        queryResultCount[query] = target.checkPagination(query)
    }

    @Test
    @DisplayName("water earth")
    fun two() {
        val query = "water earth"
        queryResultCount[query] = target.checkPagination(query)
    }

    @Test
    @DisplayName("nertag:person")
    fun three() {
        val query = "nertag:person"
        queryResultCount[query] = target.checkPagination(query)
    }

    @Test
    @DisplayName("nertag:location")
    fun four() {
        val query = "nertag:location"
        queryResultCount[query] = target.checkPagination(query)
    }

    @Test
    @DisplayName("nertag:person nertag:location")
    fun five() {
        val query = "nertag:person nertag:location"
        queryResultCount[query] = target.checkPagination(query)
    }

    @Test
    @DisplayName("nertag:person nertag:location ctx:par")
    fun six() {
        val query = "nertag:person nertag:location ctx:par"
        queryResultCount[query] = target.checkPagination(query)
    }

    @Test
    @DisplayName("nertag:person nertag:location ctx:sent")
    fun seven() {
        val query = "nertag:person nertag:location ctx:sent"
        queryResultCount[query] = target.checkPagination(query)
    }

    @AfterAll
    fun afterAll() {
        logger.info("Total result count per query")
        queryResultCount.forEach(::println)
    }
}