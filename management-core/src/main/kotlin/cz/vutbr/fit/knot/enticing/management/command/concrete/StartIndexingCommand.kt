package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.DeploymentConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.NewManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.preprocessCollections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class StartIndexingCommand(
        val corpus: CorpusConfiguration,
        val deployment: DeploymentConfiguration,
        loggerFactory: LoggerFactory
) : NewManagementCommand() {
    private val logger = loggerFactory.logger { }


    override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) = coroutineScope {
        val outputs = corpus.indexServers.map { server ->
            async {
                executor.preprocessCollections(server.address, deployment.repository, deployment.configurationScript)
            }
        }.awaitAll()

        for ((i, output) in outputs.withIndex()) {
            logger.info("server ${corpus.indexServers[i].address}: $output")
        }
    }
}