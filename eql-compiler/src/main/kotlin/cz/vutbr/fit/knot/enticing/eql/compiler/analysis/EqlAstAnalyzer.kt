package cz.vutbr.fit.knot.enticing.eql.compiler.analysis

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

abstract class EqlAstCheck<AstNote : EqlAstNode>(internal val clazz: KClass<AstNote>, private val reporter: Reporter) {
    internal fun doAnalyze(node: EqlAstNode) = analyze(clazz.cast(node))
    abstract fun analyze(n: AstNote)
}

interface Reporter {
    fun info(message: String, location: Interval)
    fun warning(message: String, location: Interval)
    fun error(message: String, location: Interval)
}

class SimpleAgregatingReporter : Reporter {

    internal val messages = mutableListOf<String>()

    override fun info(message: String, location: Interval) {
        messages.add("INFO::$location::$message")
    }

    override fun warning(message: String, location: Interval) {
        messages.add("WARN::$location::$message")
    }

    override fun error(message: String, location: Interval) {
        messages.add("ERR::$location::$message")
    }
}

private class AnalysisVisitor(checks: List<EqlAstCheck<*>>, private val reporter: Reporter) : EqlVisitor<Unit> {
    private val checksByType = checks.groupBy { it.clazz }

    fun <T : EqlAstNode> runChecksFor(node: T) {
        val checks = checksByType[node::class]
        checks?.forEach { it.doAnalyze(node) }
    }

    override fun visitRootNode(node: RootNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemRestrinctionNode(node: QueryElemNode.RestrictionNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {
        runChecksFor(node)
    }

    override fun visitQueryNode(node: QueryNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode) {
        runChecksFor(node)
    }

    override fun visitGlobalContraintNode(node: GlobalConstraintNode) {
        runChecksFor(node)
    }

    override fun visitConstraintBooleanExpressionNotNode(node: GlobalConstraintNode.BooleanExpressionNode.NotNode) {
        runChecksFor(node)
    }

    override fun visitConstraintBooleanExpressionParenNode(node: GlobalConstraintNode.BooleanExpressionNode.ParenNode) {
        runChecksFor(node)
    }

    override fun visitConstraintBooleanExpressionOperatorNode(node: GlobalConstraintNode.BooleanExpressionNode.OperatorNode) {
        runChecksFor(node)
    }

    override fun visitConstraintBooleanExpressionComparisonNode(node: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode) {
        runChecksFor(node)
    }

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode) {
        runChecksFor(node)
    }

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode) {
        runChecksFor(node)
    }

    override fun visitRestrictionProximityNode(node: RestrictionTypeNode.ProximityNode) {
        runChecksFor(node)
    }

    override fun visitRestrictionContextNode(node: RestrictionTypeNode.ContextNode) {
        runChecksFor(node)
    }
}