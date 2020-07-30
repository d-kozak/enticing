package cz.vutbr.fit.knot.enticing.index.client

import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import kotlin.time.ExperimentalTime


@ExperimentalTime
fun runConsoleClient(args: Array<String>) {
    ConsoleClient(parseCliArgs(args), SimpleStdoutLoggerFactory)
            .use { it.exec() }
}


@ExperimentalTime
fun main(args: Array<String>) = runConsoleClient(args)