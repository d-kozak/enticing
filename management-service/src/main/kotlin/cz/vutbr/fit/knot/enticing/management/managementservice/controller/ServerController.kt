package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ServerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.base.path}/server")
class ServerController(loggerFactory: LoggerFactory, val serverService: ServerService) {

    val logger = loggerFactory.logger { }

    @GetMapping
    fun getAll() = serverService.getAllServers()
}