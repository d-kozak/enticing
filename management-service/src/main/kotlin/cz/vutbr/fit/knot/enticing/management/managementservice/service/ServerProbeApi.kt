package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.AddServerRequest
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.probeServer
import cz.vutbr.fit.knot.enticing.mx.ServerProbe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class ServerProbeApi(
        private val configuration: EnticingConfiguration,
        loggerFactory: LoggerFactory
) : AutoCloseable {


    private val shellExecutor = ShellCommandExecutor(configuration, CoroutineScope(Dispatchers.IO), loggerFactory)

    fun request(request: AddServerRequest): ServerProbe.Info = runBlocking {
        shellExecutor.probeServer(request.url, configuration.deploymentConfiguration.repository, configuration.deploymentConfiguration.buildId)
    }

    override fun close() {
        shellExecutor.close()
    }
}