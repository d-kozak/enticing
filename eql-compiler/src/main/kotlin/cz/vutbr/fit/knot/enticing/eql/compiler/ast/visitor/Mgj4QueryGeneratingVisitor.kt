package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*

class Mgj4QueryGeneratingVisitor : EqlVisitor<String> {
    override fun visitRootNode(node: RootNode): String {
        val query = node.query.accept(this)
        val restriction = node.contextRestriction
        return if (restriction != null) "(($query) ${visitContextRestriction(restriction)})" else query
    }

    private fun visitContextRestriction(restriction: ContextRestriction): String = when (restriction) {
        ContextRestriction.PARAGRAPH -> " - §"
        ContextRestriction.SENTENCE -> " - ¶"
    }

    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode): String = "!(${node.elem.accept(this)})"

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode): String = node.elem.accept(this)

    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode): String = node.content

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode): String = "(${node.index}:(${node.elem.accept(this)}){{${node.index}->token}})"

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode): String = "((nertag:${node.entity}{{nertag->token}}) ^ (${node.correspondingIndex}:(${node.elem.accept(this)}){{${node.correspondingIndex}->token}}))"

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode): String = if (node.restriction != null) "(${node.query.accept(this)}) ${node.restriction!!.accept(this)}" else node.query.accept(this)

    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode): String {
        val content = node.children.joinToString(" ${node.operator.mg4jValue} ", "(", ")") { it.accept(this) }
        return if (node.restriction != null) "($content ${node.restriction!!.accept(this)})" else content
    }

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode): String = "(${node.left.accept(this)} < ${node.right.accept(this)})"

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode): String = """"${node.elems.joinToString(" ") { it.accept(this) }}""""

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode): String = "(${node.left.accept(this)} ^ ${node.right.accept(this)})"

    override fun visitQueryElemNextNode(node: QueryElemNode.NextNode): String = node.elems.joinToString(" ", prefix = "\"", postfix = "\"") { it.accept(this) }

    override fun visitProximityRestrictionNode(node: ProximityRestrictionNode): String = "~ ${node.distance}"

    override fun visitConstraintNode(node: ConstraintNode): String = fail()

    override fun visitConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode): String = fail()

    override fun visitConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode): String = fail()

    override fun visitConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode): String = fail()

    override fun visitConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode): String = fail()

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode): String = fail()

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode): String = fail()


}