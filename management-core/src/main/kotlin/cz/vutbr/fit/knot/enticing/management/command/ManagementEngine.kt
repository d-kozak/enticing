package cz.vutbr.fit.knot.enticing.management.command

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger

class ManagementEngine(val configuration: EnticingConfiguration, logService: MeasuringLogService) {

    private val logger = logService.logger { }

    fun executeCommand(command: ManagementCommand) {
        if (!command.canExecute()) {
            logger.error("command $command cannot be executed")
            return
        }
        logger.measure("command $command") {
            command.execute()
        }
    }
}