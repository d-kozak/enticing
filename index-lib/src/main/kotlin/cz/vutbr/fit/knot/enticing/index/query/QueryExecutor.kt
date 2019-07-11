package cz.vutbr.fit.knot.enticing.index.query

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.filterBy
import cz.vutbr.fit.knot.enticing.dto.query.Offset
import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.response.SearchResult
import cz.vutbr.fit.knot.enticing.dto.response.Snippet
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jDocument
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jDocumentFactory
import cz.vutbr.fit.knot.enticing.index.payload.createPayload
import it.unimi.di.big.mg4j.index.Index
import it.unimi.di.big.mg4j.index.TermProcessor
import it.unimi.di.big.mg4j.query.IntervalSelector
import it.unimi.di.big.mg4j.query.Query
import it.unimi.di.big.mg4j.query.QueryEngine
import it.unimi.di.big.mg4j.query.SelectedInterval
import it.unimi.di.big.mg4j.query.parser.SimpleParser
import it.unimi.di.big.mg4j.search.DocumentIteratorBuilderVisitor
import it.unimi.di.big.mg4j.search.score.DocumentScoreInfo
import it.unimi.dsi.fastutil.objects.*
import it.unimi.dsi.util.Interval
import org.slf4j.Logger
import org.slf4j.LoggerFactory


// todo put into the configuration?
const val SNIPPET_SIZE = 50

fun initQueryExecutor(config: IndexClientConfig): QueryExecutor {
    val collection = Mg4jCompositeDocumentCollection(config.corpusConfiguration, config.mg4jFiles)
    val factory = Mg4jDocumentFactory(config.corpusConfiguration)

    val indexDir = config.indexDirectory

    val indexMap = Object2ReferenceLinkedOpenHashMap<String, Index>()
    val termProcessors = Object2ObjectOpenHashMap<String, TermProcessor>()
    val index2weight = Reference2DoubleOpenHashMap<Index>()
    for (index in config.indexes) {
        val mg4jIndex = Index.getInstance(indexDir.resolve("${config.corpusConfiguration.corpusName}-${index.name}").path)
        requireNotNull(mg4jIndex.field)
        indexMap[mg4jIndex.field] = mg4jIndex
        termProcessors[mg4jIndex.field] = mg4jIndex.termProcessor
        index2weight[mg4jIndex] = 1.0
    }

    val defaultIndex = "token"
    val engine = QueryEngine(
            SimpleParser(indexMap.keys, defaultIndex, termProcessors),
            DocumentIteratorBuilderVisitor(indexMap, indexMap[defaultIndex], Query.MAX_STEMMING),
            indexMap
    )
    engine.setWeights(index2weight)
    engine.intervalSelector = IntervalSelector(Integer.MAX_VALUE, Integer.MAX_VALUE)
    engine.multiplex = false

    return QueryExecutor("name", collection, engine, config.corpusConfiguration)
}

internal typealias Mg4jSearchResult = DocumentScoreInfo<Reference2ObjectMap<Index, Array<SelectedInterval>>>

class QueryExecutor internal constructor(
        private val collectionName: String,
        private val collection: Mg4jCompositeDocumentCollection,
        private val engine: QueryEngine,
        private val corpusConfiguration: CorpusConfiguration
) {

    private val log: Logger = LoggerFactory.getLogger(QueryExecutor::class.java)

    fun query(query: SearchQuery): MResult<SearchResult> = MResult.runCatching {
        log.info("Executing query $query")
        val resultList = ObjectArrayList<Mg4jSearchResult>()
        val (documentOffset, matchOffset) = query.offset

        val processed = engine.process(query.query, documentOffset, query.snippetCount, resultList)
        log.info("Processed $processed documents")

        val config = corpusConfiguration.filterBy(query.metadata, query.defaultIndex)

        val matched = mutableListOf<Snippet>()
        for ((i, result) in resultList.withIndex()) {
            val (matchList, nextSnippet) = processDocument(query, result, config, query.snippetCount - matched.size, if (i == 0) matchOffset else 0)
            matched.addAll(matchList)
            if (matched.size >= query.snippetCount || nextSnippet != null) {
                val offset = when {
                    nextSnippet != null -> Offset(result.document.toInt(), nextSnippet)
                    i != resultList.size - 1 -> Offset(resultList[i + 1].document.toInt(), 0)
                    else -> null
                }
                return@runCatching SearchResult(matched, offset)
            }
        }
        return@runCatching SearchResult(matched, null)
    }

    internal fun processDocument(query: SearchQuery, result: Mg4jSearchResult, config: CorpusConfiguration, wantedSnippets: Int, offset: Int): Pair<List<Snippet>, Int?> {
        val matched = mutableListOf<Snippet>()
        val defaultIndex = engine.indexMap[query.defaultIndex]
                ?: throw IllegalArgumentException("Index ${query.defaultIndex} not found")

        val document = collection.document(result.document) as Mg4jDocument
        val scores = getScoresForIndex(result, defaultIndex)

        var lastProcessedIndex = -1

        for ((i, score) in scores.withIndex()) {
            val (left, right) = score.clampRight(score.left + SNIPPET_SIZE)
            if (right <= lastProcessedIndex) continue

            val relevantScores = scores
                    .asSequence()
                    .filterIndexed { j, _ -> j >= i }
                    .filter { (it, _) -> it < left + SNIPPET_SIZE }
                    .map { it.clampRight(left + SNIPPET_SIZE) }
                    .toList()

            val content = document.loadSnippetPartsFields(left, left + SNIPPET_SIZE, config)

            val payload = createPayload(query, content, relevantScores)

            val match = Snippet(
                    null,
                    collectionName,
                    result.document,
                    left,
                    right - left,
                    document.uri().toString(),
                    document.title().toString(),
                    payload,
                    canExtend = left > 0 || left + SNIPPET_SIZE < document.size()
            )
            matched.add(match)
            log.info("Found match $match")
            if (matched.size >= wantedSnippets) {
                return matched to matched.size + 1
            }

            lastProcessedIndex = if (relevantScores.isNotEmpty()) relevantScores.last().right else right
        }

        return matched to null
    }


    private fun getScoresForIndex(
            result: DocumentScoreInfo<Reference2ObjectMap<Index, Array<SelectedInterval>>>,
            index: Index
    ): List<Interval> = result.info[index]?.asSequence()?.map { it.interval }?.toList() ?: listOf<Interval>().also {
        log.warn("No results for index $index")
    }
}
