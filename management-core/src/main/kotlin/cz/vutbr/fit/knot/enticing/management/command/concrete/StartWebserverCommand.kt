package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.ComponentAddress
import cz.vutbr.fit.knot.enticing.dto.config.dsl.DeploymentConfiguration
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.startWebserver
import kotlinx.coroutines.CoroutineScope


open class StartWebserverCommand(
        val address: ComponentAddress,
        val deployment: DeploymentConfiguration
) : ManagementCommand() {

    override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
        executor.startWebserver(address.url, deployment.repository, deployment.configurationScript, address.port, deployment.buildId)
    }
}