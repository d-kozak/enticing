package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*

fun EqlAstNode.dumpKotlinDefs() = println(accept(AstDefinitionsGeneratingVisitor()))

/**
 * Creates a string that should contain a piece of valid Kotlin code that creates the AST nodes ( e.g. for bootstraping tests - manual AST creation is time consuming)
 */

internal fun Interval.toKotlinDef() = "Interval.valueOf(${this.from},${this.to})"

fun EqlAstNode.toKotlinDef() = this.accept(AstDefinitionsGeneratingVisitor())

class AstDefinitionsGeneratingVisitor : EqlVisitor<String> {

    override fun visitRootNode(node: RootNode): String {
        val query = node.query.accept(this)
        val constraint = node.constraint?.accept(this) ?: "null"
        return "RootNode($query,$constraint,${node.location.toKotlinDef()})"
    }

    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode): String {
        val elem = node.elem.accept(this)
        return "QueryElemNode.NotNode($elem,${node.location.toKotlinDef()})"
    }

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode): String {
        val elem = node.elem.accept(this)
        return """"QueryElemNode.AssignNode("${node.identifier}",$elem,${node.location.toKotlinDef()})"""
    }

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode): String {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        return """QueryElemNode.AlignNode($left,$right,${node.location.toKotlinDef()})"""
    }

    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode): String {
        return """QueryElemNode.SimpleNode("${node.content}",SimpleQueryType.${node.type},${node.location.toKotlinDef()})"""
    }

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode): String {
        val elem = node.elem.accept(this)
        return """QueryElemNode.IndexNode("${node.index}",$elem,${node.location.toKotlinDef()})"""
    }

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode): String {
        val elem = node.elem.accept(this)
        return """QueryElemNode.AttributeNode("${node.entity}","${node.attribute}",$elem,${node.location.toKotlinDef()})"""
    }

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode): String {
        val query = node.query.accept(this)
        val restriction = node.restriction?.accept(this) ?: "null"
        return """QueryElemNode.ParenNode($query,$restriction,${node.location.toKotlinDef()})"""
    }

    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode): String {
        val children = node.children.joinToString(",", "mutableListOf(", ")") { it.accept(this) }
        val restriction = node.restriction?.accept(this) ?: "null"
        return """QueryElemNode.BooleanNode($children,BooleanOperator.${node.operator},$restriction,${node.location.toKotlinDef()})"""
    }

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode): String {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        val restriction = node.restriction?.accept(this) ?: "null"
        return "QueryElemNode.OrderNode($left,$right,$restriction,${node.location.toKotlinDef()})"
    }

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode): String {
        val elems = node.elems.joinToString(",") { it.accept(this) }
        return "QueryElemNode.SequenceNode(listOf($elems),${node.location.toKotlinDef()})"
    }

    override fun visitQueryElemNextNode(node: QueryElemNode.NextNode): String {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        val location = node.location.toKotlinDef()
        return "QueryElemNode.NextNode($left,$right,$location)"
    }

    override fun visitProximityRestrictionNode(node: ProximityRestrictionNode): String {
        return """ProximityRestrictionNode("${node.distance}",${node.location.toKotlinDef()})"""
    }

    override fun visitConstraintNode(node: ConstraintNode): String {
        val expression = node.expression.accept(this)
        return """GlobalConstraintNode($expression,${node.location.toKotlinDef()})"""
    }

    override fun visitConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode): String {
        val elem = node.elem.accept(this)
        return """GlobalConstraintNode.BooleanExpressionNode.NotNode($elem,${node.location.toKotlinDef()})"""
    }

    override fun visitConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode): String {
        val elem = node.expression.accept(this)
        return """GlobalConstraintNode.BooleanExpressionNode.ParenNode($elem,${node.location.toKotlinDef()})"""
    }

    override fun visitConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode): String {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        return """GlobalConstraintNode.BooleanExpressionNode.OperatorNode($left,BooleanOperator.${node.operator},$right,${node.location.toKotlinDef()})"""
    }

    override fun visitConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode): String {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        return """GlobalConstraintNode.BooleanExpressionNode.ComparisonNode($left,RelationalOperator.${node.operator},$right,${node.location.toKotlinDef()})"""
    }

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode): String {
        return """ReferenceNode.SimpleReferenceNode("${node.identifier}",${node.location.toKotlinDef()})"""
    }

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode): String {
        return """ReferenceNode.NestedReferenceNode("${node.identifier}","${node.attribute}",${node.location.toKotlinDef()})"""
    }
}