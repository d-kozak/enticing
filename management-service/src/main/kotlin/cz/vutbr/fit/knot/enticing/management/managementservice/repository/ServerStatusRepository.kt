package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ServerInfoEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ServerStatusEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ServerStatusRepository : JpaRepository<ServerStatusEntity, Long> {


    // SELECT * FROM tablename
    //WHERE id IN
    //(SELECT MIN(id) FROM tablename GROUP BY EmailAddress)

    //    @Query("select min(s.freePhysicalMemorySize),s.systemCpuLoad, distinct server  from ServerStatusEntity s")
    @Query("select s from ServerStatusEntity s where s.id in (select min(id) from ServerStatusEntity s1 where s1.timestamp in (select max(timestamp) from ServerStatusEntity group by server) group by s1.server)")
    fun findLastKnownStatusOfEachServer(): List<ServerStatusEntity>

    fun findByServerIdOrderByTimestampDesc(id: Long, pageable: Pageable): Page<ServerStatusEntity>

    @Query("select s from ServerStatusEntity s where s.server.id=:serverId and s.timestamp=(select max(s2.timestamp) from ServerStatusEntity s2 where s2.server.id=:serverId)")
    fun findLastStatusFor(serverId: Long): ServerStatusEntity?

    fun deleteByServer(server: ServerInfoEntity)

}