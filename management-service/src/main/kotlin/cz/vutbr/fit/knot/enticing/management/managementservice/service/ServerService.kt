package cz.vutbr.fit.knot.enticing.management.managementservice.service

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.log.HeartbeatDto
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ServerEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ServerStatusEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerStatusRepository
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

private const val API_BASE_PATH = "/api/v1"

@Service
class ServerService(
        val serverRepository: ServerRepository,
        val serverStatusRepository: ServerStatusRepository,
        loggerFactory: LoggerFactory
) {

    val logger = loggerFactory.logger { }


    fun getAllServers() = serverRepository.findAll()

    @Transactional
    fun heartbeat(heartbeat: HeartbeatDto) {
        val server = getOrCreateServer(heartbeat.fullAddress) ?: return
        val status = heartbeat.status!!
        server.status.add(serverStatusRepository.save(ServerStatusEntity(0, status.freePhysicalMemorySize, status.processCpuLoad, status.systemCpuLoad, heartbeat.timestamp, server)))
    }

    @Transactional
    fun getOrCreateServer(fullAddress: String): ServerEntity? {
        val server = serverRepository.findByIdOrNull(fullAddress)
        if (server != null) {
            return server
        } else {
            val (processors, memorySize) = getStaticServerInfo(fullAddress) ?: return null
            val entity = ServerEntity(fullAddress, processors, memorySize, mutableListOf())
            return serverRepository.save(entity)
        }
    }

    fun getStaticServerInfo(fullAddress: String): StaticServerInfo? {
        val url = "http://$fullAddress$API_BASE_PATH/server-status"
        val (_, _, result) = url.httpGet()
                .responseString()
        return if (result is Result.Failure) {
            logger.error("Failed to load server info from $url ${result.error.exception.message}")
            null
        } else result.get().toDto()
    }
}

