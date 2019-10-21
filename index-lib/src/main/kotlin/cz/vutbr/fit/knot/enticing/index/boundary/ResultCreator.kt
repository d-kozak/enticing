package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.dto.GeneralFormatInfo
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval

/**
 * Creates generates serializable representation of the document
 */
interface ResultCreator {
    /**
     * @return list of results & hasMore flag - if true, more results can be returned from this document
     */
    fun multipleResults(document: IndexedDocument, matchInfo: MatchInfo, formatInfo: GeneralFormatInfo, resultOffset: Int, resultCount: Int, resultFormat: cz.vutbr.fit.knot.enticing.dto.ResultFormat): Pair<List<ResultFormat>, Boolean>

    fun singleResult(document: IndexedDocument, interval: Interval, eqlMatch: List<EqlMatch>, formatInfo: GeneralFormatInfo): ResultFormat.Snippet
}