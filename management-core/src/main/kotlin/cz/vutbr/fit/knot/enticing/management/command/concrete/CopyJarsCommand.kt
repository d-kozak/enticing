package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommandContext
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.copyJars

object CopyJarsCommand : ManagementCommand<CopyJarsCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService): CopyJarsCommandContext = CopyJarsCommandContext(configuration, executor, logService)
}

class CopyJarsCommandContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) : ManagementCommandContext(configuration, executor, logService) {
    override suspend fun execute() {
        shellExecutor.copyJars(username, deploymentConfiguration.server, enticingConfiguration.localHome, deploymentConfiguration.repository)
    }
}