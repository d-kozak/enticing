package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.log.HeartbeatDto
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toServerInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerStatusRepository
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ServerService(
        val serverRepository: ServerRepository,
        val serverStatusRepository: ServerStatusRepository,
        loggerFactory: LoggerFactory
) {

    val logger = loggerFactory.logger { }

    fun getServers(pageable: Pageable) = serverRepository.findAll(pageable).map { it.toServerInfo(serverStatusRepository.findLastLastStatusFor(it.componentId)?.toDto()) }

    fun getServerStatus(componentId: String, pageable: Pageable) = serverStatusRepository.findByServerComponentIdOrderByTimestampDesc(componentId, pageable)

    @Transactional
    fun heartbeat(heartbeat: HeartbeatDto) {
        val server = serverRepository.findByIdOrNull(heartbeat.fullAddress)
        if (server == null) {
            logger.warn("Received heartbeat from an unknown component ${heartbeat.fullAddress}")
            return
        }
        serverStatusRepository.save(heartbeat.status!!.toEntity(server, heartbeat.timestamp))
    }

    fun addServer(serverInfo: StaticServerInfo) = serverRepository.save(serverInfo.toEntity())
            .also { logger.info("Adding new server $it") }

}


