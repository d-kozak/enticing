package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ConstraintNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ReferenceNode

class ComparisonCheck(id: String) : EqlAstCheck<ConstraintNode.BooleanExpressionNode.ComparisonNode>(id, ConstraintNode.BooleanExpressionNode.ComparisonNode::class) {
    override fun analyze(node: ConstraintNode.BooleanExpressionNode.ComparisonNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        if ((node.left is ReferenceNode.SimpleReferenceNode) xor (node.right is ReferenceNode.SimpleReferenceNode)) {
            @Incomplete("the correct location is influenced by whitespaces, we might not be able to compute it correctly at this point")
            val operatorLocation = Interval.valueOf(node.left.location.to + 1, node.left.location.to + node.operator.name.length)
            reporter.error("Cannot compare simple references with entity references", operatorLocation, id)
        }
    }
}