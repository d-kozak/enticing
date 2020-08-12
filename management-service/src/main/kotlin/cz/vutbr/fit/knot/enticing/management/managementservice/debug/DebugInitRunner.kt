package cz.vutbr.fit.knot.enticing.management.managementservice.debug

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandState
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandType
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.*
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.io.File
import java.time.LocalDateTime
import kotlin.random.Random

@Component
class DebugInitRunner(
        private val serverInfoRepository: ServerInfoRepository,
        private val componentRepository: ComponentRepository,
        private val serverStatusRepository: ServerStatusRepository,
        private val lastServerStatusRepository: LastServerStatusRepository,
        private val logRepository: LogRepository,
        private val perfRepository: PerfRepository,
        private val userRepository: UserRepository,
        private val commandRepository: CommandRepository,
        private val corpusRepository: CorpusRepository,
        private val encoder: PasswordEncoder,
        @Value("\${debug.runner.start}")
        private val runDebug: Boolean,
        loggerFactory: LoggerFactory,
        configuration: EnticingConfiguration
) : ApplicationRunner {

    private val logger = loggerFactory.logger {}

    private val users = mutableListOf(
            UserEntity("lojza", encoder.encode("lojza12")),
            UserEntity("honza", encoder.encode("honza"), roles = mutableSetOf("PLATFORM_MAINTAINER")),
            UserEntity("admin", encoder.encode("knot12"), roles = mutableSetOf("PLATFORM_MAINTAINER", "ADMIN"))
    )

    private val commands = (1..1000).map {
        CommandEntity(0, CommandType.values()[it % CommandType.values().size], CommandState.FINISHED, "args", users[it % 2 + 1],
                LocalDateTime.now().minusHours((it + 1).toLong() * 3),
                LocalDateTime.now().minusHours((it + 1).toLong() * 2),
                LocalDateTime.now().minusHours(it.toLong())
        )
    }

    private val servers = File("${configuration.localHome}/deploy/big-wiki/servers.txt")
            .readLines()
            .filter { it.isNotEmpty() }
            .mapIndexed { i, address -> ServerInfoEntity(0, address, Random.nextInt(15), Random.nextLong(20_000), mutableListOf(), mutableListOf()) }

    private val indexServers = servers.map { ComponentEntity(0, it, 5627, ComponentType.INDEX_SERVER, LocalDateTime.now(), mutableListOf(), mutableListOf()) }

    private val webservers = servers.filterIndexed { i, _ -> i % 300 == 0 }
            .map { ComponentEntity(0, it, 8080, ComponentType.WEBSERVER, LocalDateTime.now(), mutableListOf(), mutableListOf()) }

    private val components = indexServers + webservers

    private val serverStatus = servers.flatMap { server ->
        (1..1000).map { i ->
            ServerStatusEntity(0, (i % 10) * 0.1, (i % 10) * 0.1, (i % 10) * 0.1, LocalDateTime.now().minusHours(i.toLong()), server)
        }
    }

    private val logs = components.flatMap { component ->
        (1..1000).map { LogEntity(0, "foo.bar", "baz", LogType.values()[it % LogType.values().size], component, LocalDateTime.now().minusMinutes(it.toLong())) }
    }

    private val perfIds = (1..10).map { "op$it" }

    private val perfLogs = (1..1000).map {
        PerfEntity(0, "foo.bar", perfIds[it % perfIds.size], "args $it", it * 1000L,
                "SUCCESS", components.random(), LocalDateTime.now().minusMinutes(it.toLong()))
    }

    override fun run(args: ApplicationArguments) {
        if (!runDebug) {
            logger.info("Debug runner disabled")
            return
        }
        logger.info("Inserting dummy data")
        userRepository.saveAll(users)
        commandRepository.saveAll(commands)
        serverInfoRepository.saveAll(servers)
        serverStatusRepository.saveAll(serverStatus)
        for (status in serverStatus)
            lastServerStatusRepository.save(LastServerStatusEntity(status.server.id, status.freePhysicalMemorySize, status.processCpuLoad, status.systemCpuLoad, status.timestamp))
        componentRepository.saveAll(components)
        logRepository.saveAll(logs)
        perfRepository.saveAll(perfLogs)

        // ids of components have to be set before inserting into set
        val corpus = CorpusEntity(0, "wiki-19", components.toMutableSet())
        corpusRepository.save(corpus)
        for (component in components)
            component.corpuses.add(corpus)
        componentRepository.saveAll(components)
        logger.info("Finished...")
    }
}