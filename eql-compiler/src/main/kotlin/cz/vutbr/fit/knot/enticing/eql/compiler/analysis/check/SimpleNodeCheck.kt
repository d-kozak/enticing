package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.SimpleQueryType

class SimpleNodeCheck(id: String) : EqlAstCheck<QueryElemNode.SimpleNode>(id, QueryElemNode.SimpleNode::class) {
    override fun analyze(node: QueryElemNode.SimpleNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        if (node.type == SimpleQueryType.STRING)
            node.content = node.content.toLowerCase()
    }
}