package cz.vutbr.fit.knot.enticing.management.command

import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import kotlinx.coroutines.CoroutineScope

abstract class NewManagementCommand {
    fun beforeStart() {}
    fun onFinish() {}
    abstract suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor)
    fun onFail(ex: Exception) {}
}

