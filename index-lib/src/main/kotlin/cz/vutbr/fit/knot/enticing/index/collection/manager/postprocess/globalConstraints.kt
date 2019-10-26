package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.QueryAgnosticVisitor

fun evaluateGlobalConstraint(ast: RootNode, it: Pair<Int, Interval>): Boolean {
    val constraint = ast.constraint ?: return true

    ast.forEachNode {
        if (it is QueryElemNode.AssignNode) {
            it.matchInfoIndex = -1
        }
    }

    fun traverse(node: EqlAstNode, i: Int) {
        val (indexes, match) = node.matchInfo?.get(i) ?: return
        when (node) {
            is QueryNode -> node.query.forEachIndexed { index, queryElemNode -> traverse(queryElemNode, indexes[index]) }
            is QueryElemNode.NotNode -> traverse(node.elem, i)
            is QueryElemNode.AssignNode -> {
                node.matchInfoIndex = i
            }
            is QueryElemNode.RestrictionNode -> {
                if (indexes[0] >= 0)
                    traverse(node.left, indexes[0])

                if (indexes[1] >= 0)
                    traverse(node.right, indexes[1])
            }
            is QueryElemNode.SimpleNode -> { /* nothing to do */
            }
            is QueryElemNode.IndexNode -> traverse(node.elem, i)
            is QueryElemNode.ParenNode -> traverse(node.query, i)
            is QueryElemNode.AttributeNode -> {
                traverse(node.entityNode, indexes[0])
                traverse(node.elem, indexes[1])
            }
            is QueryElemNode.OrderNode -> {
                traverse(node.left, indexes[0])
                traverse(node.right, indexes[1])
            }
            is QueryElemNode.SequenceNode -> {
                node.elems.forEachIndexed { index, queryElemNode -> traverse(queryElemNode, indexes[index]) }
            }
            is QueryElemNode.AlignNode -> {
                traverse(node.left, indexes[0])
                traverse(node.right, indexes[1])
            }
            is QueryElemNode.BooleanNode -> {
                if (indexes[0] >= 0) traverse(node.left, indexes[0])
                if (indexes[1] >= 0) traverse(node.right, indexes[1])
            }
            else -> throw IllegalStateException("unknown node type $node")
        }
    }
    traverse(ast.query, it.first)

    ast.forEachNode {
        if (it is QueryElemNode.AssignNode) {
            check(it.matchInfoIndex != -1)
        }
    }

    val res = constraint.accept(GlobalConstraintEvaluatingVisitor())
    return res
}


class GlobalConstraintEvaluatingVisitor : QueryAgnosticVisitor<Boolean>() {

    @Incomplete("not implemented")
    override fun visitConstraintBooleanExpressionComparisonNode(node: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode): Boolean {
        return true
    }

    override fun visitGlobalContraintNode(node: GlobalConstraintNode): Boolean = node.expression.accept(this)

    override fun visitConstraintBooleanExpressionNotNode(node: GlobalConstraintNode.BooleanExpressionNode.NotNode): Boolean = !node.elem.accept(this)

    override fun visitConstraintBooleanExpressionParenNode(node: GlobalConstraintNode.BooleanExpressionNode.ParenNode): Boolean = node.expression.accept(this)

    override fun visitConstraintBooleanExpressionOperatorNode(node: GlobalConstraintNode.BooleanExpressionNode.OperatorNode): Boolean {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        return when (node.operator) {
            BooleanOperator.AND -> left && right
            BooleanOperator.OR -> left || right
        }
    }


}