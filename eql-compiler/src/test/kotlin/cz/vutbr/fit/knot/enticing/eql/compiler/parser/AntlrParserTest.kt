package cz.vutbr.fit.knot.enticing.eql.compiler.parser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AntlrParserTest {

    @Nested
    inner class MeaningfulWantedQueries {

        @Test
        fun `Query 1`() {
            val input = """nertag:(person|artist) < (lemma:(influced|impact)) | (lemma:paid < lemma:tribute)"""
            val (_, errors) = EqlCompiler().parse(input)
            assertThat(errors).isEmpty()
        }

        @Test
        fun `Query 2`() {
            val input = """nertag:(person|artist) & lemma:(influced|impact) | (lemma:paid < lemma:tribute)"""
            val (_, errors) = EqlCompiler().parse(input)
            assertThat(errors).isEmpty()
        }

        @Test
        fun `Query 3`() {
            val input = """nertag:person ^ birthdate:[1/1/1936..]"""
            val (_, errors) = EqlCompiler().parse(input)
            assertThat(errors).isEmpty()
        }

        @Test
        fun `Query 4`() {
            val input = """nertag:person ^ birthdate:[..1/1/1936]"""
            val (_, errors) = EqlCompiler().parse(input)
            assertThat(errors).isEmpty()
        }

    }


    @Nested
    inner class SyntacticErrors {
        @Test
        fun `Missing bracket`() {
            val input = "nertag:person (visited|killed"

            val (_, errors) = EqlCompiler().parse(input)

            assertThat(errors).hasSize(1)
            println(errors)
        }

        @Test
        fun `Double colon`() {
            val input = "nertag::person (visited|killed)"

            val (_, errors) = EqlCompiler().parse(input)

            assertThat(errors).hasSize(1)
            println(errors)
        }
    }


    @Nested
    inner class StressTest {
        @Test
        fun `Complex valid query`() {
            val input = """(id:ahoj cau) ~ 5 foocko:=foo:bar^(bar.baz:10)^(bar.baz2:al) & (nertag:person (foo baz)) < aaaa ("baz baz bar") - _PAR_ && foocko.name < foo & id.name > bar"""
            val (_, errors) = EqlCompiler().parse(input)
            assertThat(errors).isEmpty()
        }

        @Test
        fun `Another quite complex query`() {
            val input = "nertag:person^(person.name:Jogn) (visited|entered)ahoj && ahoj.cau > cau.ahoj & foo.baz > baz.gaz | !foo.foo > foo.foo"
            val (_, errors) = EqlCompiler().parse(input)
            assertThat(errors).isEmpty()
        }

        @Test
        fun `Another query`() {
            val input = "pepa:=(nertag:person | nertag:artist) < (lemma:influence | lemma:impact) < honza:=(nertag:person | nertag:artist) - _SENT_ && pepa.nerid != honza.nerid"
            val (_, errors) = EqlCompiler().parse(input)
            assertThat(errors).isEmpty()
        }

        @Test
        fun `yet another query`() {
            val input = """(pepa := nertag:person^(birthplace:john)) | "john:=john ferda" &&  pepa.name != john.name"""
            val (_, errors) = EqlCompiler().parse(input)
            assertThat(errors).isEmpty()
        }
    }



}