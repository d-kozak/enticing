package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.management.command.CorpusSpecificCommandContext
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor

data class KillIndexingCommand(val corpusName: String) : ManagementCommand<KillIndexingCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory): KillIndexingCommandContext = KillIndexingCommandContext(corpusName, configuration, executor, loggerFactory)
}

class KillIndexingCommandContext(corpusName: String, configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) : CorpusSpecificCommandContext(corpusName, configuration, executor, loggerFactory) {

    override suspend fun execute() {
        TODO("cannot stop indexing now")
    }
}
