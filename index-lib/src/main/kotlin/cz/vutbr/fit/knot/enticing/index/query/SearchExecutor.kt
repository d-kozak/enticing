package cz.vutbr.fit.knot.enticing.index.query

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CollectionConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.filterBy
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
import kotlin.math.min

@Cleanup("put into configuration?")
const val SNIPPET_SIZE = 50

private val log = LoggerFactory.getLogger(SearchExecutor::class.java)

fun initSearchExecutor(corpusConfiguration: CorpusConfiguration, collectionConfig: CollectionConfiguration): SearchExecutor {
    log.info("initializing with config $corpusConfiguration,$collectionConfig")
    val collection = Mg4jCompositeDocumentCollection(corpusConfiguration, collectionConfig.mg4jFiles)
    val factory = Mg4jDocumentFactory(corpusConfiguration)

    val indexDir = collectionConfig.indexDirectory

    val indexMap = Object2ReferenceLinkedOpenHashMap<String, Index>()
    val termProcessors = Object2ObjectOpenHashMap<String, TermProcessor>()
    val index2weight = Reference2DoubleOpenHashMap<Index>()
    for (index in corpusConfiguration.indexes.values.toList()) {
        val mg4jIndex = Index.getInstance(indexDir.resolve("${corpusConfiguration.corpusName}-${index.name}").path)
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

    return SearchExecutor(collectionConfig.name, collection, engine, corpusConfiguration)
}

internal typealias Mg4jSearchResult = DocumentScoreInfo<Reference2ObjectMap<Index, Array<SelectedInterval>>>

/**
 * Interface of the underlying mg4j indexing library, performs requests and processes results
 */
@Cleanup("Refactor into multiple classes/functions in different files")
@WhatIf("? decouple from mg4j for easier and faster testing ?")
class SearchExecutor internal constructor(
        val collectionName: String,
        private val collection: Mg4jCompositeDocumentCollection,
        private val engine: QueryEngine,
        private val corpusConfiguration: CorpusConfiguration
) {

    private val log: Logger = LoggerFactory.getLogger(SearchExecutor::class.java)

    fun query(query: SearchQuery, offset: Offset = Offset(0, 0)): IndexServer.CollectionSearchResult {
        log.info("Executing query $query")
        val resultList = ObjectArrayList<Mg4jSearchResult>()
        val (documentOffset, matchOffset) = offset

        val processed = engine.process(query.query, documentOffset, query.snippetCount, resultList)
        log.info("Processed $processed documents")

        val config = corpusConfiguration.filterBy(query.metadata, query.defaultIndex)

        val matched = mutableListOf<IndexServer.Snippet>()
        for ((i, result) in resultList.withIndex()) {
            val (matchList, nextSnippet) = processDocument(query, result, config, query.snippetCount - matched.size, if (i == 0) matchOffset else 0)
            matched.addAll(matchList)
            if (matched.size >= query.snippetCount || nextSnippet != null) {
                val finalOffset = when {
                    nextSnippet != null -> Offset(result.document.toInt(), nextSnippet)
                    i != resultList.size - 1 -> Offset(resultList[i + 1].document.toInt(), 0)
                    else -> null
                }
                return IndexServer.CollectionSearchResult(matched, finalOffset)
            }
        }
        return IndexServer.CollectionSearchResult(matched, null)
    }

    internal fun processDocument(query: SearchQuery, result: Mg4jSearchResult, config: CorpusConfiguration, wantedSnippets: Int, offset: Int): Pair<List<IndexServer.Snippet>, Int?> {
        val matched = mutableListOf<IndexServer.Snippet>()

        val document = collection.document(result.document) as Mg4jDocument

        val allIntervals = generateSnippetIntervals(result.info.values, document.size())
        val wantedIntervals = allIntervals.subList(offset, min(wantedSnippets, allIntervals.size))


        for ((interval, subIntervals) in wantedIntervals) {
            val (from, to) = interval
            val content = document.loadSnippetPartsFields(from, to + 1, config)

            val payload = if (content.elements.isNotEmpty()) createPayload(query, content, subIntervals, corpusConfiguration)
            else {
                log.warn("loaded empty document, why? ${document.title()}")
                createPayload(query, content, emptyList(), corpusConfiguration)
            }

            val match = IndexServer.Snippet(
                    collectionName,
                    result.document,
                    from,
                    to - from,
                    document.uri().toString(),
                    document.title().toString(),
                    payload,
                    canExtend = from > 0 || to < document.size()
            )
            matched.add(match)
            log.info("Found match $match")
        }

        val nextOffset = if (offset + wantedIntervals.size < allIntervals.size) offset + wantedIntervals.size + 1 else null
        return matched to nextOffset
    }


    fun extendSnippet(query: IndexServer.ContextExtensionQuery): SnippetExtension {
        val document = collection.document(query.docId.toLong()) as Mg4jDocument
        val (prefix, suffix) = computeExtensionIntervals(left = query.location, right = query.location + query.size, extension = query.extension, documentSize = document.size())

        val filteredConfig = corpusConfiguration.filterBy(query.metadata, query.defaultIndex)

        val prefixPayload = createPayload(query, document.loadSnippetPartsFields(prefix, filteredConfig), emptyList(), corpusConfiguration) as Payload.FullResponse
        val suffixPayload = createPayload(query, document.loadSnippetPartsFields(suffix, filteredConfig), emptyList(), corpusConfiguration) as Payload.FullResponse

        return SnippetExtension(
                prefixPayload,
                suffixPayload,
                canExtend = document.size() > prefix.size + query.size + suffix.size
        )
    }

    fun getDocument(query: IndexServer.DocumentQuery): IndexServer.FullDocument {
        val document = collection.document(query.documentId.toLong()) as Mg4jDocument

        val filteredConfig = corpusConfiguration.filterBy(query.metadata, query.defaultIndex)

        val content = document.loadSnippetPartsFields(filteredConfig = filteredConfig)

        val payload = createPayload(query, content, emptyList(), corpusConfiguration) as Payload.FullResponse
        return IndexServer.FullDocument(
                document.title().toString(),
                document.uri().toString(),
                payload
        )
    }
}


/**
 * Compute the intervals for prefix and suffix for snippet extension
 */
internal fun computeExtensionIntervals(left: Int, right: Int, extension: Int, documentSize: Int): Pair<Interval, Interval> {
    checkPreconditions(documentSize, left, right, extension)
    val maxPrefixSize = left
    val maxSuffixSize = documentSize - right - 1
    val (prefixSize, suffixSize) = computePrefixAndSuffixSize(extension, maxPrefixSize, maxSuffixSize)


    // zero check necessary, because there is no factory supporting empty interval, grrr
    val leftInterval = if (prefixSize > 0) Interval.valueOf(left - prefixSize, left - 1) else Intervals.EMPTY_INTERVAL
    val rightInterval = if (suffixSize > 0) Interval.valueOf(right + 1, right + suffixSize) else Intervals.EMPTY_INTERVAL
    checkPostconditions(leftInterval, maxPrefixSize, rightInterval, maxSuffixSize, extension)
    return leftInterval to rightInterval
}

private fun checkPreconditions(documentSize: Int, left: Int, right: Int, extension: Int) {
    val documentRange = 0 until documentSize
    require(documentSize > 0) { "Document size should be bigger than zero, was $documentSize" }
    require(left in documentRange) { "Left boundary should be within $documentRange, was $left" }
    require(right in documentRange) { "Right boundary should be be within $documentRange, was $right" }
    require(extension != 0 && extension in documentRange) { "Extension should be within 1..${documentSize - 1}, was $extension" }
    require(left <= right) { "Left should be <= to right, was $left, $right" }
}

private fun checkPostconditions(leftInterval: Interval, maxPrefixSize: Int, rightInterval: Interval, maxSuffixSize: Int, extension: Int) {
    require(leftInterval.size in 0..maxPrefixSize) { "prefix size should be within 0..${maxPrefixSize - 1}, was ${leftInterval.size}" }
    require(rightInterval.size in 0..maxSuffixSize) { "suffix size should be within 0..${maxSuffixSize - 1}, was ${rightInterval.size}" }
    require(leftInterval.size + rightInterval.size <= extension) { "prefix + suffix <= extension,was ${leftInterval.size},${rightInterval.size},$extension" }
}

@Cleanup("This is ugly and should be refactored somehow, hopefully there is an easier way to express this operation")
private fun computePrefixAndSuffixSize(extension: Int, maxPrefixSize: Int, maxSuffixSize: Int): Pair<Int, Int> {
    var prefixSize = min(extension / 2, maxPrefixSize)
    var suffixSize = min(extension / 2, maxSuffixSize)

    loop@ while (prefixSize + suffixSize < extension) {
        val remaining = extension - prefixSize - suffixSize
        when {
            prefixSize == maxPrefixSize -> {
                suffixSize = min(suffixSize + remaining, maxSuffixSize)
                break@loop
            }
            suffixSize == maxSuffixSize -> {
                prefixSize = min(prefixSize + remaining, maxPrefixSize)
                break@loop
            }
            else -> {
                prefixSize = min(suffixSize + remaining / 2, maxPrefixSize)
                suffixSize = min(suffixSize + remaining / 2, maxSuffixSize)

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
    return prefixSize to suffixSize
}