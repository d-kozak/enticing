package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommandContext
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.startManagementService

object StartManagementServiceCommand : ManagementCommand<StartManagementServiceCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService): StartManagementServiceCommandContext = StartManagementServiceCommandContext(configuration, executor, logService)
}

class StartManagementServiceCommandContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) : ManagementCommandContext(configuration, executor, logService) {

    override suspend fun execute() {
        shellExecutor.startManagementService(username, enticingConfiguration.managementServiceConfiguration.address, deploymentConfiguration.repository, deploymentConfiguration.configurationScript)
    }
}