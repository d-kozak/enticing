package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.management.managementservice.service.ServerInfoService
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/server")
class ServerInfoController(val serverInfoService: ServerInfoService) {

    @GetMapping
    fun getAll(pageable: Pageable) = serverInfoService.getServers(pageable)

    @GetMapping("/{serverId}")
    fun getServerStatus(pageable: Pageable, @PathVariable serverId: Long) = serverInfoService.getServerStatus(serverId, pageable)

    @PostMapping
    fun addNew(@Valid @RequestBody serverInfo: StaticServerInfo) = serverInfoService.addServer(serverInfo)
}