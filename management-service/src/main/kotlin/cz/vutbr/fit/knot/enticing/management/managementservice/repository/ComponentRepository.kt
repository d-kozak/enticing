package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ComponentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ComponentRepository : JpaRepository<ComponentEntity, Long>