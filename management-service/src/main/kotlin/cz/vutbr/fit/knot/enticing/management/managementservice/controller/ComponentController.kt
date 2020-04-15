package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.management.managementservice.service.ComponentService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.base.path}/component")
class ComponentController(
        val componentService: ComponentService
) {

    @GetMapping
    fun getComponents(pageable: Pageable) = componentService.getComponents(pageable)
}