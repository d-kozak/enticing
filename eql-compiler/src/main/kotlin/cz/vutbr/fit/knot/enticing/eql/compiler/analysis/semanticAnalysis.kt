package cz.vutbr.fit.knot.enticing.eql.compiler.analysis


import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check.*
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.AgregatingListener
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError

val FIRST_PASS = listOf(
        FlattenBooleanOperationsCheck("BOOL-1")
)

val SECOND_PASS: List<EqlAstCheck<*>> = listOf(
        RewriteContextRestrictionCheck("CTX-1")
)

val THIRD_PASS = listOf(
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


class SemanticAnalyzer(private val config: MetadataConfiguration, checks: List<List<EqlAstCheck<*>>> = listOf(FIRST_PASS, SECOND_PASS, THIRD_PASS)) {

    private val checksByType = checks.map { it.groupBy { it.clazz } }

    fun performAnalysis(node: RootNode): List<CompilerError> {
        checkParentRefs(node)
        val symbolTable = SymbolTable(node)
        val reporter = SimpleAgregatingReporter()
        node.symbolTable = symbolTable

        for (pass in checksByType) {
            node.walk(AgregatingListener({ currentNode ->
                val checks = pass[currentNode::class]
                checks?.forEach { it.doAnalyze(currentNode, symbolTable, reporter, config) }
            }))
            if (reporter.reports.isNotEmpty()) break
        }

        return reporter.reports
    }

    /**
     * Verify that all nodes except from the rootNode have valid parent references
     */
    @WhatIf("this check is not very precise, maybe move it into a test make more specific checks there?")
    private fun checkParentRefs(root: RootNode) {
        check(root.parent == null) { "root node should have no parent" }
        root.query.forEachNode { node ->
            check(node.parent != null) { "each node in the tree should have a valid parent reference" }
        }
        root.constraint?.forEachNode { node ->
            check(node.parent != null) { "each node in the tree should have a valid parent reference" }
        }
    }
}

