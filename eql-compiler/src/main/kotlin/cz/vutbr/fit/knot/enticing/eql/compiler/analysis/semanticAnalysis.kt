package cz.vutbr.fit.knot.enticing.eql.compiler.analysis

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check.*
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.AgregatingListener
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.emptySymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError

val DEFAULT_CHECKS: List<EqlAstCheck<*>> = listOf(
        ProximityNumberCheck("PROX-1"),
        IndexCheck("IND-1"),
        EntityCheck("ENT-1"),
        AttributeCheck("ENT-2"),
        AssignCheck("ASGN-1"),
        SimpleRefCheck("REF-1"),
        NestedRefCheck("REF-2")
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
        return reporter.reports
    }

}

