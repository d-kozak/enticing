package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.webserver.dto.QueryValidationRequest
import cz.vutbr.fit.knot.enticing.webserver.service.QueryService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/compiler")
class EqlCompilerController(
        private val queryService: QueryService
) {

    @PostMapping
    fun get(@RequestBody @Valid queryValidationRequest: QueryValidationRequest) = queryService.validateQuery(queryValidationRequest.query, queryValidationRequest.settingsId)
}