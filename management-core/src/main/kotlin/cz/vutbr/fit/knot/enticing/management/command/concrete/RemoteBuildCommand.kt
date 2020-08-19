package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.DeploymentConfiguration
import cz.vutbr.fit.knot.enticing.management.command.NewManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.pullAndBuild
import kotlinx.coroutines.CoroutineScope

open class RemoteBuildCommand(
        val deploymentConfiguration: DeploymentConfiguration
) : NewManagementCommand() {

    override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
        val (server, repository) = deploymentConfiguration
        executor.pullAndBuild(server, repository)
    }
}