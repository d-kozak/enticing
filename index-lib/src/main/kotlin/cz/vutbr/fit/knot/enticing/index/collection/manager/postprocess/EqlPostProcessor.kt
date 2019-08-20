package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.DummyLeaf
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.DummyRoot
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.PostProcessor

@Cleanup("put into eql-compiler?")
@Incomplete("finish when EQL is in place")
class EqlPostProcessor : PostProcessor {
    override fun process(ast: AstNode, document: IndexedDocument, enclosingInterval: Interval?): Boolean {
        require(ast is DummyRoot) { "eql not implemented, only DummyRoot is expected now" }
        // nothing to do so far
        return true
    }

    override fun process(ast: AstNode, document: IndexedDocument, matchedIntervals: List<List<Interval>>): Boolean {
        require(ast is DummyRoot) { "eql not implemented, only DummyRoot is expected now" }

        // until eql is ready, just append whatever intervals come add
        for (intervals in matchedIntervals) {
            ast.children.add(DummyLeaf(Interval.valueOf(1, 2), intervals.toMutableList()))
        }

        return true
    }
}