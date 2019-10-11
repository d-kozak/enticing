package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

class AssignCheck(id: String) : EqlAstCheck<QueryElemNode.AssignNode>(id, QueryElemNode.AssignNode::class) {
    override fun analyze(node: QueryElemNode.AssignNode, symbolTable: SymbolTable, corpusConfiguration: CorpusConfiguration, reporter: Reporter) {
        if (node.identifier in symbolTable) {
            val loc = Interval.valueOf(node.location.from, node.location.from + node.identifier.length - 1)
            reporter.error("Identifier ${node.identifier} is already used", loc, id)
            return
        }
        symbolTable[node.identifier] = node
    }
}