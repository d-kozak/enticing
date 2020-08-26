package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.AddComponentRequest
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ComponentService
import cz.vutbr.fit.knot.enticing.management.managementservice.service.ServerInfoService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/component")
class ComponentController(
        private val componentService: ComponentService,
        private val serverInfoService: ServerInfoService
) {

    @GetMapping
    fun getComponents(pageable: Pageable, @RequestParam type: ComponentType?, @RequestParam corpusId: Long?) =
            if (corpusId != null) componentService.getComponentsOfCorpus(corpusId, pageable) else componentService.getComponents(type, pageable)

    @GetMapping("/{componentId}")
    fun getComponent(@PathVariable componentId: Long) = componentService.getComponent(componentId)

    @PostMapping
    fun addComponent(@RequestBody @Valid request: AddComponentRequest) = serverInfoService.addComponent(request)

}