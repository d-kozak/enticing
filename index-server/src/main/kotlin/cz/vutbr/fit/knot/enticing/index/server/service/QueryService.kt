package cz.vutbr.fit.knot.enticing.index.server.service

import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.index.query.QueryExecutor
import org.springframework.stereotype.Service


@Service
class QueryService(
        private val queryExecutor: QueryExecutor
) {

    fun processQuery(query: SearchQuery) = queryExecutor.query(query)
}