package cz.vutbr.fit.knot.enticing.management

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.concrete.*
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

class ManagementEngine(val configuration: EnticingConfiguration, val logService: MeasuringLogService) : AutoCloseable {

    private val pool = Executors.newFixedThreadPool(24)

    private val scope = CoroutineScope(pool.asCoroutineDispatcher())

    private val logger = logService.logger { }

    private val executor = ShellCommandExecutor(logService, scope)

    fun execute(args: ManagementCliArguments) {
        if (args.build)
            executeCommand(BuildProjectCommand)
        if (args.removeFiles) args.corpuses.executeAll { RemoveDistributedFiles(it) }
        if (args.distribute) args.corpuses.executeAll { DistributeCorpus(it) }
        if (args.printFiles) args.corpuses.executeAll { ShowDistributedFiles(it) }
        if (args.startIndexing) args.corpuses.executeAll { StartIndexingCommand(it) }
//        if (args.indexServers) {
//            args.corpuses.executeAll { KillIndexServers(it) }
//            if (args.startComponents) args.corpuses.executeAll { StartIndexServers(it) }
//        }
//        if (args.webserver) {
//            executeCommand(KillWebserver)
//            if (args.startComponents) executeCommand(StartWebserver)
//        }
    }


    fun executeCommand(command: ManagementCommand<*>) {
        runBlocking(scope.coroutineContext) {
            logger.measure("command $command") {
                command.execute(configuration, executor, logService)
            }
        }
    }

    private fun Collection<String>.executeAll(factory: (String) -> ManagementCommand<*>) {
        for (name in this) executeCommand(factory(name))
    }

    override fun close() {
        logger.measure("shutting down the thread pool") {
            scope.cancel()
            pool.shutdown()
        }
    }
}