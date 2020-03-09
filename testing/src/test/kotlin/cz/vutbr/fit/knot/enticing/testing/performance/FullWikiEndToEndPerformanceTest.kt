package cz.vutbr.fit.knot.enticing.testing.performance

import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.measure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File

@Disabled
class FullWikiEndToEndPerformanceTest {

    val rebootComponents = true

    val fixture = EnticingTestFixture(FullWiki2018Config, rebootComponents)

    val logger = SimpleStdoutLoggerFactory.logger { }

    @Test
    fun killAll() {
        fixture.killTestSetup()
    }

    @Test
    fun `ask simple query hello`() {
        logger.measure("query hello") {
            val result = fixture.sendQuery("hello")
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
}