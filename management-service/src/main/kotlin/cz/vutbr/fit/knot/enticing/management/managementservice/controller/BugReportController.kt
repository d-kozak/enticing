package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.dto.BugReport
import cz.vutbr.fit.knot.enticing.management.managementservice.service.BugReportService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/bug-report")
class BugReportController(private val bugReportService: BugReportService) {

    @PostMapping
    fun submit(@RequestBody @Valid report: BugReport) = bugReportService.addNew(report)

    @GetMapping
    fun getPage(pageable: Pageable) = bugReportService.getPage(pageable)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = bugReportService.deleteById(id)

}






