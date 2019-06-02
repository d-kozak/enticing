package cz.vutbr.fit.knot.enticing.eql.compiler.parser

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.AstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import org.assertj.core.api.Assertions

fun assertParseWithoutErrors(input: String): AstNode {
    val (parseTree, errors) = EqlCompiler().parse(input)
    Assertions.assertThat(errors).isEmpty()
    return parseTree
}

fun assertParseWithErrors(input: String, errorCount: Int): ParsedQuery {
    val parsedQuery = EqlCompiler().parse(input)
    println(parsedQuery.errors)
    Assertions.assertThat(parsedQuery.errors).hasSize(errorCount)
    return parsedQuery
}