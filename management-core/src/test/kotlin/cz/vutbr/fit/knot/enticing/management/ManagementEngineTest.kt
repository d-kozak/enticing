package cz.vutbr.fit.knot.enticing.management

import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test


class ManagementEngineTest {

    @Test
    @Disabled
    fun `just config`() {
        val conf = parseCliArgs(arrayOf("../dto/src/test/resources/config.kts"))
                .validateOrFail()
        val executor = ManagementEngine(conf.configuration, SimpleStdoutLoggerFactory)
        executor.execute(conf)
    }

}