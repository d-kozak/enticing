package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ServerStatusEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ServerStatusRepository : JpaRepository<ServerStatusEntity, Long>