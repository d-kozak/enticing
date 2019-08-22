package cz.vutbr.fit.knot.enticing.index.collection.manager

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.Offset
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.SnippetExtension
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.EqlCompiler
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.index.boundary.PostProcessor
import cz.vutbr.fit.knot.enticing.index.boundary.ResultCreator
import cz.vutbr.fit.knot.enticing.index.boundary.SearchEngine
import org.slf4j.LoggerFactory
import kotlin.math.min

/**
 * Interface of the underlying mg4j indexing library, performs requests and processes results
 */
class CollectionManager internal constructor(
        val collectionName: String,
        private val searchEngine: SearchEngine,
        private val postProcessor: PostProcessor,
        private val resultCreator: ResultCreator,
        private val eqlCompiler: EqlCompiler
) {

    private val log = LoggerFactory.getLogger(CollectionManager::class.java)

    fun query(_query: SearchQuery, offset: Offset = Offset(0, 0)): IndexServer.CollectionResultList {
        log.info("Executing query $_query")
        val query = _query.copyWithAst() // make a local copy because the postprocessing modifies the AST
        val (documentOffset, resultOffset) = offset

        val (resultList, processed) = searchEngine.search(query.query, query.snippetCount, documentOffset - 1)

        val matched = mutableListOf<IndexServer.SearchResult>()
        for ((i, result) in resultList.withIndex()) {
            val document = searchEngine.loadDocument(result.documentId)
            val matchInfo = postProcessor.process(query.eqlAst, document, result.intervals) ?: continue
            val (results, hasMore) = resultCreator.multipleResults(document, matchInfo, query, if (document.id == documentOffset) resultOffset else 0, query.snippetCount, query.resultFormat)
            val searchResults = results.map {
                IndexServer.SearchResult(
                        collectionName,
                        document.id,
                        document.uri,
                        document.title,
                        it)
            }
            matched.addAll(searchResults)
            if (matched.size >= query.snippetCount) {
                val nextOffset = when {
                    hasMore -> Offset(document.id, resultOffset + results.size)
                    i != resultList.size - 1 -> Offset(resultList[i + 1].documentId, 0)
                    else -> Offset(processed + 1, 0)
                }
                return IndexServer.CollectionResultList(matched, nextOffset)
            }
        }
        return IndexServer.CollectionResultList(matched, if (resultList.isNotEmpty()) Offset(processed + 1, 0) else null)
    }

    fun extendSnippet(query: IndexServer.ContextExtensionQuery): SnippetExtension {
        val document = searchEngine.loadDocument(query.docId)
        val (prefix, suffix) = computeExtensionIntervals(left = query.location, right = query.location + query.size, extension = query.extension, documentSize = document.size)
        val (prefixInfo, suffixInfo) = if (query.query != null) {
            val ast = eqlCompiler.parseOrFail(query.query!!)
            val prefixMatch = postProcessor.process(ast, document, prefix) ?: MatchInfo.empty()
            val suffixMatch = postProcessor.process(ast, document, suffix) ?: MatchInfo.empty()
            prefixMatch to suffixMatch
        } else MatchInfo.empty() to MatchInfo.empty()

        return SnippetExtension(
                prefix = resultCreator.singleResult(document, prefixInfo, query, prefix),
                suffix = resultCreator.singleResult(document, suffixInfo, query, suffix),
                canExtend = document.size > prefix.size + query.size + suffix.size
        )
    }

    fun getDocument(query: IndexServer.DocumentQuery): IndexServer.FullDocument {
        val document = searchEngine.loadDocument(query.documentId)
        val matchInfo = if (query.query != null) {
            val ast = eqlCompiler.parseOrFail(query.query!!)
            postProcessor.process(ast, document) ?: MatchInfo.empty()
        } else MatchInfo.empty()

        val payload = resultCreator.singleResult(document, matchInfo, query)
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

