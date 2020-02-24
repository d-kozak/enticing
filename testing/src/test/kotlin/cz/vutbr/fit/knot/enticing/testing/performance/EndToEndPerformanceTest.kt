package cz.vutbr.fit.knot.enticing.testing.performance

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitString
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.log.logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EndToEndPerformanceTest {

    val logger = SmallWikiTestFixture.logger.logger { }

    @BeforeAll
    fun startUp() {
        SmallWikiTestFixture.fullInitialization()
    }

    @Test
    fun `ask simple query hello`() = runBlocking<Unit> {
        logger.measure("query hello") {
            val result = sendQuery("hello")
            println(result)
        }

    }


    @Test
    fun `ask queries from file`() = runBlocking<Unit> {
        val queries = File("../management-core/src/test/resources/queries.eql").readLines()
        for (query in queries) {
            logger.measure(query) {
                val result = sendQuery(query)
            }
        }
    }

    @Test
    fun `fuzz test`() = runBlocking<Unit>(Dispatchers.IO) {
        val queries = File("../management-core/src/test/resources/queries.eql").readLines()
        for (query in queries) {
            repeat(10) {
                launch {
                    logger.measure(query) {
                        val result = sendQuery(query)
                    }
                }
            }
        }
    }

    private suspend fun sendQuery(query: String): WebServer.ResultList = Fuel.post(SmallWikiTestFixture.queryEndpoint)
            .timeout(60_000)
            .jsonBody(SearchQuery(query).toJson())
            .awaitString()
            .toDto()
}
