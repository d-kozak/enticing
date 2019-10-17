package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.AgregatingListener
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

class NestedIndexCheck(id: String) : EqlAstCheck<QueryElemNode.IndexNode>(id, QueryElemNode.IndexNode::class) {

    override fun analyze(node: QueryElemNode.IndexNode, symbolTable: SymbolTable, corpusConfiguration: CorpusConfiguration, reporter: Reporter) {
        node.walk(AgregatingListener({
            if (it !== node && clazz.isInstance(it))
                reporter.error("Nesting index operators is not allowed", it.location, id)
        }, { it === node || !clazz.isInstance(it) }))
    }
}