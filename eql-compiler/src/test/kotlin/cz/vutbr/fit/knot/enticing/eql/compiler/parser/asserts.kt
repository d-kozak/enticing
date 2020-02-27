package cz.vutbr.fit.knot.enticing.eql.compiler.parser

import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.testconfig.dummyLogger
import org.assertj.core.api.Assertions.assertThat

fun assertParseWithAntlrWithoutErrors(input: String) {
    val (_, errors) = parseWithAntlr(input)
    assertThat(errors).isEmpty()
}

fun assertParseWithAntlrWithErrors(input: String, errorCount: Int? = null) {
    val (_, errors) = EqlCompiler(dummyLogger).parse(input)
    println(errors)
    if (errorCount != null)
        assertThat(errors).hasSize(errorCount)
    else assertThat(errors).isNotEmpty()
}