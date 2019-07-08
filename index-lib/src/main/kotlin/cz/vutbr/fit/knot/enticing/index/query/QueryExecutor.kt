package cz.vutbr.fit.knot.enticing.index.query

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.dto.query.Offset
import cz.vutbr.fit.knot.enticing.dto.query.ResponseFormat
import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.response.*
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jDocument
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jDocumentFactory
import it.unimi.di.big.mg4j.document.Document
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

const val MAX_SNIPPET_SIZE = 50

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

        val matched = mutableListOf<Match>()
        for ((i, result) in resultList.withIndex()) {
            val (matchList, nextSnippet) = processDocument(query, result, query.snippetCount - matched.size, if (i == 0) matchOffset else null)
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

    internal fun processDocument(query: SearchQuery, result: Mg4jSearchResult, wantedSnippets: Int, offset: Int? = null): Pair<List<Match>, Int?> {
        val matched = mutableListOf<Match>()
        val defaultIndex = engine.indexMap[query.defaultIndex]
                ?: throw IllegalArgumentException("Index ${query.defaultIndex} not found")

        val document = collection.document(result.document) as Mg4jDocument
        val scores = getScoresForIndex(result, defaultIndex)

        for (score in scores) {
            val (left, right) = score.interval
            // todo how big the prefix and suffix should be ( and if they are necessary at all) depends on the size of the matched region
            val prefix = Math.max(left - 5, 0)
            val suffix = right + 5

            val content = document.loadSnippetPartsFields(prefix, suffix)
            val words = content[query.defaultIndex]


            val payload = when (query.responseFormat) {
                ResponseFormat.HTML -> processAsHtml(query, document, words, left - prefix, right - prefix)
                ResponseFormat.JSON -> processAsJson(query, document, words, left - prefix, right - prefix)
            }

            val match = Match(
                    collectionName,
                    result.document,
                    left,
                    right - left,
                    document.uri().toString(),
                    document.title().toString(),
                    payload,
                    canExtend = prefix > 0 || suffix < words.size - 1
            )
            log.info("Found match $match")
            matched.add(match)
            if (matched.size >= wantedSnippets) {
                return matched to matched.size + 1
            }
        }

        return matched to null
    }


    private fun getScoresForIndex(
            result: DocumentScoreInfo<Reference2ObjectMap<Index, Array<SelectedInterval>>>,
            index: Index
    ): List<SelectedInterval> = result.info[index]?.toList() ?: listOf<SelectedInterval>().also {
        log.warn("No results for index $index")
    }
}


fun processAsJson(query: SearchQuery, document: Document, words: List<String>, left: Int, right: Int): Payload.Snippet.Json {
    val prefixText = words.subList(0, left).joinToString(separator = " ")
    val matchedText = words.subList(left, right).joinToString(separator = " ")
    val suffixText = words.subList(right + 1, words.size).joinToString(separator = " ")

    return Payload.Snippet.Json(AnnotatedText(
            prefixText + matchedText + suffixText,
            emptyMap(),
            emptyList(),
            listOf(QueryMapping(
                    textIndex = MatchedRegion(prefixText.length, matchedText.length),
                    queryIndex = MatchedRegion(0, query.query.length)
            ))
    ))
}

fun processAsHtml(query: SearchQuery, document: Document, words: List<String>, left: Int, right: Int): Payload.Snippet.Html {
    if (words.isEmpty()) return Payload.Snippet.Html("NULL")

    val prefixText = words.subList(0, Math.min(left, words.size)).joinToString(separator = " ")
    val matchedText = words.subList(Math.min(left, words.size - 1), Math.min(right, words.size)).joinToString(separator = " ")
    val suffixText = words.subList(Math.min(right, words.size - 1), words.size).joinToString(separator = " ")

    return Payload.Snippet.Html("$prefixText<b>$matchedText</b>$suffixText")
}


operator fun Interval.component1() = left
operator fun Interval.component2() = right + 1