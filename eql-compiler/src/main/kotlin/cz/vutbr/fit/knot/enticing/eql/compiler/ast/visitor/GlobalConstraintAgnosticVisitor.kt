package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ConstraintNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlVisitor
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ReferenceNode


abstract class GlobalConstraintAgnosticVisitor<T> : EqlVisitor<T> {
    private fun fail(): Nothing = throw IllegalStateException("global constraint evaluation should never be called in this type of visitor")

    override fun visitConstraintNode(node: ConstraintNode): T = fail()

    override fun visitConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode): T = fail()

    override fun visitConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode): T = fail()

    override fun visitConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode): T = fail()

    override fun visitConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode): T = fail()

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode): T = fail()

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode): T = fail()
}