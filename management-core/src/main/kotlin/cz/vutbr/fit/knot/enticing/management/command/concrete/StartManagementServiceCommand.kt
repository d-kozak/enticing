package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommandContext
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.startManagementService

object StartManagementServiceCommand : ManagementCommand<StartManagementServiceCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory): StartManagementServiceCommandContext = StartManagementServiceCommandContext(configuration, executor, loggerFactory)
}

class StartManagementServiceCommandContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) : ManagementCommandContext(configuration, executor, loggerFactory) {

    override suspend fun execute() {
        shellExecutor.startManagementService(username, managementConfiguration.address, deploymentConfiguration.repository, deploymentConfiguration.configurationScript, managementConfiguration.port)
    }
}