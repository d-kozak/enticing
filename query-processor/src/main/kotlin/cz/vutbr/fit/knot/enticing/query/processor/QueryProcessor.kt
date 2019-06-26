package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.query.processor.request.ServerInfo
import cz.vutbr.fit.knot.enticing.query.processor.request.ServerResult

interface QueryProcessor {
    fun process(searchQuery: SearchQuery, serverInfo: List<ServerInfo>): List<ServerResult>
}