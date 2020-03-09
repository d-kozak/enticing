package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.log.LogDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.LogRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ManagementLogService(val logRepository: LogRepository) {

    fun add(log: LogDto) = logRepository.save(log.toEntity()).toDto()

    fun getAll(pageable: Pageable) = logRepository.findAll(pageable).map { it.toDto() }
}