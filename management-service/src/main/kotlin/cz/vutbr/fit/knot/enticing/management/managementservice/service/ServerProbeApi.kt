package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.AddServerRequest
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.probeServer
import cz.vutbr.fit.knot.enticing.mx.ServerProbe
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class ServerProbeApi(
        private val configuration: EnticingConfiguration,
        private val loggerFactory: LoggerFactory
) {

    fun request(request: AddServerRequest): ServerProbe.Info = runBlocking {
        ShellCommandExecutor(loggerFactory, this).use {
            it.probeServer(configuration.authentication.username, request.url, configuration.deploymentConfiguration.repository)
        }
    }
}