package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.BuildEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.BuildRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BuildService(
        private val buildRepository: BuildRepository
) {

    fun isFree(name: String) = buildRepository.existsById(name)

    fun save(name: String) = buildRepository.save(BuildEntity(name))

    fun getAll(pageable: Pageable) = buildRepository.findAll(pageable)

    fun findByName(name: String) = buildRepository.findByIdOrNull(name)
}