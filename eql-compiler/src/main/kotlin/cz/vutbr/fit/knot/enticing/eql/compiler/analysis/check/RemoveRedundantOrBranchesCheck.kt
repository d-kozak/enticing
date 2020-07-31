package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.BooleanOperator
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.toEqlQuery

/**
 * Remove redundant OR branches, because they resulted in duplicates in the search results
 * a | a | a => a
 */
class RemoveRedundantOrBranchesCheck(id: String) : EqlAstCheck<QueryElemNode.BooleanNode>(id, QueryElemNode.BooleanNode::class) {

    override fun execute(node: QueryElemNode.BooleanNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        if (node.operator != BooleanOperator.OR) return
        val newChildren = mutableListOf<QueryElemNode>()

        val repr = mutableSetOf<String>()
        for (child in node.children) {
            val isNew = repr.add(child.toEqlQuery())
            if (isNew) newChildren.add(child)
        }

        node.children.clear()
        node.children.addAll(newChildren)
    }
}