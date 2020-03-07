package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ServerService
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/server")
class ServerController(loggerFactory: LoggerFactory, val serverService: ServerService) {

    val logger = loggerFactory.logger { }

    @GetMapping
    fun getAll(pageable: Pageable) = serverService.getServers(pageable)

    @GetMapping("/{componentId}")
    fun getServerStatus(pageable: Pageable, @PathVariable componentId: String) = serverService.getServerStatus(componentId, pageable)

    @PostMapping
    fun addNew(@Valid @RequestBody serverInfo: StaticServerInfo) = serverService.addServer(serverInfo)
}