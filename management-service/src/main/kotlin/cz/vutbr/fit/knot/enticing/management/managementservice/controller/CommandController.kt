package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandRequest
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandState
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandType
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
    fun get(pageable: Pageable, @RequestParam type: CommandType?, @RequestParam state: CommandState?) = commandService.getCommands(type, state, pageable)

    @GetMapping("/{commandId}")
    fun details(@PathVariable commandId: Long) = commandService.getCommand(commandId)

    @GetMapping("/{commandId}/log")
    fun commandLogs(@PathVariable commandId: Long, @RequestParam(defaultValue = "0") startLine: Int) = commandService.getCommandLogs(commandId, startLine)

    @PostMapping
    fun enqueueCommand(@RequestBody @Valid commandRequest: CommandRequest) = commandService.enqueue(commandRequest)
}