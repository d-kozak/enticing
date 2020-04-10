package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ServerInfoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ServerInfoRepository : JpaRepository<ServerInfoEntity, String>