package cz.vutbr.fit.knot.enticing.index.server.service

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.index.query.CollectionQueryExecutor
import cz.vutbr.fit.knot.enticing.index.query.SearchExecutor
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class QueryService(
        private val searchExecutors: Map<String, SearchExecutor>,
        private val indexClientConfig: IndexClientConfig
) {

    private val log = LoggerFactory.getLogger(QueryService::class.java)

    val queryDispatcher = QueryDispatcher(CollectionQueryExecutor(searchExecutors))

    fun processQuery(query: SearchQuery): IndexServer.SearchResult {
        val requestData = if(query.offset != null) query.offset!!.map { (collection, offset) -> CollectionRequestData(collection, offset) }
        else searchExecutors.keys.map { CollectionRequestData(it, Offset(0, 0)) }
        log.info("Executing query $query with requestData $requestData")
        return flatten(queryDispatcher.dispatchQuery(query, requestData))
    }

    fun extendContext(query: IndexServer.ContextExtensionQuery) = searchExecutors[query.collection]?.extendSnippet(query)
            ?: throw IllegalArgumentException("Unknown collection ${query.collection}")

    fun getDocument(query: IndexServer.DocumentQuery) = searchExecutors[query.collection]?.getDocument(query)
            ?: throw IllegalArgumentException("Unknown collection ${query.collection}")

    fun loadCorpusFormat(): CorpusFormat = indexClientConfig.corpusConfiguration.toCorpusFormat()
}

internal fun flatten(result: Map<String, List<MResult<IndexServer.CollectionSearchResult>>>): IndexServer.SearchResult {
    val matched = mutableListOf<IndexServer.Snippet>()
    val errors = mutableMapOf<CollectionName, ErrorMessage>()

    for ((collectionName, collectionResults) in result) {
        for(collectionResult in collectionResults){
            if(collectionResult.isSuccess){
                matched.addAll(collectionResult.value.matched)
            } else {
                errors[collectionName] = "${collectionResult.exception::class.simpleName}:${collectionResult.exception.message}"
            }
        }
    }

    val offset: Map<CollectionName, Offset> =
            result.map { (collectionName, collectionResults) -> collectionResults.findLast { it.isSuccess && it.value.offset != null }?.value?.offset?.let { collectionName to it } }
                    .filterNotNull()
                    .toMap()

    return IndexServer.SearchResult(matched, offset, errors)
}