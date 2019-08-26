package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.dto.CorpusFormat
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.SnippetExtension
import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.webserver.service.QueryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.base.path}/query")
class QueryController(private val queryService: QueryService) {

    @PostMapping
    fun query(@RequestBody query: SearchQuery, @RequestParam settings: Long): WebServer.ResultList = queryService.query(query, settings)

    @PostMapping("/context")
    fun context(@RequestBody query: WebServer.ContextExtensionQuery): SnippetExtension = queryService.context(query)

    @PostMapping("/document")
    fun document(@RequestBody query: WebServer.DocumentQuery): WebServer.FullDocument = queryService.document(query)

    @GetMapping("/format/{settingsId}")
    fun format(@PathVariable settingsId: Long): CorpusFormat = queryService.format(settingsId)
}