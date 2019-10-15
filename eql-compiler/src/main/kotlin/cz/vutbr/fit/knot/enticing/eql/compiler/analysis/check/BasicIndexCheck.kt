package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.BooleanOperator
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode


class BasicIndexCheck(id: String) : EqlAstCheck<QueryElemNode.IndexNode>(id, QueryElemNode.IndexNode::class) {
    override fun analyze(node: QueryElemNode.IndexNode, symbolTable: SymbolTable, corpusConfiguration: CorpusConfiguration, reporter: Reporter) {
        if (node.index !in corpusConfiguration.indexes) {
            val indexLocation = Interval.valueOf(node.location.from, node.location.from + node.index.length - 1)
            reporter.error("Index '${node.index}' is not available", indexLocation, id)
        } else if (node.index == corpusConfiguration.entityMapping.entityIndex) {
            val entityIndexRestriction = "Entity index node only allows boolean, not, parens and simple nodes"
            val nodes = mutableListOf<QueryElemNode.SimpleNode>()
            if (node.elem.collectAllSimpleNodesInExpression(nodes)) {
                for (simpleNode in nodes) {
                    if (simpleNode.content !in corpusConfiguration.entities) {
                        reporter.error("Entity ${simpleNode.content} is not available", simpleNode.location, id)
                    }
                }
            } else {
                reporter.error(entityIndexRestriction, node.elem.location, id)
            }
        }
    }
}

private fun QueryElemNode.collectAllSimpleNodesInExpression(output: MutableList<QueryElemNode.SimpleNode>): Boolean = when (this) {
    is QueryElemNode.SimpleNode -> {
        output.add(this)
        true
    }
    is QueryElemNode.OrderNode -> this.left.collectAllSimpleNodesInExpression(output) && this.right.collectAllSimpleNodesInExpression(output)
    is QueryElemNode.NotNode -> this.elem.collectAllSimpleNodesInExpression(output)
    is QueryElemNode.BooleanNode -> this.left.collectAllSimpleNodesInExpression(output) && this.right.collectAllSimpleNodesInExpression(output)
    is QueryElemNode.ParenNode -> this.query.query.size == 1 && this.query.query[0].collectAllSimpleNodesInExpression(output)
    else -> false
}
