package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

class QueryElemNextNodeCheck(id: String) : EqlAstCheck<QueryElemNode.NextNode>(id, QueryElemNode.NextNode::class) {
    override fun analyze(node: QueryElemNode.NextNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        val simpleNodes = mutableListOf<QueryElemNode.SimpleNode>()
        var current = node
        var error = false
        loop@ while (current.right !is QueryElemNode.SimpleNode) {
            simpleNodes.add(current.left)
            if (current.right is QueryElemNode.NextNode) current = current.right as QueryElemNode.NextNode
            else {
                reporter.error("Only simple nodes are allowed here", current.right.location, id)
                error = true
            }
        }
        if (!error) {
            simpleNodes.add(current.left)
            simpleNodes.add(current.right as QueryElemNode.SimpleNode)
            node.simpleNodes = simpleNodes
        }
    }
}