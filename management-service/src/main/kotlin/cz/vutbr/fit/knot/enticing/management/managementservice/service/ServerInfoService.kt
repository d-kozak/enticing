package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toServerInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerInfoRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerStatusRepository
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ServerInfoService(
        val serverInfoRepository: ServerInfoRepository,
        val serverStatusRepository: ServerStatusRepository,
        val componentService: ComponentService,
        loggerFactory: LoggerFactory
) {

    private val logger = loggerFactory.logger { }

    fun getServers(pageable: Pageable) = serverInfoRepository.findAll(pageable).map { it.toServerInfo(serverStatusRepository.findLastStatusFor(it.id)?.toDto()) }

    fun getServerStatus(serverId: Long, pageable: Pageable) = serverStatusRepository.findByServerIdOrderByTimestampDesc(serverId, pageable)

    @Transactional
    fun addServer(serverInfo: StaticServerInfo): ServerInfo {
        logger.info("Adding new server $serverInfo")
        val server = serverInfoRepository.save(serverInfo.toEntity())
        componentService.addComponent(serverInfo, server)
        return server.toServerInfo(null)
    }

}


