package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.CorpusSpecificCommandContext
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.recursiveRemove
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class RemoveDistributedFiles(val corpusName: String) : ManagementCommand<RemoveDistributedFilesContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) = RemoveDistributedFilesContext(corpusName, configuration, executor, logService)
}

class RemoveDistributedFilesContext(corpusName: String, configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) : CorpusSpecificCommandContext(corpusName, configuration, executor, logService) {

    private val logger = logService.logger { }

    override suspend fun execute(scope: CoroutineScope) {
        withContext(scope.coroutineContext) {
            corpusConfiguration.indexServers.map { server ->
                launch {
                    shellExecutor.recursiveRemove(username, server.address!!, server.collectionsDir
                            ?: server.corpus.collectionsDir)
                }
            }

        }
    }
}