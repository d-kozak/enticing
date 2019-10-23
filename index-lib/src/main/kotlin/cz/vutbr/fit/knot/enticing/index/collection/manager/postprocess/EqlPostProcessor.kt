package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.index.boundary.PostProcessor

@Cleanup("put into eql-compiler?")
@Incomplete("finish when EQL is in place")
class EqlPostProcessor : PostProcessor {
    override fun process(ast: AstNode, document: IndexedDocument, defaultIndex: String, corpusConfiguration: CorpusConfiguration, enclosingInterval: Interval?): MatchInfo? {
        return matchDocument(ast as EqlAstNode, document, defaultIndex, corpusConfiguration, enclosingInterval
                ?: Interval.valueOf(0, document.size - 1))
    }

    override fun process(ast: AstNode, document: IndexedDocument, defaultIndex: String, corpusConfiguration: CorpusConfiguration, matchedIntervals: List<List<Interval>>): MatchInfo? {
        val flat = matchedIntervals.flatten()
        val min = flat.minBy { it.from }?.from ?: 0
        val max = flat.maxBy { it.to }?.to ?: document.size - 1
        return matchDocument(ast as EqlAstNode, document, defaultIndex, corpusConfiguration, Interval.valueOf(min, max))
    }
}