package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import kotlinx.coroutines.CoroutineScope

data class KillIndexing(val corpusName: String? = null, val indexServers: List<String>? = null) : ManagementCommand() {
    override suspend fun execute(scope: CoroutineScope) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}