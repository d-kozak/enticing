package cz.vutbr.fit.knot.enticing.eql.compiler.ast

/**
 * Encapsulates the core ast walking logic that can be reused by multiple more specific walkers
 */
class AstWalker(protected val listener: EqlListener) : EqlVisitor<Unit> {

    private fun <T : EqlAstNode> shouldContinue(node: T): Boolean = listener.shouldContinue(node)

    override fun visitRootNode(node: RootNode) {
        listener.enterRootNode(node)
        if (shouldContinue(node)) {
            node.query.accept(this)
            node.constraint?.accept(this)
        }
    }

    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode) {
        listener.enterQueryElemNotNode(node)
        if (shouldContinue(node)) {
            node.elem.accept(this)
        }
    }

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode) {
        listener.enterQueryElemAssignNode(node)
        if (shouldContinue(node)) {
            node.elem.accept(this)
        }
    }

    override fun visitQueryElemRestrictionNode(node: QueryElemNode.RestrictionNode) {
        listener.enterQueryElemRestrinctionNode(node)
        if (shouldContinue(node)) {
            node.left.accept(this)
            node.right.accept(this)
            node.type.accept(this)
        }
    }

    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {
        listener.enterQueryElemSimpleNode(node)
    }

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode) {
        listener.enterQueryElemIndexNode(node)
        if (shouldContinue(node)) {
            node.elem.accept(this)
        }
    }

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {
        listener.enterQueryElemAttributeNode(node)
        if (shouldContinue(node)) {
            node.elem.accept(this)
        }
    }

    override fun visitQueryNode(node: QueryNode) {
        listener.enterQueryNode(node)
        if (shouldContinue(node)) {
            node.query.forEach { it.accept(this) }
            node.restriction?.accept(this)
        }
    }

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode) {
        listener.enterQueryElemParenNode(node)
        if (shouldContinue(node)) {
            node.query.accept(this)
            node.restriction?.accept(this)
        }
    }

    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode) {
        listener.enterQueryElemBooleanNode(node)
        if (shouldContinue(node)) {
            node.left.accept(this)
            node.right.accept(this)
        }
    }

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode) {
        listener.enterQueryElemOrderNode(node)
        if (shouldContinue(node)) {
            node.left.accept(this)
            node.right.accept(this)
        }
    }

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode) {
        listener.enterQueryElemSequenceNode(node)
        if (shouldContinue(node)) {
            node.elems.forEach { it.accept(this) }
        }
    }

    override fun visitGlobalContraintNode(node: GlobalConstraintNode) {
        listener.enterGlobalContraintNode(node)
        if (shouldContinue(node)) {
            node.expression.accept(this)
        }
    }

    override fun visitConstraintBooleanExpressionNotNode(node: GlobalConstraintNode.BooleanExpressionNode.NotNode) {
        listener.enterConstraintBooleanExpressionNotNode(node)
        if (shouldContinue(node)) {
            node.elem.accept(this)
        }
    }

    override fun visitConstraintBooleanExpressionParenNode(node: GlobalConstraintNode.BooleanExpressionNode.ParenNode) {
        listener.enterConstraintBooleanExpressionParenNode(node)
        if (shouldContinue(node)) {
            node.expression.accept(this)
        }
    }

    override fun visitConstraintBooleanExpressionOperatorNode(node: GlobalConstraintNode.BooleanExpressionNode.OperatorNode) {
        listener.enterConstraintBooleanExpressionOperatorNode(node)
        if (shouldContinue(node)) {
            node.left.accept(this)
            node.right.accept(this)
        }
    }

    override fun visitConstraintBooleanExpressionComparisonNode(node: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode) {
        listener.enterConstraintBooleanExpressionComparisonNode(node)
        if (shouldContinue(node)) {
            node.left.accept(this)
            node.right.accept(this)
        }
    }

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode) {
        listener.enterSimpleReferenceNode(node)
    }

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode) {
        listener.enterNestedReferenceNode(node)
    }

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode) {
        listener.enterQueryElemAlignNode(node)
        if (shouldContinue(node)) {
            node.left.accept(this)
            node.right.accept(this)
        }
    }

    override fun visitRestrictionProximityNode(node: RestrictionTypeNode.ProximityNode) {
        listener.enterRestrictionProximityNode(node)
    }

    override fun visitRestrictionContextNode(node: RestrictionTypeNode.ContextNode) {
        listener.enterRestrictionContextNode(node)
        if (node.restriction is ContextRestrictionType.Query && shouldContinue(node)) {
            node.restriction.query.accept(this)
        }
    }
}