package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.log.LogMessage
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ManagementLogService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/log")
class ManagementLogController(logService: MeasuringLogService, val managementLogService: ManagementLogService) {

    val logger = logService.logger { }

    @PostMapping
    fun add(@RequestBody @Valid log: LogMessage) {
        logger.info("received log $log")
        managementLogService.add(log)
    }

    @GetMapping
    fun getAll() = managementLogService.getAll()
}