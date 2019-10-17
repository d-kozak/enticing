package cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*

interface EqlListener {
    fun <T : EqlAstNode> shouldContinue(node: T): Boolean = true
    fun enterRootNode(node: RootNode) {}
    fun enterQueryElemNotNode(node: QueryElemNode.NotNode) {}
    fun enterQueryElemAssignNode(node: QueryElemNode.AssignNode) {}
    fun enterQueryElemRestrinctionNode(node: QueryElemNode.RestrictionNode) {}
    fun enterQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {}
    fun enterQueryElemIndexNode(node: QueryElemNode.IndexNode) {}
    fun enterQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {}
    fun enterQueryNode(node: QueryNode) {}
    fun enterQueryElemParenNode(node: QueryElemNode.ParenNode) {}
    fun enterQueryElemBooleanNode(node: QueryElemNode.BooleanNode) {}
    fun enterQueryElemOrderNode(node: QueryElemNode.OrderNode) {}
    fun enterQueryElemSequenceNode(node: QueryElemNode.SequenceNode) {}
    fun enterGlobalContraintNode(node: GlobalConstraintNode) {}
    fun enterConstraintBooleanExpressionNotNode(node: GlobalConstraintNode.BooleanExpressionNode.NotNode) {}
    fun enterConstraintBooleanExpressionParenNode(node: GlobalConstraintNode.BooleanExpressionNode.ParenNode) {}
    fun enterConstraintBooleanExpressionOperatorNode(node: GlobalConstraintNode.BooleanExpressionNode.OperatorNode) {}
    fun enterConstraintBooleanExpressionComparisonNode(node: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode) {}
    fun enterSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode) {}
    fun enterNestedReferenceNode(node: ReferenceNode.NestedReferenceNode) {}
    fun enterQueryElemAlignNode(node: QueryElemNode.AlignNode) {}
    fun enterRestrictionProximityNode(node: RestrictionTypeNode.ProximityNode) {}
    fun enterRestrictionContextNode(node: RestrictionTypeNode.ContextNode) {}
}


class AgregatingListener(private val check: (node: EqlAstNode) -> Unit, val shouldContinueCheck: (node: EqlAstNode) -> Boolean = { true }) : EqlListener {
    override fun <T : EqlAstNode> shouldContinue(node: T): Boolean = this.shouldContinueCheck(node)

    override fun enterRootNode(node: RootNode) = check(node)

    override fun enterQueryElemNotNode(node: QueryElemNode.NotNode) = check(node)

    override fun enterQueryElemAssignNode(node: QueryElemNode.AssignNode) = check(node)

    override fun enterQueryElemRestrinctionNode(node: QueryElemNode.RestrictionNode) = check(node)

    override fun enterQueryElemSimpleNode(node: QueryElemNode.SimpleNode) = check(node)

    override fun enterQueryElemIndexNode(node: QueryElemNode.IndexNode) = check(node)

    override fun enterQueryElemAttributeNode(node: QueryElemNode.AttributeNode) = check(node)

    override fun enterQueryNode(node: QueryNode) = check(node)

    override fun enterQueryElemParenNode(node: QueryElemNode.ParenNode) = check(node)

    override fun enterQueryElemBooleanNode(node: QueryElemNode.BooleanNode) = check(node)

    override fun enterQueryElemOrderNode(node: QueryElemNode.OrderNode) = check(node)

    override fun enterQueryElemSequenceNode(node: QueryElemNode.SequenceNode) = check(node)

    override fun enterGlobalContraintNode(node: GlobalConstraintNode) = check(node)

    override fun enterConstraintBooleanExpressionNotNode(node: GlobalConstraintNode.BooleanExpressionNode.NotNode) = check(node)

    override fun enterConstraintBooleanExpressionParenNode(node: GlobalConstraintNode.BooleanExpressionNode.ParenNode) = check(node)

    override fun enterConstraintBooleanExpressionOperatorNode(node: GlobalConstraintNode.BooleanExpressionNode.OperatorNode) = check(node)

    override fun enterConstraintBooleanExpressionComparisonNode(node: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode) = check(node)

    override fun enterSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode) = check(node)

    override fun enterNestedReferenceNode(node: ReferenceNode.NestedReferenceNode) = check(node)

    override fun enterQueryElemAlignNode(node: QueryElemNode.AlignNode) = check(node)

    override fun enterRestrictionProximityNode(node: RestrictionTypeNode.ProximityNode) = check(node)

    override fun enterRestrictionContextNode(node: RestrictionTypeNode.ContextNode) = check(node)


}