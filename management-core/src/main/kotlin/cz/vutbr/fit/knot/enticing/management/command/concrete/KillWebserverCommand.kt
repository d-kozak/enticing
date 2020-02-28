package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommandContext
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.killWebserver

object KillWebserverCommand : ManagementCommand<KillWebserverCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory): KillWebserverCommandContext = KillWebserverCommandContext(configuration, executor, loggerFactory)
}


class KillWebserverCommandContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) : ManagementCommandContext(configuration, executor, loggerFactory) {

    override suspend fun execute() {
        shellExecutor.killWebserver(username, enticingConfiguration.webserverConfiguration.address)
    }
}