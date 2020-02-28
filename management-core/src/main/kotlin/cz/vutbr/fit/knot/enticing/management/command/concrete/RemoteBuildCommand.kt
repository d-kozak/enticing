package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger

import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommandContext
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.pullAndBuild

object RemoteBuildCommand : ManagementCommand<RemoteBuildCommandContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory): RemoteBuildCommandContext = RemoteBuildCommandContext(configuration, executor, loggerFactory)
}

class RemoteBuildCommandContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) : ManagementCommandContext(configuration, executor, loggerFactory) {
    private val logger = loggerFactory.logger { }

    private val deployment = configuration.deploymentConfiguration

    override suspend fun execute() {
        val (server, repository) = deployment
        shellExecutor.pullAndBuild(username, server, repository)
    }
}