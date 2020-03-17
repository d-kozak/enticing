package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.BooleanOperator
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.DocumentRestriction
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

class RewriteDocumentRestrictionCheck(id: String) : EqlAstCheck<QueryElemNode.AttributeNode>(id, QueryElemNode.AttributeNode::class) {
    override fun analyze(node: QueryElemNode.AttributeNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        if (node.entity !in setOf("doc", "document")) return

        if (node.elem !is QueryElemNode.SimpleNode) {
            reporter.error("Only simple strings allowed here", node.elem.location, id)
            return
        }

        val value = node.elem.content.replace("""\""", "") // mg4j chars are escaped in RAW strings, but for document restrictions, any char is allowed, escaping has to be removed
        val restriction = when (node.attribute) {
            "id" -> DocumentRestriction.Id(value)
            "title" -> DocumentRestriction.Title(value)
            "url" -> DocumentRestriction.Url(value)
            else -> {
                reporter.error("Unknown document restriction type ${node.attribute}", node.elem.location, id)
                return
            }
        }

        if (symbolTable.rootNode.documentRestriction != null) {
            reporter.error("Only one document restriction per query allowed", node.location, id)
            return
        }

        val parent = node.parent
        if (parent is QueryElemNode.BooleanNode && parent.operator == BooleanOperator.AND) {
            if (parent.children.remove(node)) {
                symbolTable.rootNode.documentRestriction = restriction
            } else throw IllegalStateException("Failed to delete ConstraintNode from the tree")
        } else reporter.error("Invalid location for a restriction, should be in AND", node.location, id)
    }
}