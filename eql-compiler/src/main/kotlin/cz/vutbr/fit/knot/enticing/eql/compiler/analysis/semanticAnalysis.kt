package cz.vutbr.fit.knot.enticing.eql.compiler.analysis

import com.ibm.icu.text.SymbolTable
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check.*
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.emptySymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError

val DEFAULT_CHECKS: List<EqlAstCheck<*>> = listOf(
        ProximityNumberCheck("PROX-1"),
        IndexCheck("IND-1"),
        EntityCheck("ENT-1"),
        AttributeCheck("ENT-2"),
        AssignCheck("ASGN-1")
)

class SemanticAnalyzer(private val config: CorpusConfiguration, private val checks: List<EqlAstCheck<*>> = DEFAULT_CHECKS) {
    fun performAnalysis(node: EqlAstNode): List<CompilerError> {
        val symbolTable = emptySymbolTable()
        val analyzer = AnalysisVisitor(checks, symbolTable, config)
        node.accept(analyzer)
        return analyzer.reports
    }


}

