package cz.vutbr.fit.knot.enticing.management

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.measure
import cz.vutbr.fit.knot.enticing.management.command.NewManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel


class ManagementEngine(val configuration: EnticingConfiguration, private val scope: CoroutineScope, val loggerFactory: LoggerFactory) : AutoCloseable {

    private val logger = loggerFactory.logger { }

    private val executor = ShellCommandExecutor(configuration, scope, loggerFactory)

    suspend fun executeCommand(command: NewManagementCommand) {
        command.beforeStart()
        try {
            logger.measure("ExecuteCommand", command.toString()) {
                command.execute(scope, executor)
            }
            command.onFinish()
        } catch (ex: Exception) {
            command.onFail(ex)
        }
    }

    suspend fun Collection<String>.executeAll(factory: (String) -> NewManagementCommand) {
        for (name in this) executeCommand(factory(name))
    }

    override fun close() {
        logger.measure("shutting down the thread pool") {
            scope.cancel()
        }
    }
}