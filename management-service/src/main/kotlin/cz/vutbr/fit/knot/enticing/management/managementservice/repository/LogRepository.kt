package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ComponentEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.LogEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface LogRepository : JpaRepository<LogEntity, Long> {

    fun deleteByComponent(component: ComponentEntity)

    fun findTop5ByLogTypeInOrderByTimestampDesc(opts: List<LogType>): List<LogEntity>

    fun findAllByOrderByTimestampDesc(pageable: Pageable): Page<LogEntity>
    fun findByLogTypeOrderByTimestampDesc(logType: LogType, pageable: Pageable): Page<LogEntity>
    fun findByComponentTypeOrderByTimestampDesc(componentType: ComponentType, pageable: Pageable): Page<LogEntity>
    fun findByLogTypeAndComponentTypeOrderByTimestampDesc(logType: LogType, componentType: ComponentType, pageable: Pageable): Page<LogEntity>
    fun findByComponentId(componentId: Long, pageable: Pageable): Page<LogEntity>
    fun findByComponentIdAndLogType(componentId: Long, logType: LogType, pageable: Pageable): Page<LogEntity>
}