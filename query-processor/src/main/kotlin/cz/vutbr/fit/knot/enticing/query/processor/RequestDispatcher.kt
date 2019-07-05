package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.response.SearchResult
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.query.processor.request.ServerInfo

interface RequestDispatcher {
    operator fun invoke(searchQuery: SearchQuery, serverInfo: ServerInfo): MResult<SearchResult>
}