package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.DocumentId
import cz.vutbr.fit.knot.enticing.index.boundary.DocumentResult
import cz.vutbr.fit.knot.enticing.index.boundary.QueryResult
import cz.vutbr.fit.knot.enticing.index.boundary.SearchEngine
import it.unimi.di.big.mg4j.index.Index
import it.unimi.di.big.mg4j.query.QueryEngine
import it.unimi.di.big.mg4j.query.SelectedInterval
import it.unimi.di.big.mg4j.search.score.DocumentScoreInfo
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import org.slf4j.LoggerFactory


fun SelectedInterval.toEnticingInterval() = Interval.valueOf(this.interval.left, this.interval.right)

private typealias Mg4jSearchResult = DocumentScoreInfo<Reference2ObjectMap<Index, Array<SelectedInterval>>>

class Mg4jSearchEngine(
        private val collection: Mg4jCompositeDocumentCollection,
        private val engine: QueryEngine
) : SearchEngine {

    private val log = LoggerFactory.getLogger(Mg4jSearchEngine::class.java)

    override fun search(query: String, documentCount: Int, offset: Int): QueryResult {
        log.info("Executing query $query")
        val resultList = ObjectArrayList<Mg4jSearchResult>()
        val processed = engine.process(query, offset, documentCount, resultList)
        log.info("Processed $processed documents")

        val documentResults = resultList.map {
            val intervals = it.info.values.map { it.map { it.toEnticingInterval() } }
            DocumentResult(it.document.toInt(), intervals)
        }
        return QueryResult(documentResults, processed)
    }

    override fun loadDocument(id: DocumentId): Mg4jDocument = collection.document(id.toLong())
}