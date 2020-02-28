package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.management.command.CorpusSpecificCommandContext
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.startIndexServer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

data class StartIndexServersCommand(val corpusName: String) : ManagementCommand<StartIndexServerCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory): StartIndexServerCommandContext = StartIndexServerCommandContext(corpusName, configuration, executor, loggerFactory)
}

class StartIndexServerCommandContext(corpusName: String, configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) : CorpusSpecificCommandContext(corpusName, configuration, executor, loggerFactory) {
    override suspend fun execute() = coroutineScope<Unit> {
        corpusConfiguration.indexServers.map { server ->
            launch {
                shellExecutor.startIndexServer(username, server.address, enticingConfiguration.deploymentConfiguration.repository, enticingConfiguration.deploymentConfiguration.configurationScript, server.port)
            }
        }
    }
}