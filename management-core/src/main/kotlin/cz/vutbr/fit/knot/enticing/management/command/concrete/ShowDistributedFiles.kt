package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import kotlinx.coroutines.CoroutineScope

data class ShowDistributedFiles(val corpusName: String? = null, val indexServers: List<String>? = null) : ManagementCommand() {

//    private var indexServers = mutableListOf<IndexServerConfiguration>()

    override fun init(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) {
        super.init(configuration, executor, logService)
    }

    override suspend fun execute(scope: CoroutineScope) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}