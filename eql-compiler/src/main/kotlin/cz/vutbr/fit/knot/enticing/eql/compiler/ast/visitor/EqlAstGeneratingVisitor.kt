package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.EqlParser
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.EqlVisitor
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode

val ParserRuleContext.location: Interval
    get() = Interval.valueOf(this.start.startIndex, this.stop.stopIndex)

internal fun fail(message: String = "Should never be called"): Nothing = throw IllegalStateException(message)

@Incomplete("escape all the necessary chars")
internal fun espaceMg4jQuery(input: String) = input

class EqlAstGeneratingVisitor : EqlVisitor<AstNode> {

    override fun visit(tree: ParseTree): AstNode = tree.accept(this)

    override fun visitAlign(ctx: EqlParser.AlignContext): AstNode {
        val left = ctx.queryElem(0).accept(this) as QueryElemNode
        val right = ctx.queryElem(1).accept(this) as QueryElemNode
        return QueryElemNode.AlignNode(left, right, ctx.location)
    }

    override fun visitRestriction(ctx: EqlParser.RestrictionContext): AstNode {
        val left = ctx.queryElem(0).accept(this) as QueryElemNode
        val right = ctx.queryElem(1).accept(this) as QueryElemNode
        val type = ctx.restrictionType().accept(this) as RestrictionTypeNode
        return QueryElemNode.RestrictionNode(left, right, type, ctx.location)
    }

    override fun visitProximity(ctx: EqlParser.ProximityContext): AstNode {
        return RestrictionTypeNode.ProximityNode(ctx.IDENTIFIER().symbol.text, ctx.location)
    }

    override fun visitRoot(ctx: EqlParser.RootContext): AstNode {
        val query = ctx.query().accept(this) as QueryNode
        val constraint = ctx.globalConstraint()?.accept(this) as? GlobalConstraintNode
        return RootNode(query, constraint, ctx.location)
    }

    override fun visitQuery(ctx: EqlParser.QueryContext): AstNode {
        val nodes = ctx.queryElem().map { it.accept(this) as QueryElemNode }
        val type = ctx.restrictionType()?.accept(this) as? RestrictionTypeNode
        return QueryNode(nodes, type, ctx.location)
    }

    override fun visitSequence(ctx: EqlParser.SequenceContext): AstNode {
        val elems = ctx.queryElem().map { it.accept(this) as QueryElemNode }
        return QueryElemNode.SequenceNode(elems, ctx.location)
    }

    override fun visitParenQuery(ctx: EqlParser.ParenQueryContext): AstNode {
        val query = ctx.query().accept(this) as QueryNode
        val type = ctx.restrictionType()?.accept(this) as? RestrictionTypeNode
        return QueryElemNode.ParenNode(query, type, ctx.location)
    }

    override fun visitNotQuery(ctx: EqlParser.NotQueryContext): AstNode {
        val query = ctx.queryElem().accept(this) as QueryElemNode
        return QueryElemNode.NotNode(query, ctx.location)
    }

    override fun visitBooleanQuery(ctx: EqlParser.BooleanQueryContext): AstNode {
        val operator = if (ctx.booleanOperator().AND() != null) BooleanOperator.AND else if (ctx.booleanOperator().OR() != null) BooleanOperator.OR else {
            throw IllegalStateException("should never happen")
        }
        val left = ctx.queryElem(0).accept(this) as QueryElemNode
        val right = ctx.queryElem(1).accept(this) as QueryElemNode
        return QueryElemNode.BooleanNode(left, operator, right, ctx.location)
    }

    override fun visitIndex(ctx: EqlParser.IndexContext): AstNode {
        val indexName = ctx.IDENTIFIER().text
        val elem = ctx.queryElem().accept(this) as QueryElemNode
        return QueryElemNode.IndexNode(indexName, elem, ctx.location)
    }

    override fun visitAttribute(ctx: EqlParser.AttributeContext): AstNode {
        val entityName = ctx.IDENTIFIER(0).text
        val attributeName = ctx.IDENTIFIER(1).text
        val queryElem = ctx.queryElem().accept(this) as QueryElemNode
        return QueryElemNode.AttributeNode(entityName, attributeName, queryElem, ctx.location)
    }

    override fun visitAssign(ctx: EqlParser.AssignContext): AstNode {
        val identifier = ctx.IDENTIFIER().text
        val query = ctx.queryElem().accept(this) as QueryElemNode
        return QueryElemNode.AssignNode(identifier, query, ctx.location)
    }

    override fun visitReference(ctx: EqlParser.ReferenceContext): AstNode {
        return if (ctx.IDENTIFIER().size == 2) {
            ReferenceNode.NestedReferenceNode(ctx.IDENTIFIER(0).text, ctx.IDENTIFIER(1).text, ctx.location)
        } else {
            ReferenceNode.SimpleReferenceNode(ctx.IDENTIFIER(0).text, ctx.location)
        }
    }

    private val intIntervalRegex = """\[\s*\d+\s*\.\.\s*\d+\s*]""".toRegex()

