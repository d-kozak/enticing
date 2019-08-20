package cz.vutbr.fit.knot.enticing.index.collection.manager.format.result

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.GeneralFormatInfo
import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.ResultCreator

@Incomplete("not implemented yet")
class EqlResultCreator : ResultCreator {
    override fun multipleResults(query: SearchQuery, document: IndexedDocument, resultOffset: Int): Pair<List<IndexServer.SearchResult>, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun singleResult(query: GeneralFormatInfo, document: IndexedDocument, astNode: AstNode?, interval: Interval?): ResultFormat.Snippet {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}