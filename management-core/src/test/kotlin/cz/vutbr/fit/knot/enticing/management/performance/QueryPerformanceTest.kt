package cz.vutbr.fit.knot.enticing.management.performance

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitString
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.log.StdoutLogService
import cz.vutbr.fit.knot.enticing.log.measuring
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File

/**
 * assumes the webserver and index servers are running
 */
@Incomplete("startup the server automatically instead of relying on it being running")
@Disabled
class QueryPerformanceTest {

    /**
     * id of the search settings to use
     */
    val settingsId = 2

    val config = executeScript<EnticingConfiguration>("../deploy/small-wiki/testConfig.kts").validateOrFail()

    val webserver = config.webserverConfiguration.address

    val queryEndpoint = "http://$webserver:8080/api/v1/query?settings=$settingsId"

    val logger = StdoutLogService(config.loggingConfiguration)
            .measuring(config.loggingConfiguration)

    private suspend fun sendQuery(query: String): WebServer.ResultList = Fuel.post(queryEndpoint)
            .timeout(60_000)
            .jsonBody(SearchQuery("hello").toJson())
            .awaitString()
            .toDto()


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
}