package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.DeploymentConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.NewManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.copyJars
import kotlinx.coroutines.CoroutineScope


open class CopyJarsCommand(
        val localHome: String,
        val deploymentConfiguration: DeploymentConfiguration,
        loggerFactory: LoggerFactory
) : NewManagementCommand() {

    private val logger = loggerFactory.logger { }

    override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
        logger.info("Copying jars...")
        executor.copyJars(deploymentConfiguration.server, localHome, deploymentConfiguration.repository)
    }
}