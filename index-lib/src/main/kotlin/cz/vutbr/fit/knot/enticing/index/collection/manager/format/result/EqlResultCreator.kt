package cz.vutbr.fit.knot.enticing.index.collection.manager.format.result

import cz.vutbr.fit.knot.enticing.dto.GeneralFormatInfo
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.filterBy
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatch
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.index.boundary.ResultCreator
import cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.*
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import java.lang.Math.min

@Incomplete("not finished yet")
class EqlResultCreator(private val metadataConfiguration: MetadataConfiguration, val loggerFactory: LoggerFactory) : ResultCreator {
    override fun multipleResults(document: IndexedDocument, matchInfo: MatchInfo, formatInfo: GeneralFormatInfo, resultCount: Int, resultFormat: cz.vutbr.fit.knot.enticing.dto.ResultFormat): Pair<List<ResultFormat>, Boolean> {
        return when (resultFormat) {
            cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET -> {
                var intervals = matchInfo.intervals
                val hasMore = intervals.size > resultCount
                intervals = intervals.subList(0, min(resultCount, intervals.size))
                val results = intervals.map { (interval, matchInfo) -> singleResult(document, formatInfo, matchInfo, interval) }
                results to hasMore
            }
            cz.vutbr.fit.knot.enticing.dto.ResultFormat.IDENTIFIER_LIST -> {
                emptyList<ResultFormat>() to false
            }
        }
    }

    override fun singleResult(document: IndexedDocument, formatInfo: GeneralFormatInfo, eqlMatch: List<EqlMatch>, interval: Interval): ResultFormat.Snippet {
        val expanded = if (interval.size < 50) interval.expand(50, 0, document.size - 1) else interval
        val filteredConfig = metadataConfiguration.filterBy(formatInfo.metadata, formatInfo.defaultIndex)
        val (matchStart, matchEnd) = eqlMatch.split()

        val visitor = when (formatInfo.textFormat) {
            TextFormat.PLAIN_TEXT -> return generatePlainText(document, filteredConfig, formatInfo.defaultIndex, expanded)
            TextFormat.HTML -> HtmlGeneratingVisitor(filteredConfig, formatInfo.defaultIndex, expanded, document)
            TextFormat.STRING_WITH_METADATA -> StringWithAnnotationsGeneratingVisitor(filteredConfig, formatInfo.defaultIndex, expanded, document, loggerFactory)
            TextFormat.TEXT_UNIT_LIST -> TextUnitListGeneratingVisitor(filteredConfig, formatInfo.defaultIndex, expanded, document, loggerFactory)
        }
        val iterator = StructuredDocumentIterator(metadataConfiguration, loggerFactory)
        iterator.iterateDocument(document, matchStart, matchEnd, visitor, expanded)
        return visitor.build()
    }
}


fun List<EqlMatch>.split(): Pair<Map<Int, Interval>, Set<Int>> {
    val matchStart = mutableMapOf<Int, Interval>()
    val matchEnd = mutableSetOf<Int>()

    for (match in this) {
        matchStart[match.match.from] = match.queryInterval
        matchEnd.add(match.match.to)
    }

    return matchStart to matchEnd
}