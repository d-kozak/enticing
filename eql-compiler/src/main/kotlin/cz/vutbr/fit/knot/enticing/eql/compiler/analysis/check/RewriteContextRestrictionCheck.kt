package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.BooleanOperator
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ContextRestriction
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode

class RewriteContextRestrictionCheck(id: String) : EqlAstCheck<RootNode>(id, RootNode::class) {
    override fun analyze(node: RootNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        val query = node.query
        if (query is QueryElemNode.BooleanNode && query.operator == BooleanOperator.AND) {
            var ctxIndex = -1
            for ((i, child) in query.children.withIndex()) {
                if (child is QueryElemNode.IndexNode && child.index in ContextRestriction.indexNames) {
                    val content = child.elem
                    if (content is QueryElemNode.SimpleNode) {
                        if (ctxIndex == -1) ctxIndex = i
                        else reporter.error("Only one context restriction is allowed", child.location, id)
                    } else reporter.error("Only simple enum based restrictions allowed", content.location, id)
                }
            }
            if (ctxIndex != -1) {
                val restrictionNode = query.children.removeAt(ctxIndex) as QueryElemNode.IndexNode
                val restrictionContent = (restrictionNode.elem as QueryElemNode.SimpleNode).content.toLowerCase()
                val restrictionType = ContextRestriction.values().firstOrNull { restrictionContent in it.restrictionNames }
                if (restrictionType != null) {
                    node.contextRestriction = restrictionType
                } else reporter.error("Unknown restriction $restrictionContent", restrictionNode.elem.location, id)
            }
        }
    }
}