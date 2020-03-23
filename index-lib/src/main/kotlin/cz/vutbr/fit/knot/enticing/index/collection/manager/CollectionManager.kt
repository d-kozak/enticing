package cz.vutbr.fit.knot.enticing.index.collection.manager

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.Offset
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.SnippetExtension
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.emptySnippet
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.index.boundary.PostProcessor
import cz.vutbr.fit.knot.enticing.index.boundary.ResultCreator
import cz.vutbr.fit.knot.enticing.index.boundary.SearchEngine
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import kotlin.math.min

/**
 * Interface of the underlying mg4j indexing library, performs requests and processes results
 */
class CollectionManager internal constructor(
        val collectionName: String,
        private val searchEngine: SearchEngine,
        private val postProcessor: PostProcessor,
        private val resultCreator: ResultCreator,
        private val eqlCompiler: EqlCompiler,
        private val metadataConfiguration: MetadataConfiguration,
        loggerFactory: LoggerFactory
) {

    val logger = loggerFactory.logger { }

    fun query(query: SearchQuery, offset: Offset = Offset(0, 0)): IndexServer.CollectionResultList {
        logger.info("Executing query $query")
        var (documentOffset, resultOffset) = offset

        val ast = if (query.eqlAst == null) {
            query.eqlAst = eqlCompiler.parseOrFail(query.query, metadataConfiguration)
            query.eqlAst!!
        } else query.eqlAst!!

        val matched = mutableListOf<IndexServer.SearchResult>()
        var firstDocument = true

        while (true) {
            val (resultList, relevantDocuments) = searchEngine.search(ast.toMgj4Query(), query.snippetCount, documentOffset)
            if (resultList.isEmpty()) return IndexServer.CollectionResultList(matched, null)
            for ((i, result) in resultList.withIndex()) {
                val document = searchEngine.loadDocument(result.documentId)
                check(document.id == result.documentId) { "Invalid document id set in the search engine: ${result.documentId} vs ${document.id}" }
                val matchInfo = postProcessor.process(ast.deepCopy(), document, query.defaultIndex, if (firstDocument) resultOffset else 0, metadataConfiguration)
                if (matchInfo == null || matchInfo.intervals.isEmpty()) continue
                val (results, hasMore) = resultCreator.multipleResults(document, matchInfo, query, query.snippetCount, query.resultFormat)
                val searchResults = results.map {
                    IndexServer.SearchResult(
                            collectionName,
                            document.id,
                            document.uuid,
                            document.uri,
                            document.title,
                            it)
                }
                matched.addAll(searchResults)
                if (matched.size >= query.snippetCount) {
                    val nextOffset = when {
                        hasMore && firstDocument -> Offset(documentOffset, results.size + resultOffset)
                        hasMore -> Offset(documentOffset + i, results.size)
                        else -> Offset(documentOffset + i + 1, 0)
                    }
                    return IndexServer.CollectionResultList(matched, nextOffset)
                }
                firstDocument = false
            }
            documentOffset += resultList.size
        }
    }

    /**
     * Everything important is already in the snippet, so there is no need for postprocessing here
     */
    fun extendSnippet(query: IndexServer.ContextExtensionQuery): SnippetExtension {
        val document = searchEngine.loadDocument(query.docId)
        val (prefix, suffix) = computeExtensionIntervals(left = query.location, right = query.location + query.size, extension = query.extension, documentSize = document.size)
        if (prefix.isEmpty() && suffix.isEmpty()) return SnippetExtension(emptySnippet(query.textFormat), emptySnippet(query.textFormat), false)

        return SnippetExtension(
                prefix = resultCreator.singleResult(document, query, emptyList(), prefix),
                suffix = resultCreator.singleResult(document, query, emptyList(), suffix),
                canExtend = document.size > prefix.size + query.size + suffix.size
        )
    }

    fun getDocument(query: IndexServer.DocumentQuery): IndexServer.FullDocument {
        val document = searchEngine.loadDocument(query.documentId)
        val matchInfo = if (query.query != null) {
            val ast = eqlCompiler.parseOrFail(query.query!!, metadataConfiguration)
            postProcessor.process(ast, document, query.defaultIndex, 0, metadataConfiguration) ?: MatchInfo.empty()
        } else MatchInfo.empty()

        val offset = query.offset
        val matchList = when {
            offset != null && offset < matchInfo.intervals.size -> matchInfo.intervals[offset].eqlMatch
            offset == null && matchInfo.intervals.isNotEmpty() -> matchInfo.intervals[0].eqlMatch
            else -> emptyList()
        }

        val payload = resultCreator.singleResult(document, query, matchList, document.interval)
        return IndexServer.FullDocument(
                document.title,
                document.uri,
                payload
        )
    }

    fun getRawDocument(documentId: Int, from: Int, to: Int): String = searchEngine.getRawDocument(documentId, from, to)
}


/**
 * Compute the intervals for prefix and suffix for snippet extension
 */
@Cleanup("refactor and move closer to the interval domain class")
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

