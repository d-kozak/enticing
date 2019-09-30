package cz.vutbr.fit.knot.enticing.eql.compiler.analysis

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.SemanticError
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.Severity
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

abstract class EqlAstCheck<AstNote : EqlAstNode>(val id:String,internal val clazz: KClass<AstNote>) {
    internal fun doAnalyze(node: EqlAstNode, reporter: Reporter, corpusConfiguration: CorpusConfiguration) = analyze(clazz.cast(node), corpusConfiguration, reporter)
    abstract fun analyze(node: AstNote, corpusConfiguration: CorpusConfiguration, reporter: Reporter)
}

interface Reporter {
    val reports: List<CompilerError>

    fun info(message: String, location: Interval,analysisId:String)
    fun warning(message: String, location: Interval,analysisId:String)
    fun error(message: String, location: Interval,analysisId:String)
}

class SimpleAgregatingReporter : Reporter {

    override val reports = mutableListOf<CompilerError>()

    override fun info(message: String, location: Interval,analysisId:String) {
        reports.add(SemanticError(message, location, Severity.INFO,analysisId))
    }

    override fun warning(message: String, location: Interval,analysisId:String) {
        reports.add(SemanticError(message, location, Severity.WARN,analysisId))
    }

    override fun error(message: String, location: Interval,analysisId:String) {
        reports.add(SemanticError(message, location, Severity.ERROR,analysisId))
    }
}

internal class AnalysisVisitor(checks: List<EqlAstCheck<*>>, private val corpusConfiguration: CorpusConfiguration, private val reporter: Reporter = SimpleAgregatingReporter()) : EqlVisitor<Unit> {
    private val checksByType = checks.groupBy { it.clazz }

    val reports: List<CompilerError>
        get() = reporter.reports

    fun <T : EqlAstNode> runChecksFor(node: T) {
        val checks = checksByType[node::class]
        checks?.forEach { it.doAnalyze(node, reporter, corpusConfiguration) }
    }

    override fun visitRootNode(node: RootNode) {
        runChecksFor(node)
        node.query.accept(this)
        node.constraint?.accept(this)
    }

    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode) {
        runChecksFor(node)
        node.elem.accept(this)
    }

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode) {
        runChecksFor(node)
        node.elem.accept(this)
    }

    override fun visitQueryElemRestrinctionNode(node: QueryElemNode.RestrictionNode) {
        runChecksFor(node)
        node.left.accept(this)
        node.right.accept(this)
        node.type.accept(this)
    }

    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode) {
        runChecksFor(node)
        node.elem.accept(this)
    }

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {
        runChecksFor(node)
        node.elem.accept(this)
    }

    override fun visitQueryNode(node: QueryNode) {
        runChecksFor(node)
        node.query.forEach { it.accept(this) }
        node.restriction?.accept(this)
    }

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode) {
        runChecksFor(node)
        node.query.accept(this)
        node.restriction?.accept(this)
    }

    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode) {
        runChecksFor(node)
        node.left.accept(this)
        node.right.accept(this)
    }

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode) {
        runChecksFor(node)
        node.left.accept(this)
        node.right.accept(this)
    }

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode) {
        runChecksFor(node)
        node.elems.forEach { it.accept(this) }
    }

    override fun visitGlobalContraintNode(node: GlobalConstraintNode) {
        runChecksFor(node)
        node.expression.accept(this)
    }

    override fun visitConstraintBooleanExpressionNotNode(node: GlobalConstraintNode.BooleanExpressionNode.NotNode) {
        runChecksFor(node)
        node.elem.accept(this)
    }

    override fun visitConstraintBooleanExpressionParenNode(node: GlobalConstraintNode.BooleanExpressionNode.ParenNode) {
        runChecksFor(node)
        node.expression.accept(this)
    }

    override fun visitConstraintBooleanExpressionOperatorNode(node: GlobalConstraintNode.BooleanExpressionNode.OperatorNode) {
        runChecksFor(node)
        node.left.accept(this)
        node.right.accept(this)
    }

    override fun visitConstraintBooleanExpressionComparisonNode(node: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode) {
        runChecksFor(node)
        node.left.accept(this)
        node.right.accept(this)
    }

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode) {
        runChecksFor(node)
    }

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode) {
        runChecksFor(node)
    }

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode) {
        runChecksFor(node)
        node.left.accept(this)
        node.right.accept(this)
    }

    override fun visitRestrictionProximityNode(node: RestrictionTypeNode.ProximityNode) {
        runChecksFor(node)
    }

    override fun visitRestrictionContextNode(node: RestrictionTypeNode.ContextNode) {
        runChecksFor(node)
        if (node.restriction is ContextRestrictionType.Query) node.restriction.query.accept(this)
    }
}