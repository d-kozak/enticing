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
import cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.next.*

@Incomplete("not finished yet")
class EqlResultCreator(private val corpusConfiguration: CorpusConfiguration) : ResultCreator {
    override fun multipleResults(document: IndexedDocument, matchInfo: MatchInfo, formatInfo: GeneralFormatInfo, resultOffset: Int, resultFormat: cz.vutbr.fit.knot.enticing.dto.ResultFormat): Pair<List<ResultFormat>, Boolean> {
        val filteredConfig = corpusConfiguration.filterBy(formatInfo.metadata, formatInfo.defaultIndex)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun singleResult(document: IndexedDocument, matchInfo: MatchInfo, formatInfo: GeneralFormatInfo, interval: Interval?): ResultFormat.Snippet {
        val filteredConfig = corpusConfiguration.filterBy(formatInfo.metadata, formatInfo.defaultIndex)
        val (matchStart, matchEnd) = matchInfo.leafIntervals.split()

        val visitor = when (formatInfo.textFormat) {
            TextFormat.PLAIN_TEXT -> return generatePlainText(document, filteredConfig, formatInfo.defaultIndex, interval)
            TextFormat.HTML -> HtmlGeneratingVisitor(filteredConfig, formatInfo.defaultIndex)
            TextFormat.STRING_WITH_METADATA -> StringWithAnnotationsGeneratingVisitor(filteredConfig, formatInfo.defaultIndex)
            TextFormat.TEXT_UNIT_LIST -> TextUnitListGeneratingVisitor(filteredConfig, formatInfo.defaultIndex)
        }
        val iterator = StructuredDocumentIterator(filteredConfig)
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
                for (i in match.documentIndexList) {
                    matchStart[i] = match.queryInterval
                    matchEnd.add(i)
                }
            }

            is EqlMatch.IdentifierMatch -> {
                for (i in match.documentIntervalList) {
                    matchStart[i.from] = match.queryInterval
                    matchEnd.add(i.to)
                }
            }
        }
    }

    return matchStart to matchEnd
}