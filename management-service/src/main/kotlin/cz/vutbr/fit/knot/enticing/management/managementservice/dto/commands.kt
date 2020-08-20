package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import cz.vutbr.fit.knot.enticing.dto.BasicComponentInfo
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.concrete.LocalBuildCommand
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ComponentService
import cz.vutbr.fit.knot.enticing.management.managementservice.service.CorpusService
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.killComponent
import cz.vutbr.fit.knot.enticing.management.shell.startComponent
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
    ADD_COMPONENT(addComponentCommandFactory),
    START_COMPONENT(addComponentCommandFactory),
    KILL_COMPONENT(killComponentCommandFactory),
    REMOVE_COMPONENT(removeComponentCommandFactory);

    fun init(id: String, configuration: EnticingConfiguration, corpusService: CorpusService, componentService: ComponentService, args: String): ManagementCommand = factory(id, configuration, corpusService, componentService, args)
}


private val localBuildCommandFactory: CommandFactory = { _, configuration, _, _, args ->
    LocalBuildCommand(args, configuration.localHome)
}

private val addComponentCommandFactory: CommandFactory = { _, configuration, _, _, args ->
    val component = args.toDto<BasicComponentInfo>()
    object : ManagementCommand() {
        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            executor.startComponent(component, configuration.deploymentConfiguration)
        }
    }
}

private val killComponentCommandFactory: CommandFactory = { _, _, _, _, args ->
    val component = args.toDto<BasicComponentInfo>()
    object : ManagementCommand() {
        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            executor.killComponent(component)
        }
    }
}

private val removeComponentCommandFactory: CommandFactory = { _, _, _, componentService, args ->
    val component = args.toDto<BasicComponentInfo>()
    object : ManagementCommand() {
        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            executor.killComponent(component)
        }

        override fun onSuccess() {
            componentService.removeComponent(component.id)
        }
    }
}