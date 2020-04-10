package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.log.HeartbeatDto
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ServerStatusRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.findByFullAddress
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class HeartbeatService(
        val componentRepository: ComponentRepository,
        val serverStatusRepository: ServerStatusRepository,
        loggerFactory: LoggerFactory
) {

    private val logger = loggerFactory.logger { }

    @Transactional
    fun heartbeatReceived(dto: HeartbeatDto) {
        val component = componentRepository.findByFullAddress(dto.fullAddress)
        if (component == null) {
            logger.warn("Received heartbeat from an unknown component ${dto.fullAddress}")
            return
        }
        component.lastHeartbeat = dto.timestamp
        serverStatusRepository.save(dto.status!!.toEntity(component.server, dto.timestamp))
    }

}