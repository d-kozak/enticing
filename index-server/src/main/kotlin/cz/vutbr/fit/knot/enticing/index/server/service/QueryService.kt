package cz.vutbr.fit.knot.enticing.index.server.service

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.index.collection.manager.CollectionManager
import cz.vutbr.fit.knot.enticing.index.collection.manager.CollectionQueryExecutor
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class QueryService(
        private val collectionManagers: Map<String, CollectionManager>,
        private val metadataConfiguration: MetadataConfiguration,
        private val eqlCompiler: EqlCompiler
) {

    private val log = LoggerFactory.getLogger(QueryService::class.java)

    val queryDispatcher = QueryDispatcher(CollectionQueryExecutor(collectionManagers))

    fun processQuery(query: SearchQuery): IndexServer.IndexResultList {
        query.eqlAst = eqlCompiler.parseOrFail(query.query, metadataConfiguration)

        val requestData = if (query.offset != null) query.offset!!.map { (collection, offset) -> CollectionRequestData(collection, offset) }
        else collectionManagers.keys.map { CollectionRequestData(it, Offset(0, 0)) }

        log.info("Executing query $query with requestData $requestData")
        return flatten(queryDispatcher.dispatchQuery(query, requestData))
    }

    fun extendContext(query: IndexServer.ContextExtensionQuery) = collectionManagers[query.collection]?.extendSnippet(query)
            ?: throw IllegalArgumentException("Unknown collection ${query.collection}, known collections are ${collectionManagers.keys}")

    fun getDocument(query: IndexServer.DocumentQuery) = collectionManagers[query.collection]?.getDocument(query)
            ?: throw IllegalArgumentException("Unknown collection ${query.collection}, known collections are ${collectionManagers.keys}")

    fun loadCorpusFormat(): CorpusFormat = metadataConfiguration.toCorpusFormat()

    fun getRawDocument(collection: String, documentId: Int, from: Int, to: Int): String = collectionManagers[collection]?.getRawDocument(documentId, from, to)
            ?: throw IllegalArgumentException("Unknown collection $collection, known collections are ${collectionManagers.keys}")

}

internal fun flatten(resultList: Map<String, List<MResult<IndexServer.CollectionResultList>>>): IndexServer.IndexResultList {
    val matched = mutableListOf<IndexServer.SearchResult>()
    val errors = mutableMapOf<CollectionName, ErrorMessage>()

    for ((collectionName, collectionResults) in resultList) {
        for (collectionResult in collectionResults) {
            if (collectionResult.isSuccess) {
                matched.addAll(collectionResult.value.searchResults)
            } else {
                errors[collectionName] = "${collectionResult.exception::class.simpleName}:${collectionResult.exception.message}"
                collectionResult.exception.printStackTrace()
            }
        }
    }

    val offset: Map<CollectionName, Offset> =
            resultList.map { (collectionName, collectionResults) -> collectionResults.findLast { it.isSuccess && it.value.offset != null }?.value?.offset?.let { collectionName to it } }
                    .filterNotNull()
                    .toMap()

    return IndexServer.IndexResultList(matched, offset, errors)
}