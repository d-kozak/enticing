package cz.vutbr.fit.knot.enticing.management

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.measure
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.concrete.*
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

class ManagementEngine(val configuration: EnticingConfiguration, val loggerFactory: LoggerFactory) : AutoCloseable {

    private val pool = Executors.newFixedThreadPool(32)

    private val scope = CoroutineScope(pool.asCoroutineDispatcher())

    private val logger = loggerFactory.logger { }

    private val executor = ShellCommandExecutor(loggerFactory, scope)

    fun execute(args: ManagementCliArguments) = with(args) {
        if (localBuild)
            executeCommand(LocalBuildCommand)
        if (remoteBuild)
            executeCommand(RemoteBuildCommand)
        if (copyJars)
            executeCommand(CopyJarsCommand)
        if (removeFiles) corpuses.executeAll { RemoveDistributedFilesCommand(it) }
        if (distribute) corpuses.executeAll { DistributeCorpusCommand(it) }
        if (printFiles) corpuses.executeAll { ShowDistributedFiles(it) }
        if (startIndexing) corpuses.executeAll { StartIndexingCommand(it) }
        if (management) {
            if (kill) executeCommand(KillManagementServiceCommand)
            else executeCommand(StartManagementServiceCommand)
        }
        if (webserver) {
            if (kill) executeCommand(KillWebserverCommand)
            else executeCommand(StartWebserverCommand)
        }
        if (indexServers) {
            if (kill)
                corpuses.executeAll { KillIndexServersCommand(it) }
            else corpuses.executeAll { StartIndexServersCommand(it) }
        }
    }

    fun executeCommand(command: ManagementCommand<*>) {
        runBlocking(scope.coroutineContext) {
            logger.measure("ExecuteCommand", command.toString()) {
                command.execute(configuration, executor, loggerFactory)
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