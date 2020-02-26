package cz.vutbr.fit.knot.enticing.testing.performance

import cz.vutbr.fit.knot.enticing.log.logger
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EndToEndPerformanceTest {

    val rebootComponents = true

    val fixture = SmallWikiTestFixture(rebootComponents)

    val logger = fixture.logService.logger { }

    @Test
    fun foo() {

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
    fun `fuzz test`() = runBlocking {
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
