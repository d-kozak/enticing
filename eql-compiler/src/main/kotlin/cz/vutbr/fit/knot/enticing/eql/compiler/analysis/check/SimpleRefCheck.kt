package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ReferenceNode

class SimpleRefCheck(id: String) : EqlAstCheck<ReferenceNode.SimpleReferenceNode>(id, ReferenceNode.SimpleReferenceNode::class) {
    override fun analyze(node: ReferenceNode.SimpleReferenceNode, symbolTable: SymbolTable, corpusConfiguration: CorpusConfiguration, reporter: Reporter) {
        if (node.identifier !in symbolTable) {
            reporter.error("Identifier ${node.identifier} is not available", node.location, id)
        }
    }
}