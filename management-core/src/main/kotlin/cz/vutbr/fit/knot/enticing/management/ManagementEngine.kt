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

    private val pool = Executors.newFixedThreadPool(64)

    private val scope = CoroutineScope(pool.asCoroutineDispatcher())

    private val logger = logService.logger { }

    private val executor = ShellCommandExecutor(logService, scope)

    fun execute(args: ManagementCliArguments) {
        if (args.build)
            executeCommand(BuildProjectCommand)
        if (args.removeFiles) args.corpuses.executeAll { RemoveDistributedFilesCommand(it) }
        if (args.distribute) args.corpuses.executeAll { DistributeCorpusCommand(it) }
        if (args.printFiles) args.corpuses.executeAll { ShowDistributedFiles(it) }
        if (args.startIndexing) args.corpuses.executeAll { StartIndexingCommand(it) }
        if (args.indexServers) {
            if (args.kill)
                args.corpuses.executeAll { KillIndexServersCommand(it) }
            else args.corpuses.executeAll { StartIndexServersCommand(it) }
        }
        if (args.webserver) {
            if (args.kill) executeCommand(KillWebserverCommand)
            else executeCommand(StartWebserverCommand)
        }
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