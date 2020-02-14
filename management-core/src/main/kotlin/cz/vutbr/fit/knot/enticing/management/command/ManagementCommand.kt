package cz.vutbr.fit.knot.enticing.management.command

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor


/**
 * High-level command for the management engine
 */
abstract class ManagementCommand {

    internal open fun validateOrFail(configuration: EnticingConfiguration) {

    }

    internal abstract fun execute(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService)
}