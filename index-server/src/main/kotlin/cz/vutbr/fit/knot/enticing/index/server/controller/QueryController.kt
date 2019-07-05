package cz.vutbr.fit.knot.enticing.index.server.controller

import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("\${api.base.path}/query")
class QueryController {

    @PostMapping
    fun query(@RequestBody query: SearchQuery) = "hello"

}