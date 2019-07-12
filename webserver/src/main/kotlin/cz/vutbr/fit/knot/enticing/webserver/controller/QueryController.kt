package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.dto.Webserver
import cz.vutbr.fit.knot.enticing.webserver.service.QueryService
import org.springframework.web.bind.annotation.*
import java.net.URLDecoder

@RestController
@RequestMapping("\${api.base.path}/query")
class QueryController(private val queryService: QueryService) {

    @GetMapping
    fun query(@RequestParam query: String, @RequestParam settings: Long): List<Webserver.Snippet> = queryService.query(URLDecoder.decode(query, "UTF-8"), settings)

    @PostMapping("/context")
    fun context(@RequestBody query: Webserver.ContextExtensionQuery): Webserver.Snippet = queryService.context(query)

    @PostMapping("/document")
    fun document(@RequestBody query: Webserver.DocumentQuery): Webserver.FullDocument = queryService.document(query)
}