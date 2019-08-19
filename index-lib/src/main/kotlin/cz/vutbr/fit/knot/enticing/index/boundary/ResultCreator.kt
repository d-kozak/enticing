package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.GeneralFormatInfo
import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval

/**
 * Creates generates serializable representation of the document
 */
interface ResultCreator {
    /**
     * @return list of results & hasMore flag - if true, more results can be returned from this document
     */
    fun multipleResults(query: SearchQuery, document: IndexedDocument, resultOffset: Int): Pair<List<IndexServer.SearchResult>, Boolean>

    fun singleResult(query: GeneralFormatInfo, document: IndexedDocument, astNode: AstNode? = null, interval: Interval? = null): ResultFormat.Snippet
}