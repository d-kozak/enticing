package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LogDto
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ManagementLogService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/log")
class ManagementLogController(loggerFactory: LoggerFactory, val managementLogService: ManagementLogService) {

    val logger = loggerFactory.logger { }

    @PostMapping
    fun add(@RequestBody @Valid log: LogDto) {
        logger.info("received log $log")
        managementLogService.add(log)
    }

    @GetMapping
    fun getAll(@RequestParam logType: LogType?, @RequestParam componentType: ComponentType?, pageable: Pageable) = managementLogService.getLogs(logType, componentType, pageable)
}