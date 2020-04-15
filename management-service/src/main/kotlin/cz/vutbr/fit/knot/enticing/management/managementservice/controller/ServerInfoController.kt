package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.management.managementservice.service.ComponentService
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ServerInfoService
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/server")
class ServerInfoController(
        val serverInfoService: ServerInfoService,
        val componentService: ComponentService
) {

    @GetMapping
    fun getAll(pageable: Pageable) = serverInfoService.getServers(pageable)

    @GetMapping("/{serverId}")
    fun getServerDetails(@PathVariable serverId: Long) = serverInfoService.getServerDetails(serverId)

    @GetMapping("/{serverId}/component")
    fun getComponents(@PathVariable serverId: Long, pageable: Pageable) = componentService.getComponentsOnServer(serverId, pageable)

    @GetMapping("/{serverId}/stats")
    fun getStats(@PathVariable serverId: Long, pageable: Pageable) = serverInfoService.getLastStats(serverId, pageable)

    @PostMapping
    fun registerServer(@Valid @RequestBody serverInfo: StaticServerInfo) = serverInfoService.registerServer(serverInfo)

    @DeleteMapping("/{serverId}")
    fun deleteServer(@PathVariable serverId: Long) = serverInfoService.deleteServerById(serverId)
}