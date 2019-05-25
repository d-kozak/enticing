package cz.vutbr.fit.knot.enticing.mg4j.compiler.parser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Mg4jParserTest {

    @Test
    fun `Complex valid query`() {
        val input = "(id:ahoj cau) ~ 5 foocko:foo:bar^(bar.baz:10)^(bar.baz2:al) & (nertag:person (foo baz) - _SENT_) < aaaa (\"baz baz bar\") - _PAR_ && foocko.name < foo & id.name > bar"
        val ast = Mg4jParser().parse(input)
        println(ast)
    }

    @Test
    fun `Missing bracket`() {
        val input = "nertag:person (visited|killed"

        val errors = try {
            Mg4jParser().parse(input)
            throw AssertionError("Should never get here")
        } catch (ex: SyntaxErrorException) {
            ex.errors
        }
        assertThat(errors).hasSize(1)
        println(errors)
    }

    @Test
    fun `Double colon`() {
        val input = "nertag::person (visited|killed)"

        val errors = try {
            Mg4jParser().parse(input)
            throw AssertionError("Should never get here")
        } catch (ex: SyntaxErrorException) {
            ex.errors
        }
        assertThat(errors).hasSize(1)
        println(errors)
    }

}