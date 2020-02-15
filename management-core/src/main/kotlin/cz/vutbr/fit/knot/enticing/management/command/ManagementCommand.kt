package cz.vutbr.fit.knot.enticing.management.command

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking


/**
 * High-level command for the management engine
 */
abstract class ManagementCommand<Ctx : ManagementCommandContext> {

    /**
     * Creates a context containing all neccessary objects to execute the command
     * @throws IllegalArgumentException if the command cannot be executed because the configuration is not valid
     */
    protected abstract fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService): Ctx

    /**
     * Executes the command in given coroutine scope
     */
    internal fun execute(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService, scope: CoroutineScope) {
        runBlocking(scope.coroutineContext) {
            buildContext(configuration, executor, logService).execute(scope)
        }
    }

}


/***
 * Internal representation of the command, extended with additional information needed to execute it
 */
abstract class ManagementCommandContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) {
    protected val shellExecutor = executor
    protected val enticingConfiguration = configuration
    protected val username = configuration.authentication.username

    internal abstract suspend fun execute(scope: CoroutineScope)
}

abstract class CorpusSpecificCommandContext(protected val corpusName: String, configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) : ManagementCommandContext(configuration, executor, logService) {
    protected val corpusConfiguration = configuration.corpuses[corpusName]
            ?: throw IllegalArgumentException("Corpus $corpusName not found in ${configuration.corpuses.keys}")

    protected val corpusSourceConfiguration = corpusConfiguration.corpusSourceConfiguration
}