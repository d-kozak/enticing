package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*

/**
 * Transform the AST into a canonic string form that can be used for comparisons
 */
fun EqlAstNode.toEqlQuery(): String = this.accept(EqlQueryGeneratingVisitor())

/**
 * Transforms the AST into a canonic string form that can be used for comparisons
 */
class EqlQueryGeneratingVisitor : EqlVisitor<String> {
    override fun visitRootNode(node: RootNode) = buildString {
        if (node.documentRestriction != null) {
            append(visitDocumentRestriction(node.documentRestriction!!))
        }
        append(node.query.accept(this@EqlQueryGeneratingVisitor))
        if (node.contextRestriction != null) {
            append(' ')
            append(visitContextRestriction(node.contextRestriction!!))
        }
        if (node.constraint != null) {
            append(" && ")
            append(node.constraint.accept(this@EqlQueryGeneratingVisitor))
        }
    }

    private fun visitContextRestriction(restriction: ContextRestriction): String = when (restriction) {
        ContextRestriction.PARAGRAPH -> "ctx:par"
        ContextRestriction.SENTENCE -> "ctx:sent"
    }

    private fun visitDocumentRestriction(restriction: DocumentRestriction): String = when (restriction) {
        is DocumentRestriction.Id -> "document.id:${restriction.text}"
        is DocumentRestriction.Title -> "document.title:${restriction.text}"
        is DocumentRestriction.Url -> "document.url:${restriction.text}"
        is DocumentRestriction.Uuid -> "document.uuid:${restriction.text}"
    }

    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode): String = "!${node.elem.accept(this)}"

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode): String = "${node.identifier}:=${node.elem.accept(this)}"

    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode): String = node.canonicEqlValue

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode): String = "${node.index}:${node.elem.accept(this)}"

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode): String = "${node.entity}.${node.attribute}:${node.elem.accept(this)}"

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode): String {
        val left = "(${node.query.accept(this)})"
        return if (node.restriction != null) {
            "$left ${node.restriction!!.accept(this)}"
        } else left
    }

    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode): String {
        val op = when (node.operator) {
            BooleanOperator.AND -> " "
            BooleanOperator.OR -> " | "
        }
        val left = node.children.joinToString(op) { it.accept(this) }
        val addBrackets = !(node.parent is QueryElemNode.ParenNode || node.parent is RootNode)
        return if (node.restriction != null) {
            if (addBrackets) "($left ${node.restriction!!.accept(this)})" else "$left ${node.restriction!!.accept(this)}"
        } else if (addBrackets) "($left)" else left
    }

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode): String {
        val left = "${node.left.accept(this)} < ${node.right.accept(this)}"
        return if (node.restriction != null) {
            "$left ${node.restriction!!.accept(this)}"
        } else left
    }

    override fun visitQueryElemNextNode(node: QueryElemNode.NextNode): String = node.elems.joinToString("+", prefix = "(", postfix = ")") { it.accept(this) }

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode): String = "\"${node.elems.joinToString(" ") { it.accept(this) }}\""

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode): String = "${node.left.accept(this)} ^ ${node.right.accept(this)}"

    override fun visitProximityRestrictionNode(node: ProximityRestrictionNode): String = "~ ${node.distance}"

    override fun visitConstraintNode(node: ConstraintNode): String = node.expression.accept(this)

    override fun visitConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode): String = "!${node.elem.accept(this)}"

    override fun visitConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode): String = "(${node.expression.accept(this)})"

    override fun visitConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode): String = "${node.left.accept(this)} ${node.operator.mg4jValue} ${node.right.accept(this)}"

    override fun visitConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode): String = "${node.left.accept(this)} ${node.operator.mg4jValue} ${node.right.accept(this)}"

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode): String = node.identifier

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode): String = "${node.identifier}.${node.attribute}"


}