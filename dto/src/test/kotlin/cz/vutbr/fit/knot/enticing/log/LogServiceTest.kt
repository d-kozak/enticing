package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.fullConf
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep

class LogServiceTest {

    private val loggingConfiguration = fullConf.loggingConfiguration

    @Test
    fun `send a few messages`() {
        val logger = loggingConfiguration.configureFor("testLogger1")
        logger.debug("just for stdout")
        logger.info("for both")
        logger.perf("for both")
        logger.error("for both")
    }

    @Test
    fun `measured execution`() {
        val logger = loggingConfiguration.configureFor("measuredLogger")
        val result = logger.measure("finding the answer") {
            repeat(5) {
                sleep(1000)
                logger.perf("round $it finished")
            }
            42
        }
        logger.info("The answer is ${result.value}")
    }
}