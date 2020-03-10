package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ServerStatusEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ServerStatusRepository : JpaRepository<ServerStatusEntity, Long> {

    fun findByServerComponentIdOrderByTimestampDesc(componentId: String, pageable: Pageable): Page<ServerStatusEntity>

    @Query("select s from ServerStatusEntity s where s.server.componentId=:componentId and s.timestamp=(select max(s2.timestamp) from ServerStatusEntity s2 where s2.server.componentId=:componentId)")
    fun findLastLastStatusFor(componentId: String): ServerStatusEntity?

}