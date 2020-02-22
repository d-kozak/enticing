package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommandContext
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.startWebserver

object StartWebserverCommand : ManagementCommand<StartWebserverCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService): StartWebserverCommandContext = StartWebserverCommandContext(configuration, executor, logService)
}


class StartWebserverCommandContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) : ManagementCommandContext(configuration, executor, logService) {

    override suspend fun execute() {
        shellExecutor.startWebserver(username, enticingConfiguration.webserverConfiguration.address, enticingConfiguration.deploymentConfiguration.repository, enticingConfiguration.deploymentConfiguration.configurationScript)
    }
}