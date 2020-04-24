package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.Average
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.LastServerStatusEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LastServerStatusRepository : JpaRepository<LastServerStatusEntity, Long> {
    @Query("select new cz.vutbr.fit.knot.enticing.management.managementservice.entity.Average(avg(e.systemCpuLoad),avg(e.freePhysicalMemorySize)) from LastServerStatusEntity e")
    fun computeAverage(): Average
}