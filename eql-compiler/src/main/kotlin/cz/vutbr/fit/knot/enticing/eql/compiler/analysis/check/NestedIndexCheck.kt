package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.AggregatingListener

/**
 * Looks for nested index operators, which are not allowed
 */
class NestedIndexCheck(id: String) : EqlAstCheck<QueryElemNode.IndexNode>(id, QueryElemNode.IndexNode::class) {

    override fun execute(node: QueryElemNode.IndexNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        node.walk(AggregatingListener({
            if (it !== node && clazz.isInstance(it))
                reporter.error("Nesting index operators is not allowed", it.location, id)
        }, { it === node || !clazz.isInstance(it) }))
    }
}