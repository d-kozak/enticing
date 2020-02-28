package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.log.PerfDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.PerfRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ManagementPerfService(val perfRepository: PerfRepository) {

    fun add(Perf: PerfDto) = perfRepository.save(Perf.toEntity()).toDto()

    fun getAll() = perfRepository.findAll().map { it.toDto() }
}