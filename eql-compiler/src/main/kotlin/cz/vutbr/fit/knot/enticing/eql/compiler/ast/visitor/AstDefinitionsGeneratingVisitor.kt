package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*

/**
 * Creates a string that should contain a piece of valid Kotlin code that creates the AST nodes ( e.g. for bootstraping tests - manual AST creation is time consuming)
 */

internal fun Interval.toKotlinDef() = "Interval.valueOf(${this.from},${this.to})"

fun EqlAstNode.toKotlinDef() = this.accept(AstDefinitionsGeneratingVisitor())

class AstDefinitionsGeneratingVisitor : EqlVisitor<String> {

    override fun visitRootNode(rootNode: RootNode): String {
        val query = rootNode.query.accept(this)
        val constraint = rootNode.constraint?.accept(this) ?: "null"
        return "RootNode($query,$constraint,${rootNode.location.toKotlinDef()})"
    }

    override fun visitQueryElemNotNode(notNode: QueryElemNode.NotNode): String {
        val elem = notNode.elem.accept(this)
        return "QueryElemNode.NotNode($elem,${notNode.location.toKotlinDef()})"
    }

    override fun visitQueryElemAssignNode(assignNode: QueryElemNode.AssignNode): String {
        val elem = assignNode.elem.accept(this)
        return """"QueryElemNode.AssignNode("${assignNode.identifier}",$elem,${assignNode.location.toKotlinDef()})"""
    }

    override fun visitQueryElemAlignNode(alignNode: QueryElemNode.AlignNode): String {
        val left = alignNode.left.accept(this)
        val right = alignNode.right.accept(this)
        return """QueryElemNode.AlignNode($left,$right,${alignNode.location.toKotlinDef()})"""
    }

    override fun visitQueryElemSimpleNode(simpleNode: QueryElemNode.SimpleNode): String {
        return """QueryElemNode.SimpleNode("${simpleNode.content}",SimpleQueryType.${simpleNode.type},${simpleNode.location.toKotlinDef()})"""
    }

    override fun visitQueryElemIndexNode(indexNode: QueryElemNode.IndexNode): String {
        val elem = indexNode.elem.accept(this)
        return """QueryElemNode.IndexNode("${indexNode.index}",$elem,${indexNode.location.toKotlinDef()})"""
    }

    override fun visitQueryElemAttributeNode(attributeNode: QueryElemNode.AttributeNode): String {
        val elem = attributeNode.elem.accept(this)
        return """QueryElemNode.AttributeNode("${attributeNode.entity}","${attributeNode.attribute}",$elem,${attributeNode.location.toKotlinDef()})"""
    }

    override fun visitQueryNode(queryNode: QueryNode): String {
        val query = queryNode.query.joinToString(",") { it.accept(this) }
        val context = queryNode.restriction?.accept(this) ?: "null"
        return """QueryNode(listOf($query),$context,${queryNode.location.toKotlinDef()})"""
    }

    override fun visitQueryElemParenNode(parenNode: QueryElemNode.ParenNode): String {
        val query = parenNode.query.accept(this)
        val restriction = parenNode.restriction?.accept(this) ?: "null"
        return """QueryElemNode.ParenNode($query,$restriction,${parenNode.location.toKotlinDef()})"""
    }

    override fun visitQueryElemBooleanNode(booleanNode: QueryElemNode.BooleanNode): String {
        val left = booleanNode.left.accept(this)
        val right = booleanNode.right.accept(this)
        return """QueryElemNode.BooleanNode($left,BooleanOperator.${booleanNode.operator},$right,${booleanNode.location.toKotlinDef()})"""
    }

    override fun visitQueryElemOrderNode(orderNode: QueryElemNode.OrderNode): String {
        val left = orderNode.left.accept(this)
        val right = orderNode.right.accept(this)
        return "QueryElemNode.OrderNode($left,$right,${orderNode.location.toKotlinDef()})"
    }

