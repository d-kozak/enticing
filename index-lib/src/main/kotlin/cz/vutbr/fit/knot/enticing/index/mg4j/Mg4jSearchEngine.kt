package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.DocumentId
import cz.vutbr.fit.knot.enticing.index.boundary.DocumentResult
import cz.vutbr.fit.knot.enticing.index.boundary.QueryResult
import cz.vutbr.fit.knot.enticing.index.boundary.SearchEngine
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import it.unimi.di.big.mg4j.index.Index
import it.unimi.di.big.mg4j.query.QueryEngine
import it.unimi.di.big.mg4j.query.SelectedInterval
import it.unimi.di.big.mg4j.search.score.DocumentScoreInfo
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap


fun SelectedInterval.toEnticingInterval() = Interval.valueOf(this.interval.left, this.interval.right)

private typealias Mg4jSearchResult = DocumentScoreInfo<Reference2ObjectMap<Index, Array<SelectedInterval>>>

class Mg4jSearchEngine(
        private val collection: Mg4jCompositeDocumentCollection,
        private val engineFactory: QueryEngine,
        loggerFactory: LoggerFactory
) : SearchEngine {

    private val logger = loggerFactory.logger { }

    override fun getRawDocument(id: DocumentId, from: Int, to: Int): String = collection.getRawDocument(id.toLong(), from, to)

    override fun search(query: String, documentCount: Int, offset: Int): QueryResult {
        val engine = engineFactory.copy()
        logger.info("Executing query $query")
        val resultList = ObjectArrayList<Mg4jSearchResult>()
        val relevantDocuments = engine.process(query, offset, documentCount, resultList)
        logger.info("Processed $relevantDocuments relevants documents")

        val documentResults = resultList.map {
            val intervals = it.info.values.map { it.map { it.toEnticingInterval() } }
            DocumentResult(it.document.toInt(), intervals)
        }
        return QueryResult(documentResults, relevantDocuments)
    }

    override fun loadDocument(id: DocumentId): Mg4jDocument = collection.document(id.toLong())
}