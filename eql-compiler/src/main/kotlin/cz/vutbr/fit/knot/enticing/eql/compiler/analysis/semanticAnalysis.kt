package cz.vutbr.fit.knot.enticing.eql.compiler.analysis

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check.AttributeCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check.EntityCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check.IndexCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check.ProximityNumberCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError

val DEFAULT_CHECKS: List<EqlAstCheck<*>> = listOf(
        ProximityNumberCheck("PROX-1"),
        IndexCheck("IND-1"),
        EntityCheck("ENT-1"),
        AttributeCheck("ENT-2")
)

class SemanticAnalyzer(private val config: CorpusConfiguration, private val checks: List<EqlAstCheck<*>> = DEFAULT_CHECKS) {
    fun performAnalysis(node: EqlAstNode): List<CompilerError> {
        val analyzer = AnalysisVisitor(checks, config)
        node.accept(analyzer)
        return analyzer.reports
    }
}

