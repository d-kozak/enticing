package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

class AddNecessaryBracketsToBooleanNodeCheck(id:String):EqlAstCheck<QueryElemNode.BooleanNode>(id,QueryElemNode.BooleanNode::class) {
    override fun analyze(node: QueryElemNode.BooleanNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        for(i in node.children.indices){
            val child = node.children[i]
            if(child is QueryElemNode.BooleanNode && child.operator != node.operator){
                val parens = QueryElemNode.ParenNode(child,null,child.location)
                parens.parent = node
                node.children[i] = parens
            }
        }
    }
}