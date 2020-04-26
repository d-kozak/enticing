package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.PerfDto
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ManagementPerfService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/perf")
class ManagementPerfController(loggerFactory: LoggerFactory, val managementPerfService: ManagementPerfService) {

    val logger = loggerFactory.logger { }

    @PostMapping
    fun add(@RequestBody @Valid perf: PerfDto) {
        logger.info("received perf $perf")
        managementPerfService.add(perf)
    }

    @GetMapping
    fun getAll(pageable: Pageable, @RequestParam componentType: ComponentType?, @RequestParam(required = false) operationId: String?) = managementPerfService.getPerfLogs(operationId, componentType, pageable)

    @GetMapping("/stats")
    fun stats(@RequestParam(required = false) operationId: String?): Any = if (operationId == null) managementPerfService.getAllOperationStats()
    else managementPerfService.getSingleOperationStats(operationId)
}