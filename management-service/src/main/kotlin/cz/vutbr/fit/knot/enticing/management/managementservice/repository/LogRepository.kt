package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.LogEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface LogRepository : JpaRepository<LogEntity, Long> {

    fun findAllByOrderByTimestampDesc(pageable: Pageable): Page<LogEntity>
}