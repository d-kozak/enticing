package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import cz.vutbr.fit.knot.enticing.dto.ComponentAddress
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.toComponentAddress
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ManagementCommand.ServerGroupCommand.*
import cz.vutbr.fit.knot.enticing.management.shell.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

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

enum class CommandType(private val factory: (EnticingConfiguration, String, String) -> ManagementCommand) {
    BUILD(::BuildCommand),
    START_INDEX_SERVER(::StartIndexServerCommand),
    KILL_INDEX_SERVER(::KillIndexServerCommand),
    START_WEBSERVER(::StartWebserver),
    KILL_WEBSERVER(::KillWebserver),
    START_MANAGEMENT_SERVER(::StartManagementServer),
    KILL_MANAGEMENT_SERVER(::KillManagementServer);

    fun init(configuration: EnticingConfiguration, id: String, args: String): ManagementCommand = factory(configuration, id, args)
}


sealed class ManagementCommand(val configuration: EnticingConfiguration, val commandId: String) {

    val username = configuration.authentication.username
    val enticingHome = configuration.deploymentConfiguration.repository
    val configFile = configuration.deploymentConfiguration.configurationScript

    abstract suspend fun execute(scope: CoroutineScope, shellCommandExecutor: ShellCommandExecutor)

    sealed class ServerGroupCommand(configuration: EnticingConfiguration, id: String, val addresses: List<ComponentAddress>) : ManagementCommand(configuration, id) {

        constructor(configuration: EnticingConfiguration, id: String, args: String) : this(configuration, id, args.split(",").map { it.toComponentAddress() })


        override suspend fun execute(scope: CoroutineScope, shellCommandExecutor: ShellCommandExecutor) {
            addresses.map { (ip, port) -> scope.launch { executeForServer(shellCommandExecutor, ip, port) } }
                    .joinAll()

        }

        abstract suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int)


        class BuildCommand(configuration: EnticingConfiguration, id: String, val args: String) : ManagementCommand(configuration, id) {
            override suspend fun execute(scope: CoroutineScope, shellCommandExecutor: ShellCommandExecutor) {
                shellCommandExecutor.localBuild(commandId, configuration.localHome)
            }
        }

        class StartIndexServerCommand(configuration: EnticingConfiguration, id: String, args: String) : ManagementCommand.ServerGroupCommand(configuration, id, args) {

            override suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int) {
                shellCommandExecutor.startIndexServer(username, ip, enticingHome, configFile, port)
            }
        }

        class KillIndexServerCommand(configuration: EnticingConfiguration, id: String, args: String) : ManagementCommand.ServerGroupCommand(configuration, id, args) {
            override suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int) {
                shellCommandExecutor.killIndexServer(username, ip)
            }
        }

        class StartWebserver(configuration: EnticingConfiguration, id: String, args: String) : ManagementCommand.ServerGroupCommand(configuration, id, args) {
            override suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int) {
                shellCommandExecutor.startWebserver(username, ip, enticingHome, configFile, port)
            }
        }

        class KillWebserver(configuration: EnticingConfiguration, id: String, args: String) : ManagementCommand.ServerGroupCommand(configuration, id, args) {
            override suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int) {
                shellCommandExecutor.killWebserver(username, ip)
            }
        }

        class StartManagementServer(configuration: EnticingConfiguration, id: String, args: String) : ManagementCommand.ServerGroupCommand(configuration, id, args) {
            override suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int) {
                shellCommandExecutor.startManagementService(username, ip, enticingHome, configFile, port)
            }
        }

        class KillManagementServer(configuration: EnticingConfiguration, id: String, args: String) : ManagementCommand.ServerGroupCommand(configuration, id, args) {
            override suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int) {
                shellCommandExecutor.killManagementService(username, ip)
            }
        }
    }
}


