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
        listener.exitRootNode(node)
    }

    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode) {
        listener.enterQueryElemNotNode(node)
        if (shouldContinue(node)) {
            node.elem.accept(this)
        }
        listener.exitQueryElemNotNode(node)
    }

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode) {
        listener.enterQueryElemAssignNode(node)
        if (shouldContinue(node)) {
            node.elem.accept(this)
        }
        listener.exitQueryElemAssignNode(node)
    }

    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {
        listener.enterQueryElemSimpleNode(node)
        listener.exitQueryElemSimpleNode(node)
    }

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode) {
        listener.enterQueryElemIndexNode(node)
        if (shouldContinue(node)) {
            node.elem.accept(this)
        }
        listener.exitQueryElemIndexNode(node)
    }

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {
        listener.enterQueryElemAttributeNode(node)
        if (shouldContinue(node)) {
            node.elem.accept(this)
        }
        listener.exitQueryElemAttributeNode(node)
    }

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode) {
        listener.enterQueryElemParenNode(node)
        if (shouldContinue(node)) {
            node.query.accept(this)
            node.restriction?.accept(this)
        }
        listener.enterQueryElemParenNode(node)
    }

    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode) {
        listener.enterQueryElemBooleanNode(node)
        if (shouldContinue(node)) {
            node.children.toList().forEach { it.accept(this) }
            node.restriction?.accept(this)
        }
        listener.exitQueryElemBooleanNode(node)
    }

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode) {
        listener.enterQueryElemOrderNode(node)
        if (shouldContinue(node)) {
            node.left.accept(this)
            node.right.accept(this)
            node.restriction?.accept(this)
        }
        listener.exitQueryElemOrderNode(node)
    }

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode) {
        listener.enterQueryElemSequenceNode(node)
        if (shouldContinue(node)) {
            node.elems.forEach { it.accept(this) }
        }
        listener.exitQueryElemSequenceNode(node)
    }

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode) {
        listener.enterQueryElemAlignNode(node)
        if (shouldContinue(node)) {
            node.left.accept(this)
            node.right.accept(this)
        }
        listener.exitQueryElemAlignNode(node)
    }

    override fun visitConstraintNode(node: ConstraintNode) {
        listener.enterConstraintNode(node)
        if (shouldContinue(node)) {
            node.expression.accept(this)
        }
        listener.exitConstraintNode(node)
    }

    override fun visitConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode) {
        listener.enterConstraintBooleanExpressionNotNode(node)
        if (shouldContinue(node)) {
            node.elem.accept(this)
        }
        listener.exitConstraintBooleanExpressionNotNode(node)
    }

    override fun visitConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode) {
        listener.enterConstraintBooleanExpressionParenNode(node)
        if (shouldContinue(node)) {
            node.expression.accept(this)
        }
        listener.exitConstraintBooleanExpressionParenNode(node)
    }

    override fun visitConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode) {
        listener.enterConstraintBooleanExpressionOperatorNode(node)
        if (shouldContinue(node)) {
            node.left.accept(this)
            node.right.accept(this)
        }
        listener.exitConstraintBooleanExpressionOperatorNode(node)
    }

    override fun visitConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode) {
        listener.enterConstraintBooleanExpressionComparisonNode(node)
        if (shouldContinue(node)) {
            node.left.accept(this)
            node.right.accept(this)
        }
        listener.exitConstraintBooleanExpressionComparisonNode(node)
    }

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode) {
        listener.enterSimpleReferenceNode(node)
        listener.exitSimpleReferenceNode(node)
    }

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode) {
        listener.enterNestedReferenceNode(node)
        listener.exitNestedReferenceNode(node)
    }



    override fun visitProximityRestrictionNode(node: ProximityRestrictionNode) {
        listener.enterProximityRestrictionNode(node)
        listener.exitProximityRestrictionNode(node)
    }

}