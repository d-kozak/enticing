package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.dto.BugReport
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.BugReportEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.BugReportRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BugReportService(
        private val bugReportReportRepository: BugReportRepository
) {

    fun addNew(report: BugReport) = bugReportReportRepository.save(report.toEntity())

    fun getPage(pageable: Pageable) = bugReportReportRepository.findAll(pageable)
    fun deleteById(id: Long) = bugReportReportRepository.deleteById(id)
}

fun BugReport.toEntity() = BugReportEntity(0, query, filterOverlaps, host, collection, documentId, documentTitle, matchInterval, description)
fun BugReportEntity.toDto() = BugReport(query, filterOverlaps, host, collection, documentId, documentTitle, matchInterval, description)