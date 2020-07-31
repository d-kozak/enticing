package cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*

/**
 * Contains callback methods for entering and leaving all nodes of the AST.
 * Used be the EqlWalker
 */
interface EqlListener {
    fun <T : EqlAstNode> shouldContinue(node: T): Boolean = true
    fun enterRootNode(node: RootNode) {}
    fun enterQueryElemNotNode(node: QueryElemNode.NotNode) {}
    fun enterQueryElemAssignNode(node: QueryElemNode.AssignNode) {}
    fun enterQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {}
    fun enterQueryElemIndexNode(node: QueryElemNode.IndexNode) {}
    fun enterQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {}
    fun enterQueryElemParenNode(node: QueryElemNode.ParenNode) {}
    fun enterQueryElemBooleanNode(node: QueryElemNode.BooleanNode) {}
    fun enterQueryElemOrderNode(node: QueryElemNode.OrderNode) {}
    fun enterQueryElemSequenceNode(node: QueryElemNode.SequenceNode) {}
    fun enterQueryElemAlignNode(node: QueryElemNode.AlignNode) {}
    fun enterConstraintNode(node: ConstraintNode) {}
    fun enterConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode) {}
    fun enterConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode) {}
    fun enterConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode) {}
    fun enterConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode) {}
    fun enterSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode) {}
    fun enterNestedReferenceNode(node: ReferenceNode.NestedReferenceNode) {}
    fun enterProximityRestrictionNode(node: ProximityRestrictionNode) {}

    fun exitRootNode(node: RootNode) {}
    fun exitQueryElemNotNode(node: QueryElemNode.NotNode) {}
    fun exitQueryElemAssignNode(node: QueryElemNode.AssignNode) {}
    fun exitQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {}
    fun exitQueryElemIndexNode(node: QueryElemNode.IndexNode) {}
    fun exitQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {}
    fun exitQueryElemParenNode(node: QueryElemNode.ParenNode) {}
    fun exitQueryElemBooleanNode(node: QueryElemNode.BooleanNode) {}
    fun exitQueryElemOrderNode(node: QueryElemNode.OrderNode) {}
    fun exitQueryElemSequenceNode(node: QueryElemNode.SequenceNode) {}
    fun exitQueryElemAlignNode(node: QueryElemNode.AlignNode) {}
    fun exitConstraintNode(node: ConstraintNode) {}
    fun exitConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode) {}
    fun exitConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode) {}
    fun exitConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode) {}
    fun exitConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode) {}
    fun exitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode) {}
    fun exitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode) {}
    fun exitProximityRestrictionNode(node: ProximityRestrictionNode) {}
    fun enterQueryElemNextNode(node: QueryElemNode.NextNode) {}
    fun exitQueryElemNextNode(node: QueryElemNode.NextNode) {}
}


/**
 * Aggregates all the different callbacks into one check that is executed for every node.
 */
class AggregatingListener(private val check: (node: EqlAstNode) -> Unit, val shouldContinueCheck: (node: EqlAstNode) -> Boolean = { true }) : EqlListener {
    override fun <T : EqlAstNode> shouldContinue(node: T): Boolean = this.shouldContinueCheck(node)

    override fun enterRootNode(node: RootNode) = check(node)

    override fun enterQueryElemNotNode(node: QueryElemNode.NotNode) = check(node)

    override fun enterQueryElemAssignNode(node: QueryElemNode.AssignNode) = check(node)

    override fun enterQueryElemSimpleNode(node: QueryElemNode.SimpleNode) = check(node)

    override fun enterQueryElemIndexNode(node: QueryElemNode.IndexNode) = check(node)

    override fun enterQueryElemAttributeNode(node: QueryElemNode.AttributeNode) = check(node)

    override fun enterQueryElemParenNode(node: QueryElemNode.ParenNode) = check(node)

    override fun enterQueryElemBooleanNode(node: QueryElemNode.BooleanNode) = check(node)

    override fun enterQueryElemOrderNode(node: QueryElemNode.OrderNode) = check(node)

    override fun enterQueryElemSequenceNode(node: QueryElemNode.SequenceNode) = check(node)

    override fun enterQueryElemNextNode(node: QueryElemNode.NextNode) = check(node)

    override fun enterConstraintNode(node: ConstraintNode) = check(node)

    override fun enterConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode) = check(node)

    override fun enterConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode) = check(node)

    override fun enterConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode) = check(node)

    override fun enterConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode) = check(node)

    override fun enterSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode) = check(node)

    override fun enterNestedReferenceNode(node: ReferenceNode.NestedReferenceNode) = check(node)

    override fun enterQueryElemAlignNode(node: QueryElemNode.AlignNode) = check(node)

    override fun enterProximityRestrictionNode(node: ProximityRestrictionNode) = check(node)
}