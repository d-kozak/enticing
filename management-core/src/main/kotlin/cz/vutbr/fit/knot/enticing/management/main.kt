package cz.vutbr.fit.knot.enticing.management

import cz.vutbr.fit.knot.enticing.log.loggerFactoryFor
import cz.vutbr.fit.knot.enticing.log.measure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

fun main(args: Array<String>) {
    runManagementCli(args)
}

fun runManagementCli(args: Array<String>) {
    println("Management-cli: starting with args ${args.contentToString()}")
    val args = parseCliArgs(args).validateOrFail()
    val configuration = args.configuration
    configuration.deploymentConfiguration.buildId = args.buildId
    val loggerFactory = configuration.loggingConfiguration.loggerFactoryFor("management-cli")
    val logger = loggerFactory.namedLogger("Management-cli")
    val pool = Executors.newFixedThreadPool(4)
    val dispatcher = pool.asCoroutineDispatcher()
    val scope = CoroutineScope(dispatcher)
    val engine = ManagementEngine(configuration, scope, loggerFactory)
    dispatcher.use {
        engine.use {
            logger.measure("executeCliApp", args.toString()) {
                runBlocking(scope.coroutineContext) {
                    engine.runCliApp(args, configuration, loggerFactory)
                }
            }
        }
    }
}