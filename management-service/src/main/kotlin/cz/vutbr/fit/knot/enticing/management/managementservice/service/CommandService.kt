package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandRequest
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandState
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandType
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.CommandDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.CommandEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.CommandRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.UserRepository
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class CommandService(
        private val configuration: EnticingConfiguration,
        private val commandRepository: CommandRepository,
        private val userService: ManagementUserService,
        private val userRepository: UserRepository,
        private val loggerFactory: LoggerFactory,
        @Value("\${command.runner.start}")
        private val runExecutor: Boolean
) : AutoCloseable {

    private val logger = loggerFactory.logger { }

    private val scope = CoroutineScope(Dispatchers.IO)

    private val commandLogDirectory = Path.of(configuration.loggingConfiguration.rootDirectory, "commands")

    init {
        val file = commandLogDirectory.toFile()
        if (!file.exists())
            check(commandLogDirectory.toFile().mkdirs()) { "failed to create directory for command log files at $commandLogDirectory" }
        check(file.isDirectory) { "$commandLogDirectory is not a directory" }

        if (runExecutor) {
            scope.launch {
                delay(2_000)
                commandExecutionLoop()
            }
        } else logger.warn("Executor NOT started")
    }

    @Transactional
    fun enqueue(request: CommandRequest): CommandDto {
        val userDto = userService.requireMaintainerOrAdmin()
        val userEntity = userRepository.findByIdOrNull(userDto.login)
                ?: error("User $userDto should be found in the database")
        val (type, arguments) = request
        val commandEntity = commandRepository.save(CommandEntity(0, type, CommandState.ENQUED, arguments, userEntity))

        return commandEntity.toDto()
    }

    // todo error recovery and timeout
    internal suspend fun commandExecutionLoop() {
        logger.info("Starting the execution loop")
        while (true) {
            var commandEntity: CommandEntity? = commandRepository.findFirstByStateOrderBySubmittedAtAsc(CommandState.ENQUED)
            while (commandEntity == null) {
                logger.info("Waiting for next command")
                delay(2_000)
                commandEntity = commandRepository.findFirstByStateOrderBySubmittedAtAsc(CommandState.ENQUED)
            }
            execute(commandEntity)
        }
    }

    internal suspend fun execute(entity: CommandEntity) {
        logger.info("Preparing to execute command $entity")
        entity.startAt = LocalDateTime.now()
        entity.state = CommandState.RUNNING
        commandRepository.save(entity)
        try {
            val logFile = commandLogDirectory.resolve("${entity.id}.log").toString()
            val executor = ShellCommandExecutor(loggerFactory, scope, logFile)
            executor.use {
                val commandDto = entity.type.init(configuration, entity.arguments)
                commandDto.execute(scope, executor)
            }
            entity.finishedAt = LocalDateTime.now()
            entity.state = CommandState.FINISHED
            logger.info("Command $entity finished")
        } catch (ex: Exception) {
            entity.finishedAt = LocalDateTime.now()
            entity.state = CommandState.FAILED
            logger.info("Command $entity failed: ${ex::class} ${ex.message}")
        } finally {
            commandRepository.save(entity)
        }
    }


    override fun close() {
        scope.cancel()
    }

    fun getCommands(type: CommandType?, state: CommandState?, pageable: Pageable): Page<CommandDto> {
        val entities = when {
            state != null && type != null -> commandRepository.findByTypeAndState(type, state, pageable)
            state != null -> commandRepository.findByState(state, pageable)
            type != null -> commandRepository.findByType(type, pageable)
            else -> commandRepository.findAll(pageable)
        }
        return entities.map { it.toDto() }
    }

    fun getCommand(commandId: Long) = commandRepository.findByIdOrNull(commandId)?.toDto()
}