package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import java.util.*

/**
 * Flattens possibly nested NextNode tree into a single NextNode with multiple simple nodes as children.
 */
class QueryElemNextNodeCheck(id: String) : EqlAstCheck<QueryElemNode.NextNode>(id, QueryElemNode.NextNode::class) {
    override fun execute(node: QueryElemNode.NextNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        val queue = ArrayDeque(node.elems)
        val simpleNodes = mutableListOf<QueryElemNode.SimpleNode>()
        while (queue.isNotEmpty()) {
            when (val elem = queue.removeFirst()) {
                is QueryElemNode.SimpleNode -> simpleNodes.add(elem)
                is QueryElemNode.NextNode -> queue.addAll(elem.elems)
                else -> reporter.error("Only simple nodes are allowed here", elem.location, id)
            }
        }
        node.elems = simpleNodes
    }
}