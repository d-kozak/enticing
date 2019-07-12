package cz.vutbr.fit.knot.enticing.index.server.service

import cz.vutbr.fit.knot.enticing.dto.CorpusFormat
import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.dto.toCorpusFormat
import cz.vutbr.fit.knot.enticing.index.query.QueryExecutor
import org.springframework.stereotype.Service


@Service
class QueryService(
        private val queryExecutor: QueryExecutor,
        private val indexClientConfig: IndexClientConfig
) {

    fun processQuery(query: SearchQuery) = queryExecutor.query(query).unwrap()

    fun extendContext(query: IndexServer.ContextExtensionQuery) = queryExecutor.extendSnippet(query).unwrap()

    fun getDocument(query: IndexServer.DocumentQuery) = queryExecutor.getDocument(query).unwrap()

    fun loadCorpusFormat(): CorpusFormat = indexClientConfig.corpusConfiguration.toCorpusFormat()
}