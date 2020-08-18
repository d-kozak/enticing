package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.management.command.NewManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.killIndexServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class KillIndexServersCommand(
        val corpus: CorpusConfiguration
) : NewManagementCommand() {
    override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) = coroutineScope<Unit> {
        corpus.indexServers.map { server ->
            launch {
                executor.killIndexServer(server.address)
            }
        }
    }
}