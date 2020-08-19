package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.DeploymentConfiguration
import cz.vutbr.fit.knot.enticing.management.command.NewManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.startIndexServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

open class StartIndexServersCommand(
        val corpus: CorpusConfiguration,
        val deployment: DeploymentConfiguration
) : NewManagementCommand() {
    override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) = coroutineScope<Unit> {
        corpus.indexServers.map { server ->
            launch {
                executor.startIndexServer(server.address, deployment.repository, deployment.configurationScript, server.port)
            }
        }
    }
}