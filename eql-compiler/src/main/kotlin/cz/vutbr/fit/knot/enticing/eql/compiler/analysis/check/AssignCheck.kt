package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

/**
 * Updates the symbol table for each assign node.
 * Reports if any identifier is defined multiple times.
 */
class AssignCheck(id: String) : EqlAstCheck<QueryElemNode.AssignNode>(id, QueryElemNode.AssignNode::class) {
    override fun execute(node: QueryElemNode.AssignNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        if (node.identifier in symbolTable) {
            val loc = Interval.valueOf(node.location.from, node.location.from + node.identifier.length - 1)
            reporter.error("Identifier ${node.identifier} is already defined", loc, id)
            return
        }
        symbolTable[node.identifier] = node
    }
}