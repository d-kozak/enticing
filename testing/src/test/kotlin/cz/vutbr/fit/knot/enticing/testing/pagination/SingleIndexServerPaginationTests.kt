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
abstract class SingleIndexServerPaginationTests(private val target: QueryTarget) {

    @Disabled
    class Knot01 : SingleIndexServerPaginationTests(QueryTarget.IndexServerTarget("knot01.fit.vutbr.cz:5627", SimpleStdoutLoggerFactory))

    @Disabled
    class Knot25 : SingleIndexServerPaginationTests(QueryTarget.IndexServerTarget("knot25.fit.vutbr.cz:5627", SimpleStdoutLoggerFactory))

    @Disabled
    class Athena12 : SingleIndexServerPaginationTests(QueryTarget.IndexServerTarget("athena12.fit.vutbr.cz:5627", SimpleStdoutLoggerFactory))

    private val queryResultCount = ConcurrentHashMap<String, Int>()

    private val logger = stdoutLogger { }

    @Test
    @DisplayName("horse")
    fun one() {
        val query = "horse"
        queryResultCount[query] = target.checkPagination(query) // 3059 for knot01
    }

    @Test
    @DisplayName("water earth")
    fun two() {
        val query = "water earth"
        queryResultCount[query] = target.checkPagination(query) // 238 for knot01
    }

    @Test
    @DisplayName("person.name:Jon*")
    fun three() {
        val query = "person.name:Jon*" // 6878 for knot01
        queryResultCount[query] = target.checkPagination(query)
    }

    @Test
    @DisplayName("location.name:Bac*")
    fun four() {
        val query = "location.name:Bac*" // 481 for knot01
        queryResultCount[query] = target.checkPagination(query)
    }

    @Test
    @DisplayName("person.name:Jon* nertag:location")
    fun five() {
        val query = "person.name:Jon* nertag:location"
        queryResultCount[query] = target.checkPagination(query) // 10010 for knot01
    }

    @Test
    @DisplayName("person.name:Jon* nertag:location ctx:par")
    fun six() {
        val query = "person.name:Jon* nertag:location ctx:par"
        queryResultCount[query] = target.checkPagination(query) // 3316 for knot01
    }

    @Test
    @DisplayName("person.name:Jon* nertag:location ctx:sent")
    fun seven() {
        val query = "person.name:Jon* nertag:location ctx:sent"
        queryResultCount[query] = target.checkPagination(query) // 1604 for knot01
    }

    @Test
    @DisplayName("a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url")
    fun eight() {
        val query = "a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url"
        queryResultCount[query] = target.checkPagination(query) // 897 for knot01
    }


    @AfterAll
    fun afterAll() {
        logger.info("Total result count per query")
        queryResultCount.forEach(::println)
    }
}