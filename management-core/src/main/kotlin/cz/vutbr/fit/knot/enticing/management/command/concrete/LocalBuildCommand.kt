package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.localBuild
import kotlinx.coroutines.CoroutineScope

open class LocalBuildCommand(val buildId: String, val localHome: String) : ManagementCommand() {
    override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
        executor.localBuild(buildId, localHome)
    }
}