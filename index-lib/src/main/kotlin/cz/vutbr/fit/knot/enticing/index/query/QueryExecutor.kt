package cz.vutbr.fit.knot.enticing.index.query

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.Offset
import cz.vutbr.fit.knot.enticing.dto.Payload
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.filterBy
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
import it.unimi.dsi.util.Intervals
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Cleanup("put into configuration?")
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

    fun query(query: SearchQuery): MResult<IndexServer.SearchResult> = MResult.runCatching {
        log.info("Executing query $query")
        val resultList = ObjectArrayList<Mg4jSearchResult>()
        val (documentOffset, matchOffset) = query.offset

        val processed = engine.process(query.query, documentOffset, query.snippetCount, resultList)
        log.info("Processed $processed documents")

        val config = corpusConfiguration.filterBy(query.metadata, query.defaultIndex)

        val matched = mutableListOf<IndexServer.Snippet>()
        for ((i, result) in resultList.withIndex()) {
            val (matchList, nextSnippet) = processDocument(query, result, config, query.snippetCount - matched.size, if (i == 0) matchOffset else 0)
            matched.addAll(matchList)
            if (matched.size >= query.snippetCount || nextSnippet != null) {
                val offset = when {
                    nextSnippet != null -> Offset(result.document.toInt(), nextSnippet)
                    i != resultList.size - 1 -> Offset(resultList[i + 1].document.toInt(), 0)
                    else -> null
                }
                return@runCatching IndexServer.SearchResult(matched, offset)
            }
        }
        return@runCatching IndexServer.SearchResult(matched, null)
    }

    @Incomplete("Better snippet creation algorithm needed")
    @Incomplete("Offset is not being used")
    internal fun processDocument(query: SearchQuery, result: Mg4jSearchResult, config: CorpusConfiguration, wantedSnippets: Int, offset: Int): Pair<List<IndexServer.Snippet>, Int?> {
        val matched = mutableListOf<IndexServer.Snippet>()
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

            val match = IndexServer.Snippet(
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

    @Incomplete("not implemented yet, returns dummy data")
    fun extendSnippet(query: IndexServer.ContextExtensionQuery): MResult<IndexServer.SnippetExtension> = MResult.runCatching {
        val document = collection.document(query.docId.toLong()) as Mg4jDocument


        IndexServer.SnippetExtension(
                Payload.FullResponse.Html("null"),
                Payload.FullResponse.Html("null"),
                false
        )
    }

    fun getDocument(query: IndexServer.DocumentQuery): MResult<IndexServer.FullDocument> = MResult.runCatching {
        val document = collection.document(query.documentId.toLong()) as Mg4jDocument

        val filteredConfig = corpusConfiguration.filterBy(query.metadata, query.defaultIndex)

        val content = document.loadSnippetPartsFields(filteredConfig = filteredConfig)

        val payload = createPayload(query, content, emptyList()) as Payload.FullResponse
        IndexServer.FullDocument(
                document.title().toString(),
                document.uri().toString(),
                payload
        )
    }
}

/**
 * Compute the size of prefix and suffix for snippet extension
 */
internal fun computeExtensionIntervals(left: Int, right: Int, extension: Int, documentSize: Int): Pair<Interval, Interval> {
    val documentRange = 0 until documentSize
    require(documentSize > 0) { "Document size should be bigger than zero, was $documentSize" }
    require(left in documentRange) { "Left boundary should be within $documentRange was $left" }
    require(right in documentRange) { "Right boundary should be be within $documentRange, was $right" }
    require(extension != 0 && extension in documentRange) { "Extension should be within $documentRange, was $extension" }
    require(left <= right) { "Left should be <= to right, was $left, $right" }

    val maxPrefixSize = left
    val maxSuffixSize = documentSize - right - 1
    var prefixSize = Math.min(extension / 2, maxPrefixSize)
    var suffixSize = Math.min(extension / 2, maxSuffixSize)

    loop@ while (prefixSize + suffixSize < extension) {
        val remaining = extension - prefixSize - suffixSize
        when {
            prefixSize == maxPrefixSize -> {
                suffixSize = Math.min(suffixSize + remaining, maxSuffixSize)
                break@loop
            }
            suffixSize == maxSuffixSize -> {
                prefixSize = Math.min(prefixSize + remaining, maxPrefixSize)
                break@loop
            }
            else -> {
                prefixSize = Math.min(suffixSize + remaining / 2, maxPrefixSize)
                suffixSize = Math.min(suffixSize + remaining / 2, maxSuffixSize)

                if (extension % 2 == 1) {
                    if (prefixSize < maxPrefixSize)
                        prefixSize++
                    else if (suffixSize < maxSuffixSize)
                        suffixSize++
                    break@loop
                }
            }
        }
    }

    require(prefixSize in 0..maxPrefixSize) { "prefix size should be within 0..${maxPrefixSize - 1}, was $prefixSize" }
    require(suffixSize in 0..maxSuffixSize) { "suffix size should be within 0..${maxSuffixSize - 1}, was $suffixSize" }
    require(prefixSize + suffixSize <= extension) { "prefix + suffix <= extension,was $prefixSize,$suffixSize,$extension" }


    // zero check necessary, because there is no factory supporting empty interval, grrr
    val leftInterval = if (prefixSize > 0) Interval.valueOf(left - prefixSize, left - 1) else Intervals.EMPTY_INTERVAL
    val rightInterval = if (suffixSize > 0) Interval.valueOf(right + 1, right + suffixSize) else Intervals.EMPTY_INTERVAL

    require(leftInterval.size in 0..maxPrefixSize) { "prefix size should be within 0..${maxPrefixSize - 1}, was ${leftInterval.size}" }
    require(rightInterval.size in 0..maxSuffixSize) { "suffix size should be within 0..${maxSuffixSize - 1}, was ${rightInterval.size}" }
    require(leftInterval.size + rightInterval.size <= extension) { "prefix + suffix <= extension,was $leftInterval.size,$rightInterval.size,$extension" }

    return leftInterval to rightInterval
}