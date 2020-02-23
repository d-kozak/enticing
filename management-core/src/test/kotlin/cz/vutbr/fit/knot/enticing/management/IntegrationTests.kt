package cz.vutbr.fit.knot.enticing.management

import cz.vutbr.fit.knot.enticing.log.StdoutLogService
import cz.vutbr.fit.knot.enticing.log.measuring
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * Tests triggering the whole machinery in the management service including starting remote services.
 * They should be run only from within the KNOT network and MANUALLY, that's why they are disabled by default (not a good candidate for CI)
 */
@Disabled
class IntegrationTests {

    private fun runCliApp(args: String) {
        val cliArgs = parseCliArgs(args.split("""\s+""".toRegex()).toTypedArray()).validateOrFail()
        val config = cliArgs.configuration
        val logger = StdoutLogService(config.loggingConfiguration).measuring(config.loggingConfiguration)
        val engine = ManagementEngine(config, logger)
        engine.use {
            engine.execute(cliArgs)
        }
    }

    @Nested
    @Disabled
    inner class SmallTestConfig {

        @Test
        @Disabled
        fun `run what I want`() {
            runCliApp("../deploy/small-wiki/testConfig.kts -wi")
        }

        //@Test
        @Disabled
        fun `whole pipeline`() {
            runCliApp("../deploy/small-wiki/testConfig.kts --remove -dpwi")
        }

    }

    @Disabled
    inner class FullWiki {

        @Test
        @Disabled
        fun `run what I want`() {
            runCliApp("../dto/src/test/resources/config.kts -wi")
        }

        //    @Test
        @Disabled
        fun `build remotely`() {
            runCliApp("../dto/src/test/resources/config.kts -b")
        }

        //    @Test
        @Disabled
        fun `distribute corpus`() {
            runCliApp("../dto/src/test/resources/config.kts -d")
        }

        //        @Test
        @Disabled
        fun `print corpus`() {
            runCliApp("../dto/src/test/resources/config.kts --print")
        }

        //        @Test
        @Disabled
        fun `remove files`() {
            runCliApp("../dto/src/test/resources/config.kts --remove")
        }

        //    @Test
        @Disabled
        fun `start indexing`() {
            runCliApp("../dto/src/test/resources/config.kts -p")
        }

        //        @Test
        @Disabled
        fun `start webserver`() {
            runCliApp("../dto/src/test/resources/config.kts -w")
        }

        //        @Test
        @Disabled
        fun `kill webserver`() {
            runCliApp("../dto/src/test/resources/config.kts -wk")
        }

        //        @Test
        @Disabled
        fun `start index servers`() {
            runCliApp("../dto/src/test/resources/config.kts -i")
        }

        //            @Test
        @Disabled
        fun `kill index servers`() {
            runCliApp("../dto/src/test/resources/config.kts -ik")
        }


        //        @Test
        @Disabled
        fun `start all`() {
            runCliApp("../dto/src/test/resources/config.kts -wi")
        }

        //    @Test
        @Disabled
        fun `kill all`() {
            runCliApp("../dto/src/test/resources/config.kts -wik")
        }
    }

}