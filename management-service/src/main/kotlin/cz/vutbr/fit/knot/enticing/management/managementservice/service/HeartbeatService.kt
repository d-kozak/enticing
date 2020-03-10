package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.HeartbeatDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.HeartbeatRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerStatusRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class HeartbeatService(
        val repository: HeartbeatRepository,
        val serverService: ServerService,
        val serverStatusRepository: ServerStatusRepository,
        val configuration: EnticingConfiguration
) : AutoCloseable {

    private val scope = CoroutineScope(context = Dispatchers.IO)

    fun heartbeat(dto: HeartbeatDto): HeartbeatDto = repository.save(dto.toEntity()).toDto(true, dto.status).also {
        scope.launch {
            serverService.heartbeat(dto)
        }
    }

    fun getAll() = repository.findAll()
            .map {
                it.toDto(isComponentAlive(it.timestamp), serverStatusRepository.findLastLastStatusFor(it.componentId)?.toDto())
            }

    private fun isComponentAlive(lastTimestamp: LocalDateTime) = Duration.between(lastTimestamp, LocalDateTime.now()).toMillis() < 5 * configuration.managementServiceConfiguration.heartbeatConfiguration.period

    override fun close() {
        scope.cancel()
    }
}