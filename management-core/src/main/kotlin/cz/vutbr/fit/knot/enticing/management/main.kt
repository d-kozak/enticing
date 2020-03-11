package cz.vutbr.fit.knot.enticing.management

import cz.vutbr.fit.knot.enticing.log.loggerFactoryFor
import cz.vutbr.fit.knot.enticing.log.measure

fun main(args: Array<String>) {
    runManagementCli(args)
}

fun runManagementCli(args: Array<String>) {
    println("Management-cli: starting with args ${args.contentToString()}")
    val args = parseCliArgs(args).validateOrFail()
    val configuration = args.configuration
    val loggerFactory = configuration.loggingConfiguration.loggerFactoryFor("management-cli")
    val logger = loggerFactory.namedLogger("Management-cli")
    val engine = ManagementEngine(configuration, loggerFactory)
    logger.measure("executeCliApp", args.toString()) {
        engine.use {
            engine.execute(args)
        }
    }
}