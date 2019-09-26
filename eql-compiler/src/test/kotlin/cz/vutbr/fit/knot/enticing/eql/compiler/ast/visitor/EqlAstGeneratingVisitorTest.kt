package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.forEachQuery
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.parseToEqlAst
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class EqlAstGeneratingVisitorTest {
    @Nested
    @DisplayName("Queries from files, just to test that it does not crash, no assertions here yet")
    inner class QueriesFromFiles {

        @Test
        fun `valid queries`() {
            forEachQuery("valid.eql") { parseToEqlAst(it).errors.isEmpty() }
        }

        @Test
        fun `queries from the spec`() {
            forEachQuery("spec_queries.eql") { parseToEqlAst(it).errors.isEmpty() }
        }

        @Test
        fun `syntactic errors`() {
            forEachQuery("syntactic_errors.eql") { parseToEqlAst(it).errors.isNotEmpty() }
        }


    }
}