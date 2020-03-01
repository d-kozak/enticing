package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.HeartbeatDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.HeartbeatRepository
import org.springframework.stereotype.Service

@Service
class HeartbeatService(
        val repository: HeartbeatRepository,
        val configuration: EnticingConfiguration
) {

    fun heartbeat(dto: HeartbeatDto) = repository.save(dto.toEntity()).toDto(true)

    fun getAll() = repository.findAll().map { it.toDto(isComponentAlive(it.timestamp)) }

    private fun isComponentAlive(lastTimestamp: Long) =
            System.currentTimeMillis() - lastTimestamp < 5 * configuration.managementServiceConfiguration.heartbeatConfiguration.period
}