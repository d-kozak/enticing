package cz.vutbr.fit.knot.enticing.index.server.controller

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.SnippetExtension
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.index.server.service.QueryService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}")
class QueryController(
        private val queryService: QueryService
) {

    @PostMapping("/query")
    fun query(@RequestBody @Valid query: SearchQuery): IndexServer.IndexResultList = queryService.processQuery(query)

    @PostMapping("/context")
    fun context(@RequestBody @Valid query: IndexServer.ContextExtensionQuery): SnippetExtension = queryService.extendContext(query)

    @GetMapping("/document")
    fun getDocument(@RequestParam id: Int, @RequestParam collection: String) = queryService.getDocument(IndexServer.DocumentQuery(collection, id, textFormat = TextFormat.PLAIN_TEXT))

    @PostMapping("/document")
    fun document(@RequestBody @Valid query: IndexServer.DocumentQuery): IndexServer.FullDocument = queryService.getDocument(query)

    @GetMapping("/format")
    fun format() = queryService.loadCorpusFormat()

}
