package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommandContext
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.copyJars

object CopyJarsCommand : ManagementCommand<CopyJarsCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory): CopyJarsCommandContext = CopyJarsCommandContext(configuration, executor, loggerFactory)
}

class CopyJarsCommandContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) : ManagementCommandContext(configuration, executor, loggerFactory) {

    private val logger = loggerFactory.logger { }

    override suspend fun execute() {
        logger.info("Copying jars...")
        shellExecutor.copyJars(username, deploymentConfiguration.server, enticingConfiguration.localHome, deploymentConfiguration.repository)
    }
}