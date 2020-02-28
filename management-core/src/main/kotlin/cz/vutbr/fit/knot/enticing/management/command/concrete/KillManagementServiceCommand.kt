package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommandContext
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.killManagementService

object KillManagementServiceCommand : ManagementCommand<KillManagementServiceCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory): KillManagementServiceCommandContext = KillManagementServiceCommandContext(configuration, executor, loggerFactory)
}

class KillManagementServiceCommandContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) : ManagementCommandContext(configuration, executor, loggerFactory) {

    override suspend fun execute() {
        shellExecutor.killManagementService(username, managementConfiguration.address)
    }
}