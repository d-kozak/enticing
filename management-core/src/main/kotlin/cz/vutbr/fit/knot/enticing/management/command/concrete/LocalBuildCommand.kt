package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommandContext
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.localBuild

object LocalBuildCommand : ManagementCommand<LocalBuildCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService): LocalBuildCommandContext = LocalBuildCommandContext(configuration, executor, logService)
}


class LocalBuildCommandContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) : ManagementCommandContext(configuration, executor, logService) {
    private val logger = logService.logger { }

    override suspend fun execute() {
        shellExecutor.localBuild(enticingConfiguration.localHome)
    }
}