package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*

class Mgj4QueryGeneratingVisitor : EqlVisitor<String> {
    override fun visitRootNode(node: RootNode): String = node.query.accept(this)

    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode): String = "!(${node.elem.accept(this)})"

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode): String = node.elem.accept(this)

    override fun visitQueryElemRestrictionNode(node: QueryElemNode.RestrictionNode): String =
            "((${node.left.accept(this)} ${node.right.accept(this)}) ${node.type.accept(this)})"

    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode): String = node.content

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode): String = "(${node.index}:(${node.elem.accept(this)}){{${node.index}->token}})"

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode): String = "((nertag:${node.entity}{{nertag->token}}) ^ (${node.correspondingIndex}:(${node.elem.accept(this)}){{${node.correspondingIndex}->token}}))"

    override fun visitQueryNode(node: QueryNode): String {
        val query = node.query.joinToString(" ") { it.accept(this) }
        return if (node.restriction != null) "($query ${node.restriction.accept(this)})" else query
    }

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode): String = if (node.restriction != null) "(${node.query.accept(this)}) ${node.restriction.accept(this)}" else node.query.accept(this)

    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode): String = "(${node.left.accept(this)} ${node.operator.mg4jValue} ${node.right.accept(this)})"

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode): String = "(${node.left.accept(this)} < ${node.right.accept(this)})"

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode): String = """"${node.elems.joinToString(" ") { it.accept(this) }}""""

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode): String = "(${node.left.accept(this)} ^ ${node.right.accept(this)})"

    override fun visitRestrictionProximityNode(node: RestrictionTypeNode.ProximityNode): String = "~ ${node.distance}"

    override fun visitRestrictionContextNode(node: RestrictionTypeNode.ContextNode): String = when (node.restriction) {
        is ContextRestrictionType.Paragraph -> " - §"
        is ContextRestrictionType.Sentence -> " - ¶"
        is ContextRestrictionType.Query -> " - ${node.restriction.query.accept(this)}"
    }

    override fun visitGlobalContraintNode(node: GlobalConstraintNode): String = fail()

    override fun visitConstraintBooleanExpressionNotNode(node: GlobalConstraintNode.BooleanExpressionNode.NotNode): String = fail()

    override fun visitConstraintBooleanExpressionParenNode(node: GlobalConstraintNode.BooleanExpressionNode.ParenNode): String = fail()

    override fun visitConstraintBooleanExpressionOperatorNode(node: GlobalConstraintNode.BooleanExpressionNode.OperatorNode): String = fail()

    override fun visitConstraintBooleanExpressionComparisonNode(node: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode): String = fail()

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode): String = fail()

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode): String = fail()


}