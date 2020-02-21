package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommandContext
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.pullAndBuild

object BuildProjectCommand : ManagementCommand<BuildProjectCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService): BuildProjectCommandContext = BuildProjectCommandContext(configuration, executor, logService)
}

class BuildProjectCommandContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) : ManagementCommandContext(configuration, executor, logService) {
    private val logger = logService.logger { }

    private val deployment = configuration.deploymentConfiguration

    override suspend fun execute() {
        val (server, repository) = deployment
        shellExecutor.pullAndBuild(username, server, repository)
    }
}