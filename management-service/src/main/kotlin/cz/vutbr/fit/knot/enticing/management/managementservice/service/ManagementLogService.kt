package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.log.LogDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.LogRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ManagementLogService(val logRepository: LogRepository) {

    fun add(log: LogDto) = logRepository.save(log.toEntity()).toDto()

    fun getAll() = logRepository.findAll().map { it.toDto() }
}