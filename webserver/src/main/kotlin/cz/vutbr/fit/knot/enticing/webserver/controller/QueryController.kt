package cz.vutbr.fit.knot.enticing.webserver.controller

import cz.vutbr.fit.knot.enticing.dto.CorpusFormat
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.SnippetExtension
import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.webserver.service.QueryService
import cz.vutbr.fit.knot.enticing.webserver.service.TemporaryResultStorage
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession
import javax.validation.Valid

@RestController
@RequestMapping("\${api.base.path}/query")
class QueryController(private val queryService: QueryService, private val temporaryResultStorage: TemporaryResultStorage) {

    @PostMapping
    fun query(@RequestBody @Valid query: SearchQuery, @RequestParam settings: Long, session: HttpSession): WebServer.ResultList = queryService.query(query, settings, session)

    @GetMapping("/storage/{uuid}")
    fun storage(@PathVariable uuid: String) = temporaryResultStorage.getResults(uuid)

    @GetMapping("/get_more")
    fun getMore(session: HttpSession) = queryService.getMore(session)

    @PostMapping("/context")
    fun context(@RequestBody @Valid query: WebServer.ContextExtensionQuery): SnippetExtension = queryService.context(query)

    @PostMapping("/document")
    fun document(@RequestBody @Valid query: WebServer.DocumentQuery): WebServer.FullDocument = queryService.document(query)

    @PostMapping("/raw-document")
    fun rawDocument(@RequestBody @Valid request: WebServer.RawDocumentRequest) = queryService.getRawDocument(request)


    @GetMapping("/format/{settingsId}")
    fun format(@PathVariable settingsId: Long): CorpusFormat = queryService.format(settingsId)
}