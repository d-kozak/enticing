package cz.vutbr.fit.knot.enticing.testing.performance

import cz.vutbr.fit.knot.enticing.log.StdoutLogService
import cz.vutbr.fit.knot.enticing.log.measuring
import cz.vutbr.fit.knot.enticing.management.ManagementEngine
import cz.vutbr.fit.knot.enticing.management.parseCliArgs

fun runManagementCli(args: String) {
    val cliArgs = parseCliArgs(args.split("""\s+""".toRegex()).toTypedArray()).validateOrFail()
    val config = cliArgs.configuration
    val logger = StdoutLogService(config.loggingConfiguration).measuring()
    val engine = ManagementEngine(config, logger)
    engine.use {
        engine.execute(cliArgs)
    }
}


