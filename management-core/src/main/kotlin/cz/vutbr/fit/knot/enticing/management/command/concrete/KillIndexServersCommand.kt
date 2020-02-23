package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.management.command.CorpusSpecificCommandContext
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.killIndexServer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

data class KillIndexServersCommand(val corpusName: String) : ManagementCommand<KillIndexServerCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService): KillIndexServerCommandContext = KillIndexServerCommandContext(corpusName, configuration, executor, logService)
}

class KillIndexServerCommandContext(corpusName: String, configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) : CorpusSpecificCommandContext(corpusName, configuration, executor, logService) {
    override suspend fun execute() = coroutineScope<Unit> {
        corpusConfiguration.indexServers.map { server ->
            launch {
                shellExecutor.killIndexServer(username, server.address)
            }
        }
    }
}