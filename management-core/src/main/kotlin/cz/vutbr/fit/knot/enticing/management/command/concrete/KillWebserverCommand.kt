package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.killWebserver
import kotlinx.coroutines.CoroutineScope

open class KillWebserverCommand(
        val address: String
) : ManagementCommand() {

    override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
        executor.killWebserver(address)
    }
}