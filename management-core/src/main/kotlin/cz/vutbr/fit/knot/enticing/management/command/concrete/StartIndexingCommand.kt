package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.CorpusSpecificCommandContext
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.preprocessCollections
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

data class StartIndexingCommand(val corpusName: String) : ManagementCommand<StartIndexingCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) = StartIndexingCommandContext(corpusName, configuration, executor, loggerFactory)
}

class StartIndexingCommandContext(corpusName: String, configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) : CorpusSpecificCommandContext(corpusName, configuration, executor, loggerFactory) {
    private val logger = loggerFactory.logger { }

    private val deployment = configuration.deploymentConfiguration

    override suspend fun execute() = coroutineScope {
        val outputs = corpusConfiguration.indexServers.map { server ->
            async {
                shellExecutor.preprocessCollections(username, server.address, enticingConfiguration.deploymentConfiguration.repository, enticingConfiguration.deploymentConfiguration.configurationScript)
            }
        }.awaitAll()

        for ((i, output) in outputs.withIndex()) {
            logger.info("server ${corpusConfiguration.indexServers[i].address}: $output")
        }
    }
}