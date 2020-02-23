package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.CorpusSpecificCommandContext
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.recursiveRemove
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

data class RemoveDistributedFilesCommand(val corpusName: String) : ManagementCommand<RemoveDistributedFilesCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) = RemoveDistributedFilesCommandContext(corpusName, configuration, executor, logService)
}

class RemoveDistributedFilesCommandContext(corpusName: String, configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) : CorpusSpecificCommandContext(corpusName, configuration, executor, logService) {

    private val logger = logService.logger { }

    override suspend fun execute() = coroutineScope<Unit> {
        corpusConfiguration.indexServers.map { server ->
            launch {
                shellExecutor.recursiveRemove(username, server.address, server.collectionsDir
                        ?: server.corpus.collectionsDir)
            }
        }
    }
}