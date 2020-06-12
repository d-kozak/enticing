package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompilerException
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.EqlParser
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.EqlVisitor
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode

fun EqlParser.RootContext.toEqlAst() = accept(EqlAstGeneratingVisitor()) as RootNode

val ParserRuleContext.location: Interval
    get() = Interval.valueOf(this.start.startIndex, this.stop.stopIndex)

internal fun fail(message: String = "Should never be called"): Nothing = throw IllegalStateException(message)

@Incomplete("escape all the necessary chars")
internal fun espaceMg4jQuery(input: String) = input.replace(":", """\:""")

class EqlAstGeneratingVisitor : EqlVisitor<AstNode> {

    override fun visit(tree: ParseTree): AstNode = tree.accept(this)

    override fun visitAlign(ctx: EqlParser.AlignContext): AstNode {
        val left = ctx.queryElem(0).accept(this) as QueryElemNode
        val right = ctx.queryElem(1).accept(this) as QueryElemNode
        return QueryElemNode.AlignNode(left, right, ctx.location)
                .also {
                    left.parent = it
                    right.parent = it
                }
    }

    override fun visitProx(ctx: EqlParser.ProxContext): AstNode {
        val node = ctx.queryElem().accept(this) as EqlAstNode
        val proximity = ctx.proximity().accept(this) as ProximityRestrictionNode
        if (node is WithProximityRestriction) {
            node.restriction = proximity
            proximity.parent = node
            return node
        } else throw EqlCompilerException("Proximity cannot be applied to this node")
    }

    override fun visitProximity(ctx: EqlParser.ProximityContext): AstNode {
        return ProximityRestrictionNode(ctx.IDENTIFIER().symbol.text, ctx.location)
    }

    override fun visitRoot(ctx: EqlParser.RootContext): AstNode {
        val query = ctx.queryElem().accept(this) as QueryElemNode
        val constraint = ctx.constraint()?.accept(this) as? ConstraintNode
        return RootNode(query, constraint, ctx.location)
                .also {
                    query.parent = it
                    if (constraint != null) constraint.parent = it
                }
    }

    override fun visitSequence(ctx: EqlParser.SequenceContext): AstNode {
        val elems = ctx.simpleQuery().map { it.accept(this) as QueryElemNode.SimpleNode }
        return QueryElemNode.SequenceNode(elems, ctx.location)
                .also { sequence ->
                    elems.forEach { it.parent = sequence }
                }
    }

    override fun visitParenQuery(ctx: EqlParser.ParenQueryContext): AstNode {
        val query = ctx.queryElem().accept(this) as QueryElemNode
        return QueryElemNode.ParenNode(query, null, ctx.location)
                .also { query.parent = it }
    }

    override fun visitNotQuery(ctx: EqlParser.NotQueryContext): AstNode {
        val query = ctx.queryElem().accept(this) as QueryElemNode
        return QueryElemNode.NotNode(query, ctx.location).also {
            query.parent = it
        }
    }

    override fun visitAnd(ctx: EqlParser.AndContext): AstNode {
        val left = ctx.queryElem(0).accept(this) as QueryElemNode
        val right = ctx.queryElem(1).accept(this) as QueryElemNode
        return QueryElemNode.BooleanNode(mutableListOf(left, right), BooleanOperator.AND, null, ctx.location)
                .also {
                    left.parent = it
                    right.parent = it
                }
    }

    override fun visitOr(ctx: EqlParser.OrContext): AstNode {
        val left = ctx.queryElem(0).accept(this) as QueryElemNode
        val right = ctx.queryElem(1).accept(this) as QueryElemNode
        return QueryElemNode.BooleanNode(mutableListOf(left, right), BooleanOperator.OR, null, ctx.location)
                .also {
                    left.parent = it
                    right.parent = it
                }
    }

    override fun visitSimple(ctx: EqlParser.SimpleContext): AstNode = ctx.simpleQuery().accept(this)

    override fun visitNext(ctx: EqlParser.NextContext): AstNode {
        val left = ctx.simpleQuery(0).accept(this) as QueryElemNode.SimpleNode
        val right = ctx.simpleQuery(1).accept(this) as QueryElemNode.SimpleNode
        return QueryElemNode.SequenceNode(listOf(left, right), ctx.location)
    }

    override fun visitIndex(ctx: EqlParser.IndexContext): AstNode {
        val indexName = ctx.IDENTIFIER().text
        val elem = ctx.queryElem().accept(this) as QueryElemNode
        return QueryElemNode.IndexNode(indexName, elem, ctx.location)
                .also {
                    elem.parent = it
                }
    }

    override fun visitAttribute(ctx: EqlParser.AttributeContext): AstNode {
        val entityName = ctx.IDENTIFIER(0).text
        val attributeName = ctx.IDENTIFIER(1).text
        val queryElem = ctx.queryElem().accept(this) as QueryElemNode
        val entityLocation = Interval.valueOf(ctx.location.from, ctx.location.from + entityName.length - 1)
        val entityNode = QueryElemNode.SimpleNode(entityName, SimpleQueryType.STRING, entityLocation)
        return QueryElemNode.AttributeNode(entityNode, attributeName, queryElem, ctx.location).also {
            queryElem.parent = it
        }
    }

