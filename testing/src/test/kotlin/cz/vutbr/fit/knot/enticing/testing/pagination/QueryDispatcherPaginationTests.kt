package cz.vutbr.fit.knot.enticing.testing.pagination

import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.index.client.QueryTarget
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.log.stdoutLogger
import kotlinx.coroutines.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class QueryDispatcherPaginationTests(
        vararg servers: String
) {

    private val servers = servers.toList()

    private val dispatcherTarget = QueryTarget.QueryDispatcherTarget(servers.toList(), SimpleStdoutLoggerFactory)
    private val serverTargets = servers.map { QueryTarget.IndexServerTarget(it, SimpleStdoutLoggerFactory) }

    private val logger = stdoutLogger { }

    class FiveKnots : QueryDispatcherPaginationTests("knot01.fit.vutbr.cz:5627", "knot02.fit.vutbr.cz:5627",
            "knot03.fit.vutbr.cz:5627", "knot04.fit.vutbr.cz:5627", "knot05.fit.vutbr.cz:5627")

    class TwoKnots : QueryDispatcherPaginationTests("knot01.fit.vutbr.cz:5627", "knot02.fit.vutbr.cz:5627")

    class AllOfThem : QueryDispatcherPaginationTests(*File("../deploy/big-wiki/servers.txt").readLines()
            .filterNot { it.isBlank() }
            .map { "$it:5627" }
            .toTypedArray()
    )

    private suspend fun executeTest(query: SearchQuery, scope: CoroutineScope) {
        logger.info("Executing query $query")
        logger.info("Using servers $servers")
        val individualResults = serverTargets.map {
            scope.async {
                it.getAll(query).value.size
            }
        }.awaitAll()
        val dispatcherResult = dispatcherTarget.getAll(query).value.size
        logger.info("Individual server results: $individualResults")
        logger.info("QueryDispatcher returned $dispatcherResult")
        assertThat(dispatcherResult)
                .isEqualTo(individualResults.sum())

    }

    @Test
    @DisplayName("horse")
    fun one() = runBlocking(Dispatchers.IO) {
        executeTest(SearchQuery("horse", snippetCount = 137), this)
    }

    @Test
    @DisplayName("person.name:Jon* nertag:location ctx:par")
    fun two() = runBlocking(Dispatchers.IO) {
        executeTest(SearchQuery("person.name:Jon* nertag:location ctx:par", snippetCount = 137), this) // 14037
    }


    @Test
    @DisplayName("a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url")
    fun eight() = runBlocking(Dispatchers.IO) {
        executeTest(SearchQuery("a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url", snippetCount = 300), this)
    }
}