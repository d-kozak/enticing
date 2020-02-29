package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.HeartbeatEntity
import org.springframework.data.jpa.repository.JpaRepository

interface HeartbeatRepository : JpaRepository<HeartbeatEntity, String>