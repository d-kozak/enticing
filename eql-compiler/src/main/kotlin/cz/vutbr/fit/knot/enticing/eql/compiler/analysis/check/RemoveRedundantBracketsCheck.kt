package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.EqlListener

class RemoveRedundantBracketsCheck(id: String) : EqlAstCheck<RootNode>(id, RootNode::class) {
    override fun analyze(node: RootNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        node.walk(RemoveRedundantBracketsListener())
    }


    private class RemoveRedundantBracketsListener : EqlListener {
        override fun exitQueryElemParenNode(node: QueryElemNode.ParenNode) {
            val child = node.query
            if (child is QueryElemNode.ParenNode && child.restriction == null) {
                node.query = child.query
                node.query.parent = node
            }
        }

        override fun exitRootNode(node: RootNode) {
            val child = node.query
            if (child is QueryElemNode.ParenNode && child.restriction == null) {
                node.query = child.query
                node.query.parent = node
            }
        }

        override fun exitQueryElemBooleanNode(node: QueryElemNode.BooleanNode) {
            for (i in node.children.indices) {
                val child = node.children[i]
                if (child is QueryElemNode.ParenNode && child.restriction == null) {
                    val subQuery = child.query
                    val sameBooleanOp = subQuery is QueryElemNode.BooleanNode && node.operator == subQuery.operator
                    val simpleNodeInside = subQuery is QueryElemNode.SimpleNode
                    if (sameBooleanOp || simpleNodeInside) {
                        subQuery.parent = node
                        node.children[i] = subQuery
                    }
                }
            }
        }

        override fun exitQueryElemOrderNode(node: QueryElemNode.OrderNode) {

            fun hasRedundantBracket(node: QueryElemNode): Boolean =
                    node is QueryElemNode.ParenNode && node.restriction == null && (
                            node.query is QueryElemNode.SimpleNode || node.query is QueryElemNode.OrderNode)


            if (hasRedundantBracket(node.left)) {
                node.left = (node.left as QueryElemNode.ParenNode).query
                node.left.parent = node
            }
            if (hasRedundantBracket(node.right)) {
                node.right = (node.right as QueryElemNode.ParenNode).query
                node.right.parent = node
            }
        }
    }
}

