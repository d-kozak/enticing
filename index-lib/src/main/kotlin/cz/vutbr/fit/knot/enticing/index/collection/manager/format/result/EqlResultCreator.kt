package cz.vutbr.fit.knot.enticing.index.collection.manager.format.result

import cz.vutbr.fit.knot.enticing.dto.GeneralFormatInfo
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.filterBy
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.EqlMatch
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.index.boundary.ResultCreator
import cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.*
import java.lang.Math.min

@Incomplete("not finished yet")
class EqlResultCreator(private val corpusConfiguration: CorpusConfiguration) : ResultCreator {
    override fun multipleResults(document: IndexedDocument, matchInfo: MatchInfo, formatInfo: GeneralFormatInfo, resultOffset: Int, resultCount: Int, resultFormat: cz.vutbr.fit.knot.enticing.dto.ResultFormat): Pair<List<ResultFormat>, Boolean> {
        return when (resultFormat) {
            cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET -> {
                var intervals = matchInfo.intervals.keys.toList()
                if (intervals.size < resultOffset) return Pair(emptyList(), false)
                intervals = intervals.subList(resultOffset, intervals.size)
                val hasMore = intervals.size > resultCount
                intervals = intervals.subList(0, min(resultCount, intervals.size))
                val results = intervals.map { singleResult(document, it, matchInfo.intervals.getValue(it), formatInfo) }
                results to hasMore
            }
            cz.vutbr.fit.knot.enticing.dto.ResultFormat.IDENTIFIER_LIST -> {
                emptyList<ResultFormat>() to false
            }
        }
    }

    override fun singleResult(document: IndexedDocument, interval: Interval, eqlMatch: List<EqlMatch>, formatInfo: GeneralFormatInfo): ResultFormat.Snippet {
        val filteredConfig = corpusConfiguration.filterBy(formatInfo.metadata, formatInfo.defaultIndex)
        val (matchStart, matchEnd) = eqlMatch.split()

        val visitor = when (formatInfo.textFormat) {
            TextFormat.PLAIN_TEXT -> return generatePlainText(document, filteredConfig, formatInfo.defaultIndex, interval)
            TextFormat.HTML -> HtmlGeneratingVisitor(filteredConfig, formatInfo.defaultIndex, interval, document)
            TextFormat.STRING_WITH_METADATA -> StringWithAnnotationsGeneratingVisitor(filteredConfig, formatInfo.defaultIndex, interval, document)
            TextFormat.TEXT_UNIT_LIST -> TextUnitListGeneratingVisitor(filteredConfig, formatInfo.defaultIndex, interval, document)
        }
        val iterator = StructuredDocumentIterator(corpusConfiguration)
        iterator.iterateDocument(document, matchStart, matchEnd, visitor, interval)
        return visitor.build()
    }
}


fun List<EqlMatch>.split(): Pair<Map<Int, Interval>, Set<Int>> {
    val matchStart = mutableMapOf<Int, Interval>()
    val matchEnd = mutableSetOf<Int>()

    for (match in this) {
        when (match) {
            is EqlMatch.IndexMatch -> {
                for (index in match.documentIndexes) {
                    matchStart[index] = match.queryInterval
                    matchEnd.add(index)
                }
            }

            is EqlMatch.IdentifierMatch -> {
                for ((from, to) in match.documentIntervals) {
                    matchStart[from] = match.queryInterval
                    matchEnd.add(to)
                }
            }
        }
    }

    return matchStart to matchEnd
}