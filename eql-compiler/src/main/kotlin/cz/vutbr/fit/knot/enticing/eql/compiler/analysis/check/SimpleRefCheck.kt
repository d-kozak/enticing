package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ReferenceNode

/**
 * Check whether given reference actually corresponds to an identifier in the query
 */
class SimpleRefCheck(id: String) : EqlAstCheck<ReferenceNode.SimpleReferenceNode>(id, ReferenceNode.SimpleReferenceNode::class) {
    override fun execute(node: ReferenceNode.SimpleReferenceNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        if (node.identifier !in symbolTable) {
            reporter.error("Identifier ${node.identifier} is not available", node.location, id)
        }
    }
}