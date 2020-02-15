package cz.vutbr.fit.knot.enticing.management.command

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import kotlinx.coroutines.CoroutineScope


/**
 * High-level command for the management engine
 */
abstract class ManagementCommand {

    protected lateinit var shellExecutor: ShellCommandExecutor
    protected lateinit var logger: MeasuringLogService
    protected lateinit var enticingConfiguration: EnticingConfiguration
    protected lateinit var username: String

    /**
     * Initialize the command
     * @throws IllegalArgumentException if the command cannot be executed because the configuration is not valid
     */
    internal open fun init(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) {
        shellExecutor = executor
        logger = logService.logger { }
        enticingConfiguration = configuration
        username = configuration.authentication.username
    }

    /**
     * Executes the command in given coroutine scope
     */
    internal abstract suspend fun execute(scope: CoroutineScope)
}