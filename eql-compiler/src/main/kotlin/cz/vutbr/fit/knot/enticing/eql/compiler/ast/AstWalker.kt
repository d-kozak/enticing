package cz.vutbr.fit.knot.enticing.eql.compiler.ast

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.EqlListener

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
            node.children.forEach { it.accept(this) }
            node.restriction?.accept(this)
        }
    }

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode) {
        listener.enterQueryElemOrderNode(node)
        if (shouldContinue(node)) {
            node.left.accept(this)
            node.right.accept(this)
            node.restriction?.accept(this)
        }
    }

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode) {
        listener.enterQueryElemSequenceNode(node)
        if (shouldContinue(node)) {
            node.elems.forEach { it.accept(this) }
        }
    }

    override fun visitConstraintNode(node: ConstraintNode) {
        listener.enterConstraintNode(node)
        if (shouldContinue(node)) {
            node.expression.accept(this)
        }
    }

    override fun visitConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode) {
        listener.enterConstraintBooleanExpressionNotNode(node)
        if (shouldContinue(node)) {
            node.elem.accept(this)
        }
    }

    override fun visitConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode) {
        listener.enterConstraintBooleanExpressionParenNode(node)
        if (shouldContinue(node)) {
            node.expression.accept(this)
        }
    }

    override fun visitConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode) {
        listener.enterConstraintBooleanExpressionOperatorNode(node)
        if (shouldContinue(node)) {
            node.left.accept(this)
            node.right.accept(this)
        }
    }

    override fun visitConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode) {
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

    override fun visitProximityRestrictionNode(node: ProximityRestrictionNode) {
        listener.enterProximityRestrictionNode(node)
    }

}