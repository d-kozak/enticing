package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlVisitor
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.GlobalConstraintNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ReferenceNode


abstract class GlobalConstraintAgnosticVisitor<T> : EqlVisitor<T> {
    private fun fail(): Nothing = throw IllegalStateException("global constraint evaluation should never be called in this type of visitor")

    override fun visitGlobalContraintNode(node: GlobalConstraintNode): T = fail()

    override fun visitConstraintBooleanExpressionNotNode(node: GlobalConstraintNode.BooleanExpressionNode.NotNode): T = fail()

    override fun visitConstraintBooleanExpressionParenNode(node: GlobalConstraintNode.BooleanExpressionNode.ParenNode): T = fail()

    override fun visitConstraintBooleanExpressionOperatorNode(node: GlobalConstraintNode.BooleanExpressionNode.OperatorNode): T = fail()

    override fun visitConstraintBooleanExpressionComparisonNode(node: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode): T = fail()

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode): T = fail()

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode): T = fail()
}