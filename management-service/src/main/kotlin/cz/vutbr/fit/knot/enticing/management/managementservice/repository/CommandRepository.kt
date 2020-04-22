package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandState
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandType
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.CommandEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CommandRepository : JpaRepository<CommandEntity, Long> {
    fun findFirstByStateOrderBySubmittedAtAsc(state: CommandState): CommandEntity?
    fun findByType(type: CommandType, pageable: Pageable): Page<CommandEntity>
}