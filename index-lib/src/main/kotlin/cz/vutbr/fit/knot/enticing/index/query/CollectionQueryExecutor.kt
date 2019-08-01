package cz.vutbr.fit.knot.enticing.index.query

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.Offset
import cz.vutbr.fit.knot.enticing.dto.RequestData
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.query.processor.QueryExecutor

/**
 * QueryExecutor which handles SearchExecutors for multiple collections and delegates requests to them accordingly
 */
class CollectionQueryExecutor(
        private val executors: Map<String, SearchExecutor>
) : QueryExecutor<SearchQuery, Offset, IndexServer.CollectionSearchResult> {
    override suspend fun invoke(searchQuery: SearchQuery, requestData: RequestData<Offset>): MResult<IndexServer.CollectionSearchResult> = MResult.runCatching {
        val executor = executors[requestData.address]
                ?: throw IllegalArgumentException("Unknown executor for collection ${requestData.address}")
        executor.query(searchQuery, requestData.offset ?: Offset(0, 0))
    }
}