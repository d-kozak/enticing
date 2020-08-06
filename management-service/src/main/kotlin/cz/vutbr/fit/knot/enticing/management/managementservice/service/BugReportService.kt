package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.api.ComponentNotAccessibleException
import cz.vutbr.fit.knot.enticing.dto.BugReport
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.BugReportEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.BugReportRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate

@Service
class BugReportService(
        @Value("\${api.base.path}") private val apiBasePath: String,
        private val bugReportRepository: BugReportRepository
) {

    private val template = RestTemplate()

    fun addNew(report: BugReport) = bugReportRepository.save(report.toEntity())

    fun getPage(pageable: Pageable) = bugReportRepository.findAll(pageable)

    fun deleteById(id: Long) = bugReportRepository.deleteById(id)

    fun getRawMg4jDocument(reportId: Long): String {
        val report = bugReportRepository.findByIdOrNull(reportId)
                ?: throw IllegalArgumentException("Invalid report id $reportId")
        val url = "http://${report.host}$apiBasePath/raw-document/${report.collection}/${report.documentId}?"
        try {
            val response = template.getForEntity(url, String::class.java)
            return response.body
                    ?: throw IllegalStateException("Request  to download mg4j document for $report failed")
        } catch (ex: ResourceAccessException) {
            throw ComponentNotAccessibleException("Could not access component at $url")
        }
    }
}

fun BugReport.toEntity() = BugReportEntity(0, query, filterOverlaps, host, collection, documentId, documentTitle, matchInterval, description)
fun BugReportEntity.toDto() = BugReport(query, filterOverlaps, host, collection, documentId, documentTitle, matchInterval, description)