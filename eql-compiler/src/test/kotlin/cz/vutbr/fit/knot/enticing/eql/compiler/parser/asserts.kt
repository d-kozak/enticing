package cz.vutbr.fit.knot.enticing.eql.compiler.parser

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import org.assertj.core.api.Assertions.assertThat

fun assertParseWithoutErrors(input: String): AstNode {
    val (parseTree, errors) = EqlCompiler().parse(input)
    assertThat(errors).isEmpty()
    return parseTree
}

fun assertParseWithErrors(input: String, errorCount: Int? = null): ParsedQuery {
    val parsedQuery = EqlCompiler().parse(input)
    println(parsedQuery.errors)
    if (errorCount != null)
        assertThat(parsedQuery.errors).hasSize(errorCount)
    else assertThat(parsedQuery.errors).isNotEmpty()
    return parsedQuery
}