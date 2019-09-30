package cz.vutbr.fit.knot.enticing.eql.compiler.parser

import cz.vutbr.fit.knot.enticing.dto.config.dsl.corpusConfig
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import org.assertj.core.api.Assertions.assertThat

fun assertParseWithAntlrWithoutErrors(input: String) {
    val (_, errors) = parseWithAntlr(input)
    assertThat(errors).isEmpty()
}

fun assertParseWithAntlrWithErrors(input: String, errorCount: Int? = null) {
    val (_, errors) = EqlCompiler(corpusConfig("dummy")).parse(input)
    println(errors)
    if (errorCount != null)
        assertThat(errors).hasSize(errorCount)
    else assertThat(errors).isNotEmpty()
}