package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandState
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.CommandEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommandRepository : JpaRepository<CommandEntity, Long> {

    fun findFirstByStateOrderBySubmittedAtAsc(state: CommandState): CommandEntity?
}