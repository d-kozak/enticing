package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import cz.vutbr.fit.knot.enticing.dto.ComponentAddress
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.concrete.LocalBuildCommand
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ComponentService
import cz.vutbr.fit.knot.enticing.management.managementservice.service.CorpusService
import cz.vutbr.fit.knot.enticing.management.shell.*
import kotlinx.coroutines.CoroutineScope

data class CommandRequest(
        val type: CommandType,
        val arguments: String
)

enum class CommandState {
    ENQUED,
    RUNNING,
    FINISHED,
    FAILED
}

typealias CommandFactory = (String, EnticingConfiguration, CorpusService, ComponentService, String) -> ManagementCommand

enum class CommandType(private val factory: CommandFactory) {
    BUILD(localBuildCommandFactory),
    START_INDEX_SERVER(startIndexServerCommandFactory),
    KILL_INDEX_SERVER(killIndexServerCommandFactory),
    START_WEBSERVER(startWebserverCommandFactory),
    KILL_WEBSERVER(killWebserverCommandFactory),
    START_MANAGEMENT_SERVER(startManagementServerCommandFactory),
    KILL_MANAGEMENT_SERVER(killManagementServerCommandFactory);

    fun init(id: String, configuration: EnticingConfiguration, corpusService: CorpusService, componentService: ComponentService, args: String): ManagementCommand = factory(id, configuration, corpusService, componentService, args)
}


private val localBuildCommandFactory: CommandFactory = { _, configuration, _, _, args ->
    LocalBuildCommand(args, configuration.localHome)
}

private val startIndexServerCommandFactory: CommandFactory = { _, configuration, _, _, args ->
    object : ManagementCommand() {
        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            val address = ComponentAddress.parse(args)
            executor.startIndexServer(address.url, configuration.deploymentConfiguration.repository, configuration.deploymentConfiguration.configurationScript, address.port)
        }
    }
}

private val killIndexServerCommandFactory: CommandFactory = { _, _, _, _, args ->
    object : ManagementCommand() {
        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            val address = ComponentAddress.parse(args)
            executor.killIndexServer(address.url)
        }
    }
}

private val startWebserverCommandFactory: CommandFactory = { _, configuration, _, _, args ->
    object : ManagementCommand() {
        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            val address = ComponentAddress.parse(args)
            executor.startWebserver(address.url, configuration.deploymentConfiguration.repository, configuration.deploymentConfiguration.configurationScript, address.port)
        }
    }
}

private val killWebserverCommandFactory: CommandFactory = { _, _, _, _, args ->
    object : ManagementCommand() {
        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            val address = ComponentAddress.parse(args)
            executor.killWebserver(address.url)
        }
    }
}

private val startManagementServerCommandFactory: CommandFactory = { _, configuration, _, _, args ->
    object : ManagementCommand() {
        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            val address = ComponentAddress.parse(args)
            executor.startManagementService(address.url, configuration.deploymentConfiguration.repository, configuration.deploymentConfiguration.configurationScript, address.port)
        }
    }
}

private val killManagementServerCommandFactory: CommandFactory = { _, _, _, _, args ->
    object : ManagementCommand() {
        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            val address = ComponentAddress.parse(args)
            executor.killManagementService(address.url)
        }
    }
}