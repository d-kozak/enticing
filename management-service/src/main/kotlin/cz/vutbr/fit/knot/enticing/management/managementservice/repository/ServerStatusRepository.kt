package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ServerStatusEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ServerStatusRepository : JpaRepository<ServerStatusEntity, Long> {

    @Query("select s from ServerStatusEntity s where s.server.componentId=:componentId",
            countQuery = "select count(s) from ServerStatusEntity s where s.server.componentId=:componentId")
    fun findAllByServerCustom(componentId: String, pageable: Pageable): Page<ServerStatusEntity>
}