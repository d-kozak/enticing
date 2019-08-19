package cz.vutbr.fit.knot.enticing.index.collection.manager

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CollectionConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.dto.interval.findEnclosingInterval
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.EqlCompiler
import cz.vutbr.fit.knot.enticing.index.boundary.PostProcessor
import cz.vutbr.fit.knot.enticing.index.boundary.ResultCreator
import cz.vutbr.fit.knot.enticing.index.boundary.SearchEngine
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jDocumentFactory
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jSearchEngine
import it.unimi.di.big.mg4j.index.Index
import it.unimi.di.big.mg4j.index.TermProcessor
import it.unimi.di.big.mg4j.query.IntervalSelector
import it.unimi.di.big.mg4j.query.Query
import it.unimi.di.big.mg4j.query.QueryEngine
import it.unimi.di.big.mg4j.query.parser.SimpleParser
import it.unimi.di.big.mg4j.search.DocumentIteratorBuilderVisitor
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2ReferenceLinkedOpenHashMap
import it.unimi.dsi.fastutil.objects.Reference2DoubleOpenHashMap
import org.slf4j.LoggerFactory
import kotlin.math.min

@Cleanup("put into configuration?")
const val SNIPPET_SIZE = 50

private val log = LoggerFactory.getLogger(CollectionManager::class.java)

fun initCollectionManager(corpusConfiguration: CorpusConfiguration, collectionConfig: CollectionConfiguration): CollectionManager {
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

    val mg4jSearchEngine = Mg4jSearchEngine(collection, engine)
    return CollectionManager(collectionConfig.name, mg4jSearchEngine, corpusConfiguration)
}


/**
 * Interface of the underlying mg4j indexing library, performs requests and processes results
 */
@Cleanup("Refactor into multiple classes/functions in different files")
@WhatIf("? decouple from mg4j for easier and faster testing ?")
class CollectionManager internal constructor(
        val collectionName: String,
        private val searchEngine: SearchEngine,
        private val postProcessor: PostProcessor,
        private val resultCreator: ResultCreator,
        private val eqlCompiler: EqlCompiler
) {

    fun query(_query: SearchQuery, offset: Offset = Offset(0, 0)): IndexServer.CollectionResultList {
        log.info("Executing query $_query")
        val query = _query.copyWithAst() // make a local copy because the postprocessing modifies the AST
        val (documentOffset, resultOffset) = offset

        val (resultList, processed) = searchEngine.search(query.query, query.snippetCount, documentOffset - 1)

        val matched = mutableListOf<IndexServer.SearchResult>()
        for ((i, result) in resultList.withIndex()) {
            val document = searchEngine.loadDocument(result.documentId)
            val enclosingInterval = findEnclosingInterval(result.intervals)
            if (!postProcessor.process(query.eqlAst, document, enclosingInterval)) continue
            val (results, hasMore) = resultCreator.multipleResults(query, document, if (document.id == documentOffset) resultOffset else 0)

            if (matched.size + results.size >= query.snippetCount) {
                val nextOffset = when {
                    results.size > query.snippetCount - matched.size || hasMore -> Offset(document.id, query.snippetCount - matched.size + 1)
                    i != resultList.size - 1 -> Offset(resultList[i + 1].documentId, 0)
                    else -> Offset(processed + 1, 0)
                }
                matched.addAll(results.subList(0, min(query.snippetCount - matched.size, results.size)))
                return IndexServer.CollectionResultList(matched, nextOffset)
            }
        }
        return IndexServer.CollectionResultList(matched, if (resultList.isNotEmpty()) Offset(processed + 1, 0) else null)
    }

    fun extendSnippet(query: IndexServer.ContextExtensionQuery): SnippetExtension {
        val document = searchEngine.loadDocument(query.docId)
        val (prefix, suffix) = computeExtensionIntervals(left = query.location, right = query.location + query.size, extension = query.extension, documentSize = document.size)

        var prefixAst: AstNode? = null
        var suffixAst: AstNode? = null
        if (query.query != null) {
            prefixAst = eqlCompiler.parseOrFail(query.query!!)
            postProcessor.process(prefixAst, document, prefix)
            suffixAst = eqlCompiler.parseOrFail(query.query!!)
            postProcessor.process(suffixAst, document, suffix)
        }

        return SnippetExtension(
                prefix = resultCreator.singleResult(query, document, prefixAst, prefix),
                suffix = resultCreator.singleResult(query, document, suffixAst, suffix),
                canExtend = document.size > prefix.size + query.size + suffix.size
        )
    }

    fun getDocument(query: IndexServer.DocumentQuery): IndexServer.FullDocument {
        val document = searchEngine.loadDocument(query.documentId)

        var ast: AstNode? = null
        if (query.query != null) {
            ast = eqlCompiler.parseOrFail(query.query!!)
            postProcessor.process(ast, document)
        }

        val payload = resultCreator.singleResult(query, document, ast)
        return IndexServer.FullDocument(
                document.title,
                document.uri,
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
    val leftInterval = if (prefixSize > 0) Interval.valueOf(left - prefixSize, left - 1) else Interval.empty()
    val rightInterval = if (suffixSize > 0) Interval.valueOf(right + 1, right + suffixSize) else Interval.empty()
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

