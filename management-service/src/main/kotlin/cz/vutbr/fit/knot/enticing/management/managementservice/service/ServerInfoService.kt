package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.AddServerRequest
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.*
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerInfoRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerStatusRepository
import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ServerInfoService(
        private val serverInfoRepository: ServerInfoRepository,
        private val serverStatusRepository: ServerStatusRepository,
        private val componentService: ComponentService,
        private val componentRepository: ComponentRepository,
        private val serverProbeApi: ServerProbeApi,
        loggerFactory: LoggerFactory
) {

    private val logger = loggerFactory.logger { }

    fun getServers(pageable: Pageable) = serverInfoRepository.findAll(pageable).map { it.toServerInfo(serverStatusRepository.findLastStatusFor(it.id)?.toDto()) }

    fun getServerDetails(serverId: Long) = serverInfoRepository.findByIdOrNull(serverId)?.toServerInfo(null)


    fun registerServer(serverInfo: StaticServerInfo): ServerInfo {
        val (address, port) = serverInfo.fullAddress.split(":")
        logger.info("Registering server $serverInfo")
        val serverEntity = serverInfoRepository.findByAddress(address)
                ?: serverInfoRepository.save(serverInfo.toEntity())
        serverEntity.availableProcessors = serverInfo.availableProcessors
        serverEntity.totalPhysicalMemorySize = serverInfo.totalPhysicalMemorySize
        val previous = serverEntity.components.find { it.fullAddress == serverInfo.fullAddress }
        if (previous == null) {
            componentRepository.save(ComponentEntity(0, serverEntity, port.toInt(), serverInfo.componentType, serverInfo.timestamp, listOf(), listOf()))
        } else {
            previous.lastHeartbeat = serverInfo.timestamp
        }
        return serverEntity.toServerInfo(null)
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

    fun getLastStats(serverId: Long, pageable: Pageable): Page<ServerStatus> = serverStatusRepository.findByServerIdOrderByTimestampDesc(serverId, pageable)
            .map { it.toDto() }

    fun addServerRequest(request: AddServerRequest): ServerInfo {
        val info = serverProbeApi.request(request)
        val entity = serverInfoRepository.save(ServerInfoEntity(0, request.url, info.processorCount, info.ramSize, listOf(), mutableListOf()))
        return entity.toServerInfo(ServerStatus(info.freeRam.toDouble() / info.ramSize, info.processCpuLoad, info.systemCpuLoad))
    }

}


