package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.EqlMatch
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.PostProcessor

@Cleanup("put into eql-compiler?")
@Incomplete("finish when EQL is in place")
class EqlPostProcessor : PostProcessor {
    override fun process(ast: AstNode, document: IndexedDocument, enclosingInterval: Interval?): List<EqlMatch>? {
        return emptyList()
    }

    override fun process(ast: AstNode, document: IndexedDocument, matchedIntervals: List<List<Interval>>): List<EqlMatch>? {
        // just highlight the ends of each interval for now
        val mg4jIntervals = matchedIntervals.map { EqlMatch.IndexMatch(Interval.valueOf(0, 1), it.map { listOf(it.from, it.to) }.flatten()) }
        return mg4jIntervals
    }
}