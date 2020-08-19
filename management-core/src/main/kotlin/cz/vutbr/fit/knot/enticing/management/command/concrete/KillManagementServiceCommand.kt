package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.killManagementService
import kotlinx.coroutines.CoroutineScope

open class KillManagementServiceCommand(
        val address: String
) : ManagementCommand() {

    override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
        executor.killManagementService(address)
    }
}