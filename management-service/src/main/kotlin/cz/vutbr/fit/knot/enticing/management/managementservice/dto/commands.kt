package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ManagementCommand.ServerGroupCommand.*
import cz.vutbr.fit.knot.enticing.management.shell.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.validation.constraints.NotEmpty

data class CommandRequest(
        val type: CommandType,
        @field:NotEmpty
        val arguments: String
)

enum class CommandState {
    ENQUED,
    RUNNING,
    FINISHED,
    FAILED
}

enum class CommandType(private val factory: (EnticingConfiguration, String) -> ManagementCommand) {
    START_INDEX_SERVER(::StartIndexServerCommand),
    KILL_INDEX_SERVER(::KillIndexServerCommand),
    START_WEBSERVER(::StartWebserver),
    KILL_WEBSERVER(::KillWebserver),
    START_MANAGEMENT_SERVER(::StartManagementServer),
    KILL_MANAGEMENT_SERVER(::KillManagementServer);

    fun init(configuration: EnticingConfiguration, args: String): ManagementCommand = factory(configuration, args)
}


sealed class ManagementCommand(val configuration: EnticingConfiguration) {

    val username = configuration.authentication.username
    val enticingHome = configuration.deploymentConfiguration.repository
    val configFile = configuration.deploymentConfiguration.configurationScript

    abstract suspend fun execute(scope: CoroutineScope, shellCommandExecutor: ShellCommandExecutor)

    sealed class ServerGroupCommand(configuration: EnticingConfiguration, val addresses: List<String>) : ManagementCommand(configuration) {

        constructor(configuration: EnticingConfiguration, args: String) : this(configuration, args.split(","))

        init {
            // todo verify valid addresses?
        }

        override suspend fun execute(scope: CoroutineScope, shellCommandExecutor: ShellCommandExecutor) {
            addresses.map { it.split(":") }
                    .map { (ip, port) -> scope.launch { executeForServer(shellCommandExecutor, ip, port.toInt()) } }
                    .joinAll()

        }

        abstract suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int)

        class StartIndexServerCommand(configuration: EnticingConfiguration, args: String) : ManagementCommand.ServerGroupCommand(configuration, args) {

            override suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int) {
                shellCommandExecutor.startIndexServer(username, ip, enticingHome, configFile, port)
            }
        }

        class KillIndexServerCommand(configuration: EnticingConfiguration, args: String) : ManagementCommand.ServerGroupCommand(configuration, args) {
            override suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int) {
                shellCommandExecutor.killIndexServer(username, ip)
            }
        }

        class StartWebserver(configuration: EnticingConfiguration, args: String) : ManagementCommand.ServerGroupCommand(configuration, args) {
            override suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int) {
                shellCommandExecutor.startWebserver(username, ip, enticingHome, configFile, port)
            }
        }

        class KillWebserver(configuration: EnticingConfiguration, args: String) : ManagementCommand.ServerGroupCommand(configuration, args) {
            override suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int) {
                shellCommandExecutor.killWebserver(username, ip)
            }
        }

        class StartManagementServer(configuration: EnticingConfiguration, args: String) : ManagementCommand.ServerGroupCommand(configuration, args) {
            override suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int) {
                shellCommandExecutor.startManagementService(username, ip, enticingHome, configFile, port)
            }
        }

        class KillManagementServer(configuration: EnticingConfiguration, args: String) : ManagementCommand.ServerGroupCommand(configuration, args) {
            override suspend fun executeForServer(shellCommandExecutor: ShellCommandExecutor, ip: String, port: Int) {
                shellCommandExecutor.killManagementService(username, ip)
            }
        }
    }
}


