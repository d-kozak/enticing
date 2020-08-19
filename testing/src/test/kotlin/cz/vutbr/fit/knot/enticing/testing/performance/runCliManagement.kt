package cz.vutbr.fit.knot.enticing.testing.performance

import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.management.ManagementEngine
import cz.vutbr.fit.knot.enticing.management.parseCliArgs
import cz.vutbr.fit.knot.enticing.management.runCliApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

fun runManagementCli(args: String) {
    val cliArgs = parseCliArgs(args.split("""\s+""".toRegex()).toTypedArray()).validateOrFail()
    val config = cliArgs.configuration
    val engine = ManagementEngine(config, CoroutineScope(Dispatchers.IO), SimpleStdoutLoggerFactory)
    engine.use {
        runBlocking {
            engine.runCliApp(cliArgs, config, SimpleStdoutLoggerFactory)
        }
    }
}


