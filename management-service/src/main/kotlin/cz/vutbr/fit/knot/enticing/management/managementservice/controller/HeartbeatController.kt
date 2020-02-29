package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.log.HeartbeatDto
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.service.HeartbeatService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/heartbeat")
class HeartbeatController(loggerFactory: LoggerFactory, val heartbeatService: HeartbeatService) {

    val logger = loggerFactory.logger { }

    @PostMapping
    fun add(@RequestBody @Valid dto: HeartbeatDto) {
        logger.info("received heartbeat $dto")
        heartbeatService.heartbeat(dto)
    }

    @GetMapping
    fun getAll() = heartbeatService.getAll()
}