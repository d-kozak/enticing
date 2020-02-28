package cz.vutbr.fit.knot.enticing.testing.performance

import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.management.ManagementEngine
import cz.vutbr.fit.knot.enticing.management.parseCliArgs

fun runManagementCli(args: String) {
    val cliArgs = parseCliArgs(args.split("""\s+""".toRegex()).toTypedArray()).validateOrFail()
    val config = cliArgs.configuration
    val engine = ManagementEngine(config, SimpleStdoutLoggerFactory)
    engine.use {
        engine.execute(cliArgs)
    }
}


