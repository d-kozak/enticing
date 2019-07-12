package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.dto.query.ContextExtensionQuery
import cz.vutbr.fit.knot.enticing.dto.query.ExtendedDocumentQuery
import cz.vutbr.fit.knot.enticing.dto.response.ExtendedDocument
import cz.vutbr.fit.knot.enticing.dto.response.ExtendedSnippet
import cz.vutbr.fit.knot.enticing.webserver.service.QueryService
import org.springframework.web.bind.annotation.*
import java.net.URLDecoder

@RestController
@RequestMapping("\${api.base.path}/query")
class QueryController(private val queryService: QueryService) {

    @GetMapping
    fun query(@RequestParam query: String, @RequestParam settings: Long): List<ExtendedSnippet> = queryService.query(URLDecoder.decode(query, "UTF-8"), settings)

    @PostMapping("/context")
    fun context(@RequestBody query: ContextExtensionQuery): ExtendedSnippet = queryService.context(query)

    @PostMapping("/document")
    fun document(@RequestBody query: ExtendedDocumentQuery): ExtendedDocument = queryService.document(query)
}