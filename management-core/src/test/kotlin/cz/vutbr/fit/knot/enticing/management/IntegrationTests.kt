package cz.vutbr.fit.knot.enticing.management

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.StdoutLogService
import cz.vutbr.fit.knot.enticing.log.measuring
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * Tests triggering the whole machinery in the management service including starting remote services.
 * They should be run only from within the KNOT network and MANUALLY, that's why they are disabled by default (not a good candidate for CI)
 */
@Disabled
class IntegrationTests {

    private fun initialize(args: String): Triple<ManagementCliArguments, EnticingConfiguration, ManagementEngine> {
        val cliArgs = parseCliArgs(args.split("""\s+""".toRegex()).toTypedArray()).validateOrFail()
        val config = cliArgs.configuration
        val logger = StdoutLogService(config.loggingConfiguration).measuring(config.loggingConfiguration)
        val engine = ManagementEngine(config, logger)
        return Triple(cliArgs, config, engine)
    }


//    @Test
    @Disabled
    fun `build remotely`() {
        val (cliArgs, config, engine) = initialize("../dto/src/test/resources/config.kts -b")
        engine.use {
            it.execute(cliArgs)
        }

    }

    //    @Test
    @Disabled
    fun `distribute corpus`() {
        val (cliArgs, config, engine) = initialize("../dto/src/test/resources/config.kts -d")
        engine.use {
            it.execute(cliArgs)
        }

    }

    //        @Test
    @Disabled
    fun `print corpus`() {
        val (cliArgs, config, engine) = initialize("../dto/src/test/resources/config.kts --print")
        engine.use {
            it.execute(cliArgs)
        }
    }

    //        @Test
    @Disabled
    fun `remove files`() {
        val (cliArgs, config, engine) = initialize("../dto/src/test/resources/config.kts --remove")
        engine.use {
            it.execute(cliArgs)
        }
    }

    @Test
    fun `start indexing`() {
        val (cliArgs, config, engine) = initialize("../dto/src/test/resources/config.kts -p")
        engine.use {
            it.execute(cliArgs)
        }
    }
}