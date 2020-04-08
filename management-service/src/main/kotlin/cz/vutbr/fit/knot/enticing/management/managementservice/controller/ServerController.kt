package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ServerInfoService
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/server")
class ServerController(loggerFactory: LoggerFactory, val serverInfoService: ServerInfoService) {

    val logger = loggerFactory.logger { }

    @GetMapping
    fun getAll(pageable: Pageable) = serverInfoService.getServers(pageable)

    @GetMapping("/{componentId}")
    fun getServerStatus(pageable: Pageable, @PathVariable componentId: String) = serverInfoService.getServerStatus(componentId, pageable)

    @PostMapping
    fun addNew(@Valid @RequestBody serverInfo: StaticServerInfo) = serverInfoService.addServer(serverInfo)
}