package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.api.ManagementServiceApi
import cz.vutbr.fit.knot.enticing.dto.BugReport
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/bug-report")
class BugReportController(val managementApi: ManagementServiceApi) {

    @PostMapping
    fun submit(@RequestBody @Valid report: BugReport) = managementApi.bugReport(report)

}






