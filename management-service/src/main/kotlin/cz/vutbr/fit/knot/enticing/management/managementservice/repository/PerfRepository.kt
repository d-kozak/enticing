package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ComponentEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.PerfEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PerfRepository : JpaRepository<PerfEntity, Long> {
    fun deleteByComponent(component: ComponentEntity)
    fun findByOperationId(operationId: String, pageable: Pageable): Page<PerfEntity>
    fun findByOperationId(operationId: String): List<PerfEntity>
    fun findByOperationIdAndComponentType(operationId: String, componentType: ComponentType, pageable: Pageable): Page<PerfEntity>
    fun findByComponentType(componentType: ComponentType, pageable: Pageable): Page<PerfEntity>
}