    override fun visitSimpleQuery(ctx: EqlParser.SimpleQueryContext): AstNode {
        return when {
            ctx.IDENTIFIER() != null -> QueryElemNode.SimpleNode(ctx.IDENTIFIER().text, SimpleQueryType.STRING, ctx.location)
            ctx.ANY_TEXT() != null -> QueryElemNode.SimpleNode(ctx.ANY_TEXT().text, SimpleQueryType.STRING, ctx.location)
            ctx.interval() != null -> {
                val data = ctx.interval().text
                val intervalType = if (intIntervalRegex.matches(data)) SimpleQueryType.INT_INTERVAL else SimpleQueryType.DATE_INTERVAL
                QueryElemNode.SimpleNode(data, intervalType, ctx.location)
            }
            ctx.RAW() != null -> QueryElemNode.SimpleNode(espaceMg4jQuery(ctx.RAW().text), SimpleQueryType.STRING, ctx.location)
            else -> fail("should never be called")
        }
    }


    override fun visitOrder(ctx: EqlParser.OrderContext): AstNode {
        val left = ctx.queryElem(0).accept(this) as QueryElemNode
        val right = ctx.queryElem(1).accept(this) as QueryElemNode
        return QueryElemNode.OrderNode(left, right, ctx.location)
    }

    override fun visitContext(ctx: EqlParser.ContextContext): AstNode {
        val restrictionType = when {
            ctx.queryElem() != null -> ContextRestrictionType.Query(ctx.queryElem().accept(this) as QueryElemNode)
            ctx.PAR() != null -> ContextRestrictionType.Paragraph
            ctx.SENT() != null -> ContextRestrictionType.Sentence
            else -> fail("should never be called")
        }
        return RestrictionTypeNode.ContextNode(restrictionType, ctx.location)
    }

    override fun visitGlobalConstraint(ctx: EqlParser.GlobalConstraintContext): AstNode {
        val expression = ctx.booleanExpression().accept(this) as GlobalConstraintNode.BooleanExpressionNode
        return GlobalConstraintNode(expression, ctx.location)
    }

    override fun visitBinaryExpression(ctx: EqlParser.BinaryExpressionContext): AstNode {
        val left = ctx.booleanExpression(0).accept(this) as GlobalConstraintNode.BooleanExpressionNode
        val right = ctx.booleanExpression(1).accept(this) as GlobalConstraintNode.BooleanExpressionNode
        val operator = if (ctx.booleanOperator().AND() != null) BooleanOperator.AND else if (ctx.booleanOperator().OR() != null) BooleanOperator.OR else fail("should never happen")
        return GlobalConstraintNode.BooleanExpressionNode.OperatorNode(left, operator, right, ctx.location)
    }

    override fun visitNotExpression(ctx: EqlParser.NotExpressionContext): AstNode {
        val expression = ctx.booleanExpression().accept(this) as GlobalConstraintNode.BooleanExpressionNode
        return GlobalConstraintNode.BooleanExpressionNode.NotNode(expression, ctx.location)
    }

    override fun visitSimpleComparison(ctx: EqlParser.SimpleComparisonContext): AstNode {
        return ctx.comparison().accept(this) as GlobalConstraintNode.BooleanExpressionNode.ComparisonNode
    }

    override fun visitParenExpression(ctx: EqlParser.ParenExpressionContext): AstNode {
        val expression = ctx.booleanExpression().accept(this) as GlobalConstraintNode.BooleanExpressionNode
        return GlobalConstraintNode.BooleanExpressionNode.ParenNode(expression, ctx.location)
    }

    override fun visitComparison(ctx: EqlParser.ComparisonContext): AstNode {
        val left = ctx.reference(0).accept(this) as ReferenceNode
        val right = ctx.reference(1).accept(this) as ReferenceNode
        val operator = when {
            ctx.comparisonOperator().EQ() != null -> RelationalOperator.EQ
            ctx.comparisonOperator().NE() != null -> RelationalOperator.NE
            ctx.comparisonOperator().GE() != null -> RelationalOperator.GE
            ctx.comparisonOperator().GT() != null -> RelationalOperator.GT
            ctx.comparisonOperator().LE() != null -> RelationalOperator.LE
            ctx.comparisonOperator().LT() != null -> RelationalOperator.LT
            else -> fail("should never happen")
        }
        return GlobalConstraintNode.BooleanExpressionNode.ComparisonNode(left, operator, right, ctx.location)
    }

    override fun visitBooleanOperator(ctx: EqlParser.BooleanOperatorContext?): AstNode {
        fail("should never be called")
    }

    override fun visitChildren(node: RuleNode?): AstNode {
        fail("should never be called")
    }

    override fun visitComparisonOperator(ctx: EqlParser.ComparisonOperatorContext): AstNode {
        fail("should never be called")
    }

    override fun visitTerminal(node: TerminalNode): AstNode {
        fail("should never be called")
    }

    override fun visitErrorNode(node: ErrorNode): AstNode {
        fail("should never be called")
    }

    override fun visitInterval(ctx: EqlParser.IntervalContext): AstNode {
        fail("should never be called")
    }
}