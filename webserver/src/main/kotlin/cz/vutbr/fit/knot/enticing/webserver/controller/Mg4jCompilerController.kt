package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.webserver.service.Mg4jCompilerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URLDecoder

@RestController
@RequestMapping("\${api.base.path}/compiler")
class Mg4jCompilerController(
        private val compilerService: Mg4jCompilerService
) {

    @GetMapping
    fun get(@RequestParam query: String) = compilerService.parseQuery(URLDecoder.decode(query, "UTF-8")).errors
}