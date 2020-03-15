package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.config
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ContextRestriction
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DeepCopyVisitorTest {

    private val compiler = EqlCompiler(SimpleStdoutLoggerFactory)

    @Test
    fun `context restrictions is preserved`() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("hello hi ctx:par", config)
        ast as RootNode
        assertThat(errors).isEmpty()
        assertThat(ast.contextRestriction).isEqualTo(ContextRestriction.PARAGRAPH)
        assertThat((ast.deepCopy() as RootNode).contextRestriction).isEqualTo(ContextRestriction.PARAGRAPH)
    }
}