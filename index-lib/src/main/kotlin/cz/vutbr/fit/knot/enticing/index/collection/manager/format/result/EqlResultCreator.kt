package cz.vutbr.fit.knot.enticing.index.collection.manager.format.result

import cz.vutbr.fit.knot.enticing.dto.GeneralFormatInfo
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.filterBy
import cz.vutbr.fit.knot.enticing.dto.format.result.Identifier
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatch
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatchType
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.index.boundary.ResultCreator
import cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.*
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import java.lang.Math.min

/**
 * Generates result DTOs from the document and match info
 */
class EqlResultCreator(private val metadataConfiguration: MetadataConfiguration, val loggerFactory: LoggerFactory) : ResultCreator {
    override fun multipleResults(document: IndexedDocument, matchInfo: MatchInfo, formatInfo: GeneralFormatInfo, resultCount: Int, resultFormat: cz.vutbr.fit.knot.enticing.dto.ResultFormat): Pair<List<ResultFormat>, Boolean> {
        var intervals = matchInfo.intervals
        val hasMore = intervals.size > resultCount
        intervals = intervals.subList(0, min(resultCount, intervals.size))
        return when (resultFormat) {
            cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET -> {
                val results = intervals.map { (interval, matchInfo) ->
                    val expanded = if (interval.size < 50) interval.expand(50, 0, document.size - 1) else interval
                    singleResult(document, formatInfo, matchInfo, expanded)
                }
                results to hasMore
            }
            cz.vutbr.fit.knot.enticing.dto.ResultFormat.IDENTIFIER_LIST -> {
                val results = intervals.map { (_, matchInfo) ->
                    val identifiers = matchInfo.filter { it.type is EqlMatchType.Identifier }
                            .map {
                                val name = (it.type as EqlMatchType.Identifier).name
                                Identifier(name, singleResult(document, formatInfo, emptyList(), it.match))
                            }
                    ResultFormat.IdentifierList(identifiers)
                }
                results to hasMore
            }
        }
    }

    override fun singleResult(document: IndexedDocument, formatInfo: GeneralFormatInfo, eqlMatch: List<EqlMatch>, interval: Interval): ResultFormat.Snippet {
        val filteredConfig = metadataConfiguration.filterBy(formatInfo.metadata, formatInfo.defaultIndex)
        val (matchStart, matchEnd) = eqlMatch.split()

        val visitor = when (formatInfo.textFormat) {
            TextFormat.PLAIN_TEXT -> return generatePlainText(document, filteredConfig, formatInfo.defaultIndex, interval)
            TextFormat.HTML -> HtmlGeneratingListener(filteredConfig, formatInfo.defaultIndex, interval, document)
            TextFormat.STRING_WITH_METADATA -> StringWithAnnotationsGeneratingListener(filteredConfig, formatInfo.defaultIndex, interval, document, loggerFactory)
            TextFormat.TEXT_UNIT_LIST -> TextUnitListGeneratingListener(filteredConfig, formatInfo.defaultIndex, interval, document, loggerFactory)
        }
        val iterator = StructuredDocumentIterator(metadataConfiguration, loggerFactory)
        iterator.iterateDocument(document, matchStart, matchEnd, visitor, interval)
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