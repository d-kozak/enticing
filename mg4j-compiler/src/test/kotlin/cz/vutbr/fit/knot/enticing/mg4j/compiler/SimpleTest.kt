package cz.vutbr.fit.knot.enticing.mg4j.compiler

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.api.Test

class SimpleTest {

    @Test
    fun simple() {
        val input = "(id:ahoj cau) ~ 5 foocko:foo:bar^(bar.baz:10)^(bar.baz2:al) & (nertag:person (foo baz) - _SENT_) < aaaa (\"baz baz bar\") - _PAR_ && foocko.name < foo & id.name > bar"
        val inputStream = CharStreams.fromString(input)
        val lexer = Mg4jEqlLexer(inputStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = Mg4jEqlParser(tokenStream)

        val ast = parser.query()
        println(ast)
    }

}