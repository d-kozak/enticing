package cz.vutbr.fit.knot.enticing.mg4j.compiler.parser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Mg4jParserTest {

    @Test
    fun `Complex valid query`() {
        val input = "(id:ahoj cau) ~ 5 foocko:foo:bar^(bar.baz:10)^(bar.baz2:al) & (nertag:person (foo baz) - _SENT_) < aaaa (\"baz baz bar\") - _PAR_ && foocko.name < foo & id.name > bar"
        val (_, errors) = Mg4jParser().parse(input)
        assertThat(errors).isEmpty()
    }

    @Test
    fun `Another quite complex query`() {
        val input = "nertag:person^(person.name:Jogn) (visited|entered)ahoj && ahoj.cau > cau.ahoj & foo.baz > baz.gaz | !foo.foo > foo.foo"
        val (_, errors) = Mg4jParser().parse(input)
        assertThat(errors).isEmpty()
    }

    @Test
    fun `Missing bracket`() {
        val input = "nertag:person (visited|killed"


        val (_, errors) = Mg4jParser().parse(input)

        assertThat(errors).hasSize(1)
        println(errors)
    }

    @Test
    fun `Double colon`() {
        val input = "nertag::person (visited|killed)"

        val (_, errors) = Mg4jParser().parse(input)

        assertThat(errors).hasSize(1)
        println(errors)
    }

}