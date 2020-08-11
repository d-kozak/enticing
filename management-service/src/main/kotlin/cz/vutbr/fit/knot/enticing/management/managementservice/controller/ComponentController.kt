package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ComponentService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.base.path}/component")
class ComponentController(
        private val componentService: ComponentService
) {

    @GetMapping
    fun getComponents(pageable: Pageable, @RequestParam type: ComponentType?) = componentService.getComponents(type, pageable)

    @GetMapping("/{componentId}")
    fun getComponent(@PathVariable componentId: Long) = componentService.getComponent(componentId)

    @DeleteMapping("/{componentId}")
    fun deleteComponent(@PathVariable componentId: Long) = componentService.deleteComponent(componentId)
}