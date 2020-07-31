package cz.vutbr.fit.knot.enticing.index.server.service

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.index.collection.manager.CollectionManager
import cz.vutbr.fit.knot.enticing.index.collection.manager.CollectionQueryExecutor
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.flattenResults
import org.springframework.stereotype.Service


/**
 * Takes care of dispatching search requests to individual collection managers
 */
@Service
class QueryService(
        private val collectionManagers: Map<String, CollectionManager>,
        private val metadataConfiguration: MetadataConfiguration,
        private val eqlCompiler: EqlCompiler,
        loggerFactory: LoggerFactory
) {

    private val logger = loggerFactory.logger { }

    /**
     * Dispatches queries to collection managers
     */
    private val queryDispatcher = QueryDispatcher(CollectionQueryExecutor(collectionManagers), ComponentType.INDEX_SERVER, loggerFactory)

    fun processQuery(query: SearchQuery): IndexServer.IndexResultList {
        query.eqlAst = eqlCompiler.parseOrFail(query.query, metadataConfiguration)

        val requestData = if (query.offset != null) query.offset!!.map { (collection, offset) -> CollectionRequestData(collection, offset) }
        else collectionManagers.keys.map { CollectionRequestData(it, Offset(0, 0)) }

        logger.info("Executing query $query with requestData $requestData")
        return queryDispatcher.dispatchQuery(query, requestData)
                .flattenResults()
    }

    fun extendContext(query: IndexServer.ContextExtensionQuery) = collectionManagers[query.collection]?.extendSnippet(query)
            ?: throw IllegalArgumentException("Unknown collection ${query.collection}, known collections are ${collectionManagers.keys}")

    fun getDocument(query: IndexServer.DocumentQuery) = collectionManagers[query.collection]?.getDocument(query)
            ?: throw IllegalArgumentException("Unknown collection ${query.collection}, known collections are ${collectionManagers.keys}")

    fun loadCorpusFormat(): CorpusFormat = metadataConfiguration.toCorpusFormat()

    fun getRawDocument(collection: String, documentId: Int, from: Int, to: Int): String = collectionManagers[collection]?.getRawDocument(documentId, from, to)
            ?: throw IllegalArgumentException("Unknown collection $collection, known collections are ${collectionManagers.keys}")

}

