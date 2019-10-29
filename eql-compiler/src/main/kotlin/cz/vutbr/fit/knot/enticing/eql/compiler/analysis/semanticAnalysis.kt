package cz.vutbr.fit.knot.enticing.eql.compiler.analysis

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check.*
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.AgregatingListener
import cz.vutbr.fit.knot.enticing.eql.compiler.emptySymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError

val DEFAULT_CHECKS: List<EqlAstCheck<*>> = listOf(
        ProximityNumberCheck("PROX-1"),
        BasicIndexCheck("IND-1"),
        NestedIndexCheck("IND-2"),
        EntityCheck("ENT-1"),
        AttributeCheck("ENT-2"),
        AssignCheck("ASGN-1"),
        SimpleRefCheck("REF-1"),
        NestedRefCheck("REF-2"),
        ComparisonCheck("COP-1")
)


class SemanticAnalyzer(private val config: CorpusConfiguration, checks: List<EqlAstCheck<*>> = DEFAULT_CHECKS) {

    private val checksByType = checks.groupBy { it.clazz }

    fun performAnalysis(node: EqlAstNode): List<CompilerError> {
        val symbolTable = emptySymbolTable()
        val reporter = SimpleAgregatingReporter()
        node.walk(AgregatingListener({ currentNode ->
            val checks = checksByType[currentNode::class]
            checks?.forEach { it.doAnalyze(currentNode, symbolTable, reporter, config) }
        }))
        if (node is RootNode) {
            node.symbolTable = symbolTable
        }
        return reporter.reports
    }

}

