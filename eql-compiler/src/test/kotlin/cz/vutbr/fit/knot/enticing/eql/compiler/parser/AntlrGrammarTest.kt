package cz.vutbr.fit.knot.enticing.eql.compiler.parser

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AntlrGrammarTest {

    @Nested
    @DisplayName("Examples from the Language Specification")
    inner class ExamplesFromLanguageSpec {

        @Test
        @DisplayName("Picasso visited Paris")
        fun `Query 1`() {
            assertParseWithoutErrors("Picasso visited Paris")
        }

        @Test
        @DisplayName("""Picasso < visited < Paris""")
        fun `Query 2`() {
            assertParseWithoutErrors("""Picasso < visited < Paris""")
        }

        @Test
        @DisplayName(""""Picasso visited Paris"""")
        fun `Query 3`() {
            assertParseWithoutErrors(""""Picasso visited Paris"""")
        }

        @Test
        @DisplayName("""Picasso visited Paris - _PAR_""")
        fun `Query 4`() {
            assertParseWithoutErrors("""Picasso visited Paris - _PAR_""")
        }

        @Test
        @DisplayName("""Picasso visited Paris - _SENT_""")
        fun `Query 5`() {
            assertParseWithoutErrors("""Picasso visited Paris - _SENT_""")
        }

        @Test
        @DisplayName("""Picasso ( visited | explored )  Paris - _SENT_""")
        fun `Query 6`() {
            assertParseWithoutErrors("""Picasso ( visited | explored )  Paris - _SENT_""")
        }


        @Test
        @DisplayName("""Picasso ( lemma:visit | lemma:explore )  Paris - _SENT_""")
        fun `Query 7`() {
            assertParseWithoutErrors("""Picasso ( lemma:visit | lemma:explore )  Paris - _SENT_""")
        }

        @Test
        @DisplayName("""Picasso lemma:(visit|explore) Paris - _SENT_""")
        fun `Query 8`() {
            assertParseWithoutErrors("""Picasso lemma:(visit|explore) Paris - _SENT_""")
        }

        @Test
        @DisplayName("""nertag:person^(person.name:Picasso) lemma:(visit|explore) Paris - _SENT_""")
        fun `Query 9`() {
            assertParseWithoutErrors("""nertag:person^(person.name:Picasso) lemma:(visit|explore) Paris - _SENT_""")
        }

        @Test
        @DisplayName("""nertag:person^(person.name:Picasso) lemma:(visit|explore)  nertag:place^(place.name:Paris) - _SENT_""")
        fun `Query 10`() {
            assertParseWithoutErrors("""nertag:person^(person.name:Picasso) lemma:(visit|explore)  nertag:place^(place.name:Paris) - _SENT_""")
        }

        @Test
        @DisplayName("""nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) - _PAR_""")
        fun `Query 11`() {
            assertParseWithoutErrors("""nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) - _PAR_""")
        }

        @Test
        @DisplayName("""influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) - _PAR_ && influencer.nerid != influencee.nerid""")
        fun `Query 12`() {
            assertParseWithoutErrors("""influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) - _PAR_ && influencer.nerid != influencee.nerid""")
        }


        @Test
        @DisplayName("""a:=nertag:(person|artist) < lemma:visit < b:=nertag:(person|artist) nertag:place^place.name:Barcelona nertag:event^event.date:[1/1/1960..12/12/2012] - _PAR_ && a.nerid != b.nerid""")
        fun `Query 13`() {
            assertParseWithoutErrors("""a:=nertag:(person|artist) < lemma:visit < b:=nertag:(person|artist) nertag:place^place.name:Barcelona nertag:event^event.date:[1/1/1960..12/12/2012] - _PAR_ && a.nerid != b.nerid""")
        }

        @Test
        @DisplayName("""A B ~ 5""")
        fun `Query 14`() {
            assertParseWithoutErrors("""A B ~ 5""")
        }

        @Test
        @DisplayName("""(A | B) & C""")
        fun `Query 15`() {
            assertParseWithoutErrors("""(A | B) & C""")
        }

        @Test
        @DisplayName("""index:(A|B|C)""")
        fun `Query 16`() {
            assertParseWithoutErrors("""index:(A|B|C)""")
        }

        @Test
        @DisplayName("""index:A | index:B  | index:C""")
        fun `Query 17`() {
            assertParseWithoutErrors("""index:A | index:B  | index:C""")
        }

        @Test
        @DisplayName("""date:[1/1/1970..2/2/2000]""")
        fun `Query 18`() {
            assertParseWithoutErrors("""date:[1/1/1970..2/2/2000]""")
        }

        @Test
        @DisplayName("""nertag:person^person.age:[20..30]""")
        fun `Query 19`() {
            assertParseWithoutErrors("""nertag:person^person.age:[20..30]""")
        }

        @Test
        @DisplayName("""nertag:person ^ person.birthplace:Brno""")
        fun `Query 20`() {
            assertParseWithoutErrors("""nertag:person ^ person.birthplace:Brno""")
        }

        @Test
        @DisplayName("""Picasso visited | explored  Paris - _SENT_""")
        fun `Query 21`() {
            assertParseWithoutErrors("""Picasso visited | explored  Paris - _SENT_""")
        }

        @Test
        @DisplayName("""(Picasso visited) | (explored  Paris) - _SENT_""")
        fun `Query 22`() {
            assertParseWithoutErrors("""(Picasso visited) | (explored  Paris) - _SENT_""")
        }

    }

    @Nested
    @DisplayName("Meaningful queries testing certain parts of the grammar")
    inner class MeaningfulQueries {

        @Test
        @DisplayName("nertag:(person|artist) < lemma:(influced|impact) | (lemma:paid < lemma:tribute)")
        fun `Query 1`() {
            assertParseWithoutErrors("""nertag:(person|artist) < lemma:(influced|impact) | (lemma:paid < lemma:tribute)""")

        }

        @Test
        @DisplayName("nertag:person ^ person.birthdate:[1/1/1936..]")
        fun `Query 2`() {
            assertParseWithoutErrors("""nertag:person ^ person.birthdate:[1/1/1936..]""")
        }

        @Test
        @DisplayName("nertag:person ^ person.birthdate:[..1/1/1936]")
        fun `Query 3`() {
            assertParseWithoutErrors("nertag:person ^ person.birthdate:[..1/1/1936]")
        }

        @Test
        @DisplayName("""work ^ pos:noun""")
        fun `Query 4`() {
            assertParseWithoutErrors("""work ^ pos:noun""")
        }

        @Test
        @DisplayName("""john ^ pos:noun ^ nertag:person""")
        fun `Query 5`() {
            assertParseWithoutErrors("""john ^ pos:noun ^ nertag:person""")
        }
    }


    @Nested
    @DisplayName("Invalid queries")
    inner class SyntacticErrors {
        @Test
        fun `Missing bracket`() {
            assertParseWithErrors("nertag:person (visited|killed", 1)
        }

        @Test
        fun `Double colon`() {
            assertParseWithErrors("nertag::person (visited|killed)", 1)
        }
    }


    @Nested
    @DisplayName("Complex, but meaningless, queries that should be parsed without errors")
    inner class StressTest {
        @Test
        fun `big query 1`() {
            assertParseWithoutErrors("""(id:ahoj cau) ~ 5 foocko:=foo:bar^(bar.baz:10)^(bar.baz2:al) & (nertag:person (foo baz)) < aaaa ("baz baz bar") - _PAR_ && foocko.name != foo.foo & id.name = bar.bar""")
        }

        @Test
        fun `big query 2`() {
            assertParseWithoutErrors("""nertag:person^(person.name:Jogn) (visited|entered)ahoj && ahoj.cau = cau.ahoj & foo.baz != baz.gaz | !foo.foo != foo.foo""")
        }

        @Test
        fun `big query 3`() {
            assertParseWithoutErrors("""pepa:=(nertag:person | nertag:artist) < (lemma:influence | lemma:impact) < honza:=(nertag:person | nertag:artist) - _SENT_ && pepa.nerid != honza.nerid""")
        }

        @Test
        fun `big query 4`() {
            assertParseWithoutErrors("""(pepa := nertag:person^(birthplace:john)) | "john:=john ferda" &&  pepa.name != john.name""")
        }
    }



}