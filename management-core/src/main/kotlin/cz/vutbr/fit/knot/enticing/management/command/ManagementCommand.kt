package cz.vutbr.fit.knot.enticing.management.command

import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import kotlinx.coroutines.CoroutineScope

abstract class ManagementCommand {
    open fun beforeStart() {}
    open fun onSuccess() {}
    abstract suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor)
    open fun onFail(ex: Exception) {}
}

