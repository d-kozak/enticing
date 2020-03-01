package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.PerfDto
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ManagementPerfService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/perf")
class ManagementPerfController(loggerFactory: LoggerFactory, val managementPefService: ManagementPerfService) {

    val logger = loggerFactory.logger { }

    @PostMapping
    fun add(@RequestBody @Valid perf: PerfDto) {
        logger.info("received perf $perf")
        managementPefService.add(perf)
    }

    @GetMapping
    fun getAll() = managementPefService.getAll()

    @GetMapping("/stats")
    fun stats() = managementPefService.computeOperationStatistics()
}