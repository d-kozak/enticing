package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor

object StartWebserver : ManagementCommand() {
    override fun execute(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}