package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

class FlattenBooleanOperationsCheck(id: String) : EqlAstCheck<QueryElemNode.BooleanNode>(id, QueryElemNode.BooleanNode::class) {

    override fun analyze(node: QueryElemNode.BooleanNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        val operator = node.operator

        fun flatten(node: QueryElemNode.BooleanNode) {
            for (i in node.children.indices.reversed()) {
                val child = node.children[i]
                if (child is QueryElemNode.BooleanNode && child.operator == operator) {
                    flatten(child)
                    node.children.removeAt(i)
                    node.children.addAll(i, child.children)
                    child.children.forEach { it.parent = node }
                }
            }
        }

        flatten(node)
    }
}