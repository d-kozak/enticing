package cz.vutbr.fit.knot.enticing.eql.compiler.parser


import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File

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
    @DisplayName("Queries from files")
    inner class QueriesFromFiles {

        @Test
        fun `valid queries`() {
            forEachQuery("valid.eql") { assertParseWithoutErrors(it) }
        }

        @Test
        fun `syntactic errors`() {
            forEachQuery("syntactic_errors.eql") { assertParseWithErrors(it) }
        }

        private fun loadFile(fileName: String): File = File(javaClass.classLoader.getResource(fileName)?.file
                ?: throw IllegalArgumentException("File with name $fileName not found in the resources folder"))

        private fun forEachQuery(fileName: String, block: (String) -> Unit) {
            for (query in loadFile(fileName).readLines()) {
                if (query.startsWith("#")) continue
                println("Processing query '$query'")
                block(query)
            }
        }
    }
}