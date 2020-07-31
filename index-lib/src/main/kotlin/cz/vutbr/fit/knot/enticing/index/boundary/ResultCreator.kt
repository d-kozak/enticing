package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.dto.GeneralFormatInfo
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatch

/**
 * Creates serializable representation of the document
 */
interface ResultCreator {
    /**
     * @return list of results & hasMore flag - if true, more results can be returned from this document
     */
    fun multipleResults(document: IndexedDocument, matchInfo: MatchInfo, formatInfo: GeneralFormatInfo, resultCount: Int, resultFormat: cz.vutbr.fit.knot.enticing.dto.ResultFormat): Pair<List<ResultFormat>, Boolean>

    /**
     * @return a single result
     */
    fun singleResult(document: IndexedDocument, formatInfo: GeneralFormatInfo, eqlMatch: List<EqlMatch>, interval: Interval = document.interval): ResultFormat.Snippet
}