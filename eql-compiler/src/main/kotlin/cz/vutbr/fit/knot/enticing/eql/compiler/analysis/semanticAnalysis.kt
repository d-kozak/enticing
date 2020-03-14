package cz.vutbr.fit.knot.enticing.eql.compiler.analysis


import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check.*
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.AgregatingListener
import cz.vutbr.fit.knot.enticing.eql.compiler.emptySymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError

val FIRST_PASS = listOf(
        FlattenBooleanOperationsCheck("BOOL-1")
)

val SECOND_PASS: List<EqlAstCheck<*>> = listOf(
        RewriteContextRestrictionCheck("CTX-1"),
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


class SemanticAnalyzer(private val config: MetadataConfiguration, checks: List<List<EqlAstCheck<*>>> = listOf(FIRST_PASS, SECOND_PASS)) {

    private val checksByType = checks.map { it.groupBy { it.clazz } }

    fun performAnalysis(node: EqlAstNode): List<CompilerError> {
        val symbolTable = emptySymbolTable()
        val reporter = SimpleAgregatingReporter()
        if (node is RootNode) {
            node.symbolTable = symbolTable
        }

        for (pass in checksByType) {
            node.walk(AgregatingListener({ currentNode ->
                val checks = pass[currentNode::class]
                checks?.forEach { it.doAnalyze(currentNode, symbolTable, reporter, config) }
            }))
            if (reporter.reports.isNotEmpty()) break
        }

        return reporter.reports
    }
}

