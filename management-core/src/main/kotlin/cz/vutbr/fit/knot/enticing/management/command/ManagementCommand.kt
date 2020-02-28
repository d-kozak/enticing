package cz.vutbr.fit.knot.enticing.management.command

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor


/**
 * High-level command for the management engine
 */
abstract class ManagementCommand<Ctx : ManagementCommandContext> {

    /**
     * Creates a context containing all neccessary objects to execute the command
     * @throws IllegalArgumentException if the command cannot be executed because the configuration is not valid
     */
    protected abstract fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory): Ctx

    /**
     * Executes the command in given coroutine scope
     */
    internal suspend fun execute(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) {
        buildContext(configuration, executor, loggerFactory).execute()
    }

}


/***
 * Internal representation of the command, extended with additional information needed to execute it
 */
abstract class ManagementCommandContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) {
    protected val shellExecutor = executor
    protected val enticingConfiguration = configuration
    protected val webserverConfiguration = configuration.webserverConfiguration
    protected val managementConfiguration = configuration.managementServiceConfiguration
    protected val deploymentConfiguration = configuration.deploymentConfiguration
    protected val username = configuration.authentication.username

    internal abstract suspend fun execute()
}

abstract class CorpusSpecificCommandContext(protected val corpusName: String, configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) : ManagementCommandContext(configuration, executor, loggerFactory) {
    protected val corpusConfiguration = configuration.corpuses[corpusName]
            ?: throw IllegalArgumentException("Corpus $corpusName not found in ${configuration.corpuses.keys}")

    protected val corpusSourceConfiguration = corpusConfiguration.corpusSourceConfiguration
}