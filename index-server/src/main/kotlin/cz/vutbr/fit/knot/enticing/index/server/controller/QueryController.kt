package cz.vutbr.fit.knot.enticing.index.server.controller

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.index.server.service.QueryService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("\${api.base.path}/query")
class QueryController(
        private val queryService: QueryService
) {

    @PostMapping
    fun query(@RequestBody query: SearchQuery): IndexServer.SearchResult {
        val result = queryService.processQuery(query)
        return if (result.isSuccess) result.value else result.rethrowException()
    }

}