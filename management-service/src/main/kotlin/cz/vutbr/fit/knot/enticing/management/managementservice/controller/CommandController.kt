package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandRequest
import cz.vutbr.fit.knot.enticing.management.managementservice.service.CommandService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/command")
class CommandController(
        val commandService: CommandService
) {

    @GetMapping
    fun get(pageable: Pageable) = commandService.getCommands(pageable)

    @PostMapping
    fun enqueueCommand(@RequestBody @Valid commandRequest: CommandRequest) = commandService.enqueue(commandRequest)
}