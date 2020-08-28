package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.management.managementservice.service.BuildService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/build")
class BuildController(
        private val buildService: BuildService
) {

    @RequestMapping("/{buildId}")
    fun isUnique(@PathVariable buildId: String) = buildService.getOne(buildId)
}