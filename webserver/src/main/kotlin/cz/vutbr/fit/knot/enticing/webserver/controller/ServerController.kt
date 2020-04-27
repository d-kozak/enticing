package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.mx.ServerMonitoringService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.base.path}/server-status")
class ServerController(
        val monitoringService: ServerMonitoringService
) {

    @GetMapping
    fun getServerStatus() = monitoringService.getStaticServerInfo()
}