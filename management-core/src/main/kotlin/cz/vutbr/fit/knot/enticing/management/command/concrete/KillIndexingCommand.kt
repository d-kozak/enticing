package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.management.command.CorpusSpecificCommandContext
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor

data class KillIndexingCommand(val corpusName: String) : ManagementCommand<KillIndexingCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService): KillIndexingCommandContext = KillIndexingCommandContext(corpusName, configuration, executor, logService)
}

class KillIndexingCommandContext(corpusName: String, configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) : CorpusSpecificCommandContext(corpusName, configuration, executor, logService) {

    override suspend fun execute() {
        TODO("cannot stop indexing now")
    }
}