    override fun visitQueryElemSequenceNode(sequenceNode: QueryElemNode.SequenceNode): String {
        val elems = sequenceNode.elems.joinToString(",") { it.accept(this) }
        return "QueryElemNode.SequenceNode(listOf($elems),${sequenceNode.location.toKotlinDef()})"
    }

    override fun visitRestrictionProximityNode(proximityNode: RestrictionTypeNode.ProximityNode): String {
        return """RestrictionTypeNode.ProximityNode("${proximityNode.distance}",${proximityNode.location.toKotlinDef()})"""
    }

    override fun visitRestrictionContextNode(contextNode: RestrictionTypeNode.ContextNode): String {
        val restriction = when (contextNode.restriction) {
            is ContextRestrictionType.Paragraph -> "ContextRestrictionType.Paragraph"
            is ContextRestrictionType.Sentence -> "ContextRestrictionType.Sentence"
            is ContextRestrictionType.Query -> "ContextRestrictionType.Query(${contextNode.restriction.query.accept(this)})"
        }
        return "RestrictionTypeNode.ContextNode($restriction,${contextNode.location.toKotlinDef()})"
    }

    override fun visitQueryElemRestrinctionNode(restrictionNode: QueryElemNode.RestrictionNode): String {
        val left = restrictionNode.left.accept(this)
        val right = restrictionNode.right.accept(this)
        val type = restrictionNode.type.accept(this)
        return """QueryElemNode.RestrictionNode($left,$right,$type,${restrictionNode.location.toKotlinDef()})"""
    }

    override fun visitGlobalContraintNode(globalConstraintNode: GlobalConstraintNode): String {
        val expression = globalConstraintNode.expression.accept(this)
        return """GlobalConstraintNode($expression,${globalConstraintNode.location.toKotlinDef()})"""
    }

    override fun visitConstraintBooleanExpressionNotNode(notNode: GlobalConstraintNode.BooleanExpressionNode.NotNode): String {
        val elem = notNode.elem.accept(this)
        return """GlobalConstraintNode.BooleanExpressionNode.NotNode($elem,${notNode.location.toKotlinDef()})"""
    }

    override fun visitConstraintBooleanExpressionParenNode(parenNode: GlobalConstraintNode.BooleanExpressionNode.ParenNode): String {
        val elem = parenNode.expression.accept(this)
        return """GlobalConstraintNode.BooleanExpressionNode.ParenNode($elem,${parenNode.location.toKotlinDef()})"""
    }

    override fun visitConstraintBooleanExpressionOperatorNode(operatorNode: GlobalConstraintNode.BooleanExpressionNode.OperatorNode): String {
        val left = operatorNode.left.accept(this)
        val right = operatorNode.right.accept(this)
        return """GlobalConstraintNode.BooleanExpressionNode.OperatorNode($left,BooleanOperator.${operatorNode.operator},$right,${operatorNode.location.toKotlinDef()})"""
    }

    override fun visitConstraintBooleanExpressionComparisonNode(comparisonNode: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode): String {
        val left = comparisonNode.left.accept(this)
        val right = comparisonNode.right.accept(this)
        return """GlobalConstraintNode.BooleanExpressionNode.ComparisonNode($left,RelationalOperator.${comparisonNode.operator},$right,${comparisonNode.location.toKotlinDef()})"""
    }

    override fun visitSimpleReferenceNode(simpleReference: ReferenceNode.SimpleReferenceNode): String {
        return """ReferenceNode.SimpleReferenceNode("${simpleReference.identifier}",${simpleReference.location.toKotlinDef()})"""
    }

    override fun visitNestedReferenceNode(nestedReference: ReferenceNode.NestedReferenceNode): String {
        return """ReferenceNode.NestedReferenceNode("${nestedReference.identifier}","${nestedReference.attribute}",${nestedReference.location.toKotlinDef()})"""
    }
}