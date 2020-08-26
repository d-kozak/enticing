package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.api.EnticingComponentApi
import cz.vutbr.fit.knot.enticing.dto.Status
import cz.vutbr.fit.knot.enticing.dto.toComponentAddress
import cz.vutbr.fit.knot.enticing.log.HeartbeatDto
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerStatusRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.findByFullAddress
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional
@Service
class HeartbeatService(
        private val componentRepository: ComponentRepository,
        private val serverStatusRepository: ServerStatusRepository,
        private val serverInfoService: ServerInfoService,
        private val componentApi: EnticingComponentApi,
        loggerFactory: LoggerFactory
) {

    private val logger = loggerFactory.logger { }


    fun heartbeatReceived(dto: HeartbeatDto) {
        var component = componentRepository.findByFullAddress(dto.fullAddress)
        if (component == null) {
            logger.warn("Received heartbeat from an unknown component ${dto.fullAddress}, trying to load information for it")
            val serverInfo = componentApi.ping(dto.fullAddress.toComponentAddress()) ?: return
            serverInfoService.registerServer(serverInfo)
            logger.warn("Server registered $serverInfo along with this component")
            component = componentRepository.findByFullAddress(dto.fullAddress)
            if (component == null) {
                logger.warn("Could not retrieve the freshly saved component")
                return
            }
        }
        component.lastHeartbeat = dto.status.timestamp
        component.status = Status.RUNNING
        serverStatusRepository.save(dto.status.toEntity(component.server))
    }

}