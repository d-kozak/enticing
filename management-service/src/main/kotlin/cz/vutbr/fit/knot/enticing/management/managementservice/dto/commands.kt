package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import com.fasterxml.jackson.databind.JsonNode
import cz.vutbr.fit.knot.enticing.dto.BasicComponentInfo
import cz.vutbr.fit.knot.enticing.dto.Status
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.command.concrete.LocalBuildCommand
import cz.vutbr.fit.knot.enticing.management.managementservice.service.BuildService
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ComponentService
import cz.vutbr.fit.knot.enticing.management.managementservice.service.CorpusService
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.killComponent
import cz.vutbr.fit.knot.enticing.management.shell.startComponent
import kotlinx.coroutines.CoroutineScope

data class CommandRequest(
        val type: CommandType,
        val arguments: JsonNode
)

enum class CommandState {
    ENQUED,
    RUNNING,
    FINISHED,
    FAILED
}

class CommandContext(
        val id: String,
        val configuration: EnticingConfiguration,
        val corpusService: CorpusService,
        val componentService: ComponentService,
        val buildService: BuildService,
        val args: String
)

typealias CommandFactory = CommandContext.() -> ManagementCommand

enum class CommandType(private val factory: CommandFactory) {
    BUILD(localBuildCommandFactory),
    START_COMPONENT(startComponentCommandFactory),
    KILL_COMPONENT(killComponentCommandFactory),
    REMOVE_COMPONENT(removeComponentCommandFactory),
    START_CORPUS(startCorpusCommandFactory),
    KILL_CORPUS(killCorpusCommandFactory);

    fun init(id: String, configuration: EnticingConfiguration, corpusService: CorpusService, componentService: ComponentService, buildService: BuildService, args: String): ManagementCommand = CommandContext(id, configuration, corpusService, componentService, buildService, args).factory()
}


private val localBuildCommandFactory: CommandFactory = {
    object : LocalBuildCommand(args, configuration.localHome) {
        override fun onSuccess() {
            buildService.save(args)
        }
    }
}

private val startComponentCommandFactory: CommandFactory = {
    val component = args.toDto<BasicComponentInfo>()
    object : ManagementCommand() {

        override fun beforeStart() {
            componentService.update(component.id) {
                status = Status.STARTING
            }
        }

        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            executor.startComponent(component, configuration.deploymentConfiguration)
        }

        override fun onFail(ex: Exception) {
            componentService.update(component.id) {
                status = Status.DEAD
            }
        }
    }
}
private val killComponentCommandFactory: CommandFactory = {
    val component = args.toDto<BasicComponentInfo>()
    object : ManagementCommand() {
        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            executor.killComponent(component)
        }

        override fun onSuccess() {
            componentService.update(component.id) {
                status = Status.DEAD
            }
        }
    }
}

private val removeComponentCommandFactory: CommandFactory = {
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

private val startCorpusCommandFactory: CommandFactory = {
    val corpusId = args.toLong()
    val components = corpusService.getComponentsFor(corpusId)
    object : ManagementCommand() {

        override fun beforeStart() {
            corpusService.update(corpusId) {
                status = Status.RUNNING
            }
        }

        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            for (component in components)
                if (component.status == Status.DEAD)
                    executor.startComponent(component, configuration.deploymentConfiguration)
        }

        override fun onFail(ex: Exception) {
            corpusService.update(corpusId) {
                status = Status.DEAD
            }
        }

        override fun onSuccess() {
            corpusService.update(corpusId) {
                status = Status.RUNNING
            }
            for (component in components) {
                componentService.update(component.id) {
                    status = Status.RUNNING
                }
            }
        }
    }
}

private val killCorpusCommandFactory: CommandFactory = {
    val corpusId = args.toLong()
    val components = corpusService.getComponentsFor(corpusId)
    object : ManagementCommand() {
        override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) {
            for (component in components)
                if (component.status == Status.RUNNING)
                    executor.killComponent(component)
        }

        override fun onSuccess() {
            corpusService.update(corpusId) {
                status = Status.DEAD
            }
            for (component in components) {
                componentService.update(component.id) {
                    status = Status.DEAD
                }
            }
        }
    }
}