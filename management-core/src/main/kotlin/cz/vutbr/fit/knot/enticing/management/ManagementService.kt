package cz.vutbr.fit.knot.enticing.management

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import java.util.concurrent.Executors

class ManagementService(configuration: EnticingConfiguration, loggerFactory: LoggerFactory) : AutoCloseable {

    private val logger = loggerFactory.logger { }

    private val pool = Executors.newFixedThreadPool(4)

    private val scope = CoroutineScope(pool.asCoroutineDispatcher())

    private val engine = ManagementEngine(configuration, scope, loggerFactory)

    suspend fun executeCommand(command: ManagementCommand) {
        engine.executeCommand(command)
    }

    override fun close() {
        engine.close()
        scope.cancel()
        pool.shutdown()
    }
}