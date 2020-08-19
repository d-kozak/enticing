package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.recursiveRemove
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

open class RemoveDistributedFilesCommand(
        val corpus: CorpusConfiguration
) : ManagementCommand() {

    override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) = coroutineScope<Unit> {
        corpus.indexServers.map { server ->
            launch {
                executor.recursiveRemove(server.address, server.collectionsDir
                        ?: server.corpus.collectionsDir)
            }
        }
    }
}