package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.webserver.service.QueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URLDecoder

@RestController
@RequestMapping("\${api.base.path}/query")
class QueryController(private val queryService: QueryService) {

    @GetMapping
    fun query(@RequestParam query: String, @RequestParam settings: Long) = queryService.query(URLDecoder.decode(query, "UTF-8"), settings)

    @GetMapping("/context")
    fun context(@RequestParam docId: Long, @RequestParam size: Long, @RequestParam location: Long) = queryService.context(docId, location, size)

    @GetMapping("/document")
    fun document(@RequestParam docId: Long) = queryService.document(docId)
}