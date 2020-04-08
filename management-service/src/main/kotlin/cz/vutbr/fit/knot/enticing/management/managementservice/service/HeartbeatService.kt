package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.HeartbeatDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.HeartbeatRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerStatusRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
class HeartbeatService(
        val repository: HeartbeatRepository,
        val serverInfoService: ServerInfoService,
        val serverStatusRepository: ServerStatusRepository,
        val configuration: EnticingConfiguration
) : AutoCloseable {

    private val scope = CoroutineScope(context = Dispatchers.IO)

    fun heartbeat(dto: HeartbeatDto): HeartbeatDto {
        val res = repository.save(dto.toEntity())
        scope.launch {
            serverInfoService.heartbeat(dto)
        }
        return res.toDto(dto.status)
    }

    fun getAll() = repository.findAll()
            .map {
                it.toDto(serverStatusRepository.findLastLastStatusFor(it.componentId)?.toDto())
            }

    override fun close() {
        scope.cancel()
    }
}