package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.webserver.service.EqlCompilerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URLDecoder

@RestController
@RequestMapping("\${api.base.path}/compiler")
class EqlCompilerController(
        private val compilerService: EqlCompilerService
) {

    @GetMapping
    fun get(@RequestParam query: String, @RequestParam settings: Long) = compilerService.parseQuery(URLDecoder.decode(query, "UTF-8"), settings).errors
}