    override fun visitAssign(ctx: EqlParser.AssignContext): AstNode {
        val identifier = ctx.IDENTIFIER().text
        val query = ctx.queryElem().accept(this) as QueryElemNode
        return QueryElemNode.AssignNode(identifier, query, ctx.location)
                .also {
                    query.parent = it
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
            ctx.RAW() != null -> QueryElemNode.SimpleNode(espaceMg4jQuery(ctx.RAW().text.substring(1, ctx.RAW().text.length - 1)), SimpleQueryType.STRING, ctx.location)
            else -> fail("should never be called")
        }
    }


    override fun visitOrder(ctx: EqlParser.OrderContext): AstNode {
        val left = ctx.queryElem(0).accept(this) as QueryElemNode
        val right = ctx.queryElem(1).accept(this) as QueryElemNode
        return QueryElemNode.OrderNode(left, right, null, ctx.location).also {
            left.parent = it
            right.parent = it
        }
    }

    override fun visitConstraint(ctx: EqlParser.ConstraintContext): AstNode {
        val expression = ctx.booleanExpression().accept(this) as ConstraintNode.BooleanExpressionNode
        return ConstraintNode(expression, ctx.location)
                .also {
                    expression.parent = it
                }
    }

    override fun visitBinaryExpression(ctx: EqlParser.BinaryExpressionContext): AstNode {
        val left = ctx.booleanExpression(0).accept(this) as ConstraintNode.BooleanExpressionNode
        val right = ctx.booleanExpression(1).accept(this) as ConstraintNode.BooleanExpressionNode
        val operator = if (ctx.booleanOperator().AND() != null) BooleanOperator.AND else if (ctx.booleanOperator().OR() != null) BooleanOperator.OR else fail("should never happen")
        return ConstraintNode.BooleanExpressionNode.OperatorNode(left, operator, right, ctx.location)
                .also {
                    left.parent = it
                    right.parent = it
                }
    }

    override fun visitNotExpression(ctx: EqlParser.NotExpressionContext): AstNode {
        val expression = ctx.booleanExpression().accept(this) as ConstraintNode.BooleanExpressionNode
        return ConstraintNode.BooleanExpressionNode.NotNode(expression, ctx.location)
                .also { expression.parent = it }
    }

    override fun visitSimpleComparison(ctx: EqlParser.SimpleComparisonContext): AstNode {
        return ctx.comparison().accept(this) as ConstraintNode.BooleanExpressionNode.ComparisonNode
    }

    override fun visitParenExpression(ctx: EqlParser.ParenExpressionContext): AstNode {
        val expression = ctx.booleanExpression().accept(this) as ConstraintNode.BooleanExpressionNode
        return ConstraintNode.BooleanExpressionNode.ParenNode(expression, ctx.location)
                .also { expression.parent = it }
    }


    override fun visitComparison(ctx: EqlParser.ComparisonContext): AstNode {
        val left = ctx.reference().accept(this) as ReferenceNode
        val right = ctx.referenceOrValue().accept(this) as ReferenceNode
        val operator = when {
            ctx.comparisonOperator().EQ() != null -> RelationalOperator.EQ
            ctx.comparisonOperator().NE() != null -> RelationalOperator.NE
            ctx.comparisonOperator().GE() != null -> RelationalOperator.GE
            ctx.comparisonOperator().GT() != null -> RelationalOperator.GT
            ctx.comparisonOperator().LE() != null -> RelationalOperator.LE
            ctx.comparisonOperator().LT() != null -> RelationalOperator.LT
            else -> fail("should never happen")
        }
        return ConstraintNode.BooleanExpressionNode.ComparisonNode(left, operator, right, ctx.location)
                .also {
                    left.parent = it
                    right.parent = it
                }
    }

    override fun visitReferenceOrValue(ctx: EqlParser.ReferenceOrValueContext): AstNode = when {
        ctx.reference() != null -> ctx.reference().accept(this)
        ctx.nestedReference() != null -> ctx.nestedReference().accept(this)
        else -> fail("should never happen")
    }

    override fun visitReference(ctx: EqlParser.ReferenceContext): AstNode {
        val nested = ctx.nestedReference()
        return if (nested != null) when {
            nested.IDENTIFIER() != null -> ReferenceNode.NestedReferenceNode(ctx.IDENTIFIER().text, nested.IDENTIFIER().text, ctx.location)
            nested.RAW() != null -> ReferenceNode.NestedReferenceNode(ctx.IDENTIFIER().text, nested.RAW().text, ctx.location)
            else -> fail("should never happen")

        } else ReferenceNode.SimpleReferenceNode(ctx.IDENTIFIER().text, ctx.location)

    }

    override fun visitNestedReference(ctx: EqlParser.NestedReferenceContext?): AstNode {
        fail("should never be called")
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