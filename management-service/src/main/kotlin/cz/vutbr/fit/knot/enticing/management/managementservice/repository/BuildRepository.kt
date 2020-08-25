package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.BuildEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BuildRepository : JpaRepository<BuildEntity, String>