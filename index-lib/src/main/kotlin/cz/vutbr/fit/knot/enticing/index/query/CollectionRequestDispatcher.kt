package cz.vutbr.fit.knot.enticing.index.query

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.Offset
import cz.vutbr.fit.knot.enticing.dto.RequestData
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.query.processor.RequestDispatcher

class CollectionRequestDispatcher(
        private val executors: Map<String, QueryExecutor>
) : RequestDispatcher<CollectionRequestData> {
    override suspend fun invoke(searchQuery: SearchQuery, requestData: CollectionRequestData): MResult<IndexServer.SearchResult> = MResult.runCatching {
        val executor = executors[requestData.address]
                ?: throw IllegalArgumentException("Unkown executor for collection ${requestData.address}")
        executor.query(searchQuery).unwrap()
    }

    override fun createRequestData(address: String, offset: Offset): CollectionRequestData = CollectionRequestData(address, offset)
}


data class CollectionRequestData(
        override val address: String,
        override val offset: Offset
) : RequestData