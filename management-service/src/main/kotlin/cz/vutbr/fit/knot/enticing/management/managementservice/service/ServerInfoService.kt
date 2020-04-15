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
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
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


    fun addServer(serverInfo: StaticServerInfo): ServerInfo {
        logger.info("Adding new server $serverInfo")
        val server = serverInfoRepository.save(serverInfo.toEntity())
        componentService.addComponent(serverInfo, server)
        return server.toServerInfo(null)
    }


    fun deleteServerById(serverId: Long): ServerInfo {
        val server = serverInfoRepository.findByIdOrNull(serverId)
        check(server != null) { "no server with id $serverId found" }
        serverStatusRepository.deleteByServer(server)
        for (component in server.components) {
            componentService.deleteComponent(component)
        }
        serverInfoRepository.delete(server)
        return server.toServerInfo(null)
    }

}


