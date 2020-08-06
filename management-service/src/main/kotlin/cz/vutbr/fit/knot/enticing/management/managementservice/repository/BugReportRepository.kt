package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.BugReportEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BugReportRepository : JpaRepository<BugReportEntity, Long>