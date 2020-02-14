package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor

data class KillIndexing(val corpusName: String? = null, val indexServers: List<String>? = null) : ManagementCommand() {
    override fun execute(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}