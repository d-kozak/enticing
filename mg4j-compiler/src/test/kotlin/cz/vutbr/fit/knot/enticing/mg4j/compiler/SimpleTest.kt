package cz.vutbr.fit.knot.enticing.mg4j.compiler

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.api.Test

class SimpleTest {

    @Test
    fun simple() {
        val inputStream = CharStreams.fromString("1.person = pepa and 2.person < john & 2.am < foo")
        val lexer = Mg4jLexer(inputStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = Mg4jParser(tokenStream)

        val ast = parser.node()
        println(ast)
    }

    @Test
    fun `With error`() {
        // missing John
        val inputStream = CharStreams.fromString("1.person = pepa and 2.person <  & 2.am < foo")
        val lexer = Mg4jLexer(inputStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = Mg4jParser(tokenStream)

        val ast = parser.node()
        println(ast)
    }
}