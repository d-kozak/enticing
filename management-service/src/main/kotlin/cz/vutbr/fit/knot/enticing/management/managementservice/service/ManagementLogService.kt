package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.log.LogDto
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.LogRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.findByFullAddress
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ManagementLogService(
        val componentRepository: ComponentRepository,
        val logRepository: LogRepository,
        loggerFactory: LoggerFactory
) {

    private val logger = loggerFactory.logger { }


    fun add(log: LogDto): LogDto? {
        val component = componentRepository.findByFullAddress(log.componentAddress)
        if (component == null) {
            logger.warn("Received log from an unknown component at ${log.componentAddress}")
            return null
        }
        return logRepository.save(log.toEntity(component)).toDto()
    }

    fun getAll(pageable: Pageable) = logRepository.findAllByOrderByTimestampDesc(pageable).map { it.toDto() }
}