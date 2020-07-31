package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.BooleanOperator
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ContextRestriction
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

/**
 * Extract context restriction from the query and put it as an attribute to the root node
 */
class RewriteContextRestrictionCheck(id: String) : EqlAstCheck<QueryElemNode.IndexNode>(id, QueryElemNode.IndexNode::class) {
    override fun execute(node: QueryElemNode.IndexNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        if (node.index in ContextRestriction.indexNames) {
            if (node.elem is QueryElemNode.SimpleNode) {
                val content = node.elem.content.toLowerCase()
                val restriction = ContextRestriction.values().find { content in it.restrictionNames }
                if (restriction != null) {
                    if (symbolTable.rootNode.contextRestriction == null) {
                        val parent = node.parent!!
                        if (parent is QueryElemNode.BooleanNode && parent.operator == BooleanOperator.AND) {
                            if (parent.children.remove(node)) {
                                symbolTable.rootNode.contextRestriction = restriction
                            } else throw IllegalStateException("Failed to delete ConstraintNode from the tree")
                        } else reporter.error("Invalid location for a restriction, should be in AND", node.location, id)
                    } else reporter.error("Only one restriction per query is allowed", node.location, id)
                } else reporter.error("Unknown restriction type $content", node.elem.location, id)
            } else reporter.error("Only simple enum based restrictions allowed", node.elem.location, id)
        }
    }

}