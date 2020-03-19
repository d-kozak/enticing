package cz.vutbr.fit.knot.enticing.management

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand

class ManagementService(configuration: EnticingConfiguration, loggerFactory: LoggerFactory) : AutoCloseable {

    private val logger = loggerFactory.logger { }

    private val engine = ManagementEngine(configuration, loggerFactory)

    fun executeCommand(command: ManagementCommand<*>) {
        engine.executeCommand(command)
    }

    override fun close() {

        engine.close()
    }
}