package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import kotlinx.coroutines.CoroutineScope


open class KillIndexingCommand(
        val corpus: CorpusConfiguration
) : ManagementCommand() {

    override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
        TODO("cannot stop indexing now")
    }
}
