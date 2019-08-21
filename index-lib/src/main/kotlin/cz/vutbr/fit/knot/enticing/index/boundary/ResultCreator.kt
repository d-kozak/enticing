package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.dto.GeneralFormatInfo
import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval

/**
 * Creates generates serializable representation of the document
 */
interface ResultCreator {
    /**
     * @return list of results & hasMore flag - if true, more results can be returned from this document
     */
    fun multipleResults(document: IndexedDocument, matchInfo: List<EqlMatch>, formatInfo: GeneralFormatInfo, resultOffset: Int): Pair<List<IndexServer.SearchResult>, Boolean>

    fun singleResult(document: IndexedDocument, matchInfo: List<EqlMatch>, formatInfo: GeneralFormatInfo, interval: Interval? = null): ResultFormat.Snippet
}