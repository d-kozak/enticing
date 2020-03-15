package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.index.collection.manager.TestDocument
import cz.vutbr.fit.knot.enticing.index.collectionManagerConfiguration
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GlobalConstraintEvaluationVisitorTest {

    private val metadata = collectionManagerConfiguration.metadataConfiguration
    protected val compiler = EqlCompiler(SimpleStdoutLoggerFactory)

    @Test
    fun `identifier lookup is working`() {
        val query = "one:=pepa two:=pepa && one != two"
        val (ast) = compiler.parseAndAnalyzeQuery(query, metadata)
        val document = TestDocument(10)
        repeat(10) {
            document["token"][it] = "pepa"
        }
        ast as RootNode

        val seq = evaluateQuery(ast, document, "token", metadata, Interval.valueOf(0, document.size - 1))
                .toList()

        check(seq.isNotEmpty()) { "empty seq will not trigger the next loop, no asserts will be executed" }

        for (match in seq) {
            val visitor = GlobalConstraintEvaluationVisitor(ast, metadata, document, match)
            assertThat(visitor.identifierMatch.keys).contains("one", "two")
            println(visitor.identifierMatch)
        }
    }

}