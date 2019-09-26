package cz.vutbr.fit.knot.enticing.eql.compiler.parser


import cz.vutbr.fit.knot.enticing.eql.compiler.forEachQuery
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
            assertParseWithAntlrWithoutErrors("Picasso visited Paris")
        }

        @Test
        @DisplayName("""Picasso < visited < Paris""")
        fun `Query 2`() {
            assertParseWithAntlrWithoutErrors("""Picasso < visited < Paris""")
        }

        @Test
        @DisplayName(""""Picasso visited Paris"""")
        fun `Query 3`() {
            assertParseWithAntlrWithoutErrors(""""Picasso visited Paris"""")
        }

        @Test
        @DisplayName("""Picasso visited Paris - _PAR_""")
        fun `Query 4`() {
            assertParseWithAntlrWithoutErrors("""Picasso visited Paris - _PAR_""")
        }

        @Test
        @DisplayName("""Picasso visited Paris - _SENT_""")
        fun `Query 5`() {
            assertParseWithAntlrWithoutErrors("""Picasso visited Paris - _SENT_""")
        }

        @Test
        @DisplayName("""Picasso ( visited | explored )  Paris - _SENT_""")
        fun `Query 6`() {
            assertParseWithAntlrWithoutErrors("""Picasso ( visited | explored )  Paris - _SENT_""")
        }


        @Test
        @DisplayName("""Picasso ( lemma:visit | lemma:explore )  Paris - _SENT_""")
        fun `Query 7`() {
            assertParseWithAntlrWithoutErrors("""Picasso ( lemma:visit | lemma:explore )  Paris - _SENT_""")
        }

        @Test
        @DisplayName("""Picasso lemma:(visit|explore) Paris - _SENT_""")
        fun `Query 8`() {
            assertParseWithAntlrWithoutErrors("""Picasso lemma:(visit|explore) Paris - _SENT_""")
        }

        @Test
        @DisplayName("""nertag:person^(person.name:Picasso) lemma:(visit|explore) Paris - _SENT_""")
        fun `Query 9`() {
            assertParseWithAntlrWithoutErrors("""nertag:person^(person.name:Picasso) lemma:(visit|explore) Paris - _SENT_""")
        }

        @Test
        @DisplayName("""nertag:person^(person.name:Picasso) lemma:(visit|explore)  nertag:place^(place.name:Paris) - _SENT_""")
        fun `Query 10`() {
            assertParseWithAntlrWithoutErrors("""nertag:person^(person.name:Picasso) lemma:(visit|explore)  nertag:place^(place.name:Paris) - _SENT_""")
        }

        @Test
        @DisplayName("""nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) - _PAR_""")
        fun `Query 11`() {
            assertParseWithAntlrWithoutErrors("""nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) - _PAR_""")
        }

        @Test
        @DisplayName("""influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) - _PAR_ && influencer.nerid != influencee.nerid""")
        fun `Query 12`() {
            assertParseWithAntlrWithoutErrors("""influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) - _PAR_ && influencer.nerid != influencee.nerid""")
        }


        @Test
        @DisplayName("""a:=nertag:(person|artist) < lemma:visit < b:=nertag:(person|artist) nertag:place^place.name:Barcelona nertag:event^event.date:[1/1/1960..12/12/2012] - _PAR_ && a.nerid != b.nerid""")
        fun `Query 13`() {
            assertParseWithAntlrWithoutErrors("""a:=nertag:(person|artist) < lemma:visit < b:=nertag:(person|artist) nertag:place^place.name:Barcelona nertag:event^event.date:[1/1/1960..12/12/2012] - _PAR_ && a.nerid != b.nerid""")
        }

        @Test
        @DisplayName("""(A B) ~ 5""")
        fun `Query 14`() {
            assertParseWithAntlrWithoutErrors("""(A B) ~ 5""")
        }

        @Test
        @DisplayName("""(A | B) & C""")
        fun `Query 15`() {
            assertParseWithAntlrWithoutErrors("""(A | B) & C""")
        }

        @Test
        @DisplayName("""index:(A|B|C)""")
        fun `Query 16`() {
            assertParseWithAntlrWithoutErrors("""index:(A|B|C)""")
        }

        @Test
        @DisplayName("""index:A | index:B  | index:C""")
        fun `Query 17`() {
            assertParseWithAntlrWithoutErrors("""index:A | index:B  | index:C""")
        }

        @Test
        @DisplayName("""date:[1/1/1970..2/2/2000]""")
        fun `Query 18`() {
            assertParseWithAntlrWithoutErrors("""date:[1/1/1970..2/2/2000]""")
        }

        @Test
        @DisplayName("""nertag:person^person.age:[20..30]""")
        fun `Query 19`() {
            assertParseWithAntlrWithoutErrors("""nertag:person^person.age:[20..30]""")
        }

        @Test
        @DisplayName("""nertag:person ^ person.birthplace:Brno""")
        fun `Query 20`() {
            assertParseWithAntlrWithoutErrors("""nertag:person ^ person.birthplace:Brno""")
        }

        @Test
        @DisplayName("""Picasso visited | explored  Paris - _SENT_""")
        fun `Query 21`() {
            assertParseWithAntlrWithoutErrors("""Picasso visited | explored  Paris - _SENT_""")
        }

        @Test
        @DisplayName("""(Picasso visited) | (explored  Paris) - _SENT_""")
        fun `Query 22`() {
            assertParseWithAntlrWithoutErrors("""(Picasso visited) | (explored  Paris) - _SENT_""")
        }

    }
    @Nested
    @DisplayName("Queries from files")
    inner class QueriesFromFiles {

        @Test
        fun `valid queries`() {
            forEachQuery("valid.eql") { parseWithAntlr(it).second.isEmpty() }
        }

        @Test
        fun `queries from the spec`() {
            forEachQuery("spec_queries.eql") { parseWithAntlr(it).second.isEmpty() }
        }

        @Test
        fun `syntactic errors`() {
            forEachQuery("syntactic_errors.eql") { parseWithAntlr(it).second.isNotEmpty() }
        }


    }
}