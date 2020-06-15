package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.forEachQuery
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.parseToEqlAst
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal fun parseToEqlAstOrFail(input: String): EqlAstNode {
    val (ast, errors) = parseToEqlAst(input)
    assertThat(errors).isEmpty()
    ast as EqlAstNode
    println("assertThat(ast).isEqualTo(${ast.toKotlinDef()})")
    return ast
}

@Incomplete("finish when ast is finalized")
internal class EqlAstGeneratingVisitorTest {
    @Nested
    @DisplayName("Queries from files, just to test that it does not crash, no assertions here yet")
    inner class QueriesFromFiles {
        @Test
        fun `valid queries`() {
            forEachQuery("valid.eql") { parseToEqlAst(it).errors.isEmpty() }
        }

        @Test
        fun `queries from the spec`() {
            forEachQuery("spec_queries.eql") { parseToEqlAst(it).errors.isEmpty() }
        }

        @Test
        fun `syntactic errors`() {
            forEachQuery("syntactic_errors.eql") { parseToEqlAst(it).errors.isNotEmpty() }
        }
    }

    @Nested
    inner class EscapingMg4j {

        @Test
        fun escapeUrl() {
            assertThat(espaceMg4jQuery("https://cs.wikipedia.org/wiki/Harry_Potter"))
                    .isEqualTo("""https\://cs.wikipedia.org/wiki/Harry_Potter""")
        }
    }

    @Nested
    @DisplayName("Examples from the Language Specification")
    inner class ExamplesFromLanguageSpec {

        @Test
        @DisplayName("Picasso visited Paris")
        fun `Query 1`() {
            val ast = parseToEqlAstOrFail("Picasso visited Paris")
        }

        @Test
        @DisplayName("""Picasso < visited < Paris""")
        fun `Query 2`() {
            val ast = parseToEqlAstOrFail("""Picasso < visited < Paris""")
        }

        @Test
        @DisplayName("""Picasso visited Paris ctx:par""")
        fun `Query 4`() {
            val ast = parseToEqlAstOrFail("""Picasso visited Paris ctx:par""")
        }

        @Test
        @DisplayName("""(Picasso visited Paris) ctx:par""")
        fun `Query 4v1`() {
            val ast = parseToEqlAstOrFail("""(Picasso visited Paris) ctx:par""")
        }

        @Test
        @DisplayName("""Picasso visited Paris ctx:sent""")
        fun `Query 5`() {
            val ast = parseToEqlAstOrFail("""Picasso visited Paris ctx:sent""")
        }

        @Test
        @DisplayName("""(Picasso ( visited | explored )  Paris) ctx:sent""")
        fun `Query 6`() {
            val ast = parseToEqlAstOrFail("""Picasso ( visited | explored )  Paris ctx:sent""")
        }


        @Test
        @DisplayName("""(Picasso ( lemma:visit | lemma:explore )  Paris) ctx:sent""")
        fun `Query 7`() {
            val ast = parseToEqlAstOrFail("""(Picasso ( lemma:visit | lemma:explore )  Paris) ctx:sent""")
        }

        @Test
        @DisplayName("""Picasso lemma:(visit|explore) Paris ctx:sent""")
        fun `Query 8`() {
            val ast = parseToEqlAstOrFail("""Picasso lemma:(visit|explore) Paris ctx:sent""")
        }

        @Test
        @DisplayName("""nertag:person^(person.name:Picasso) lemma:(visit|explore) Paris ctx:sent""")
        fun `Query 9`() {
            val ast = parseToEqlAstOrFail("""nertag:person^(person.name:Picasso) lemma:(visit|explore) Paris ctx:sent""")
        }

        @Test
        @DisplayName("""nertag:person^(person.name:Picasso) lemma:(visit|explore)  nertag:place^(place.name:Paris) ctx:sent""")
        fun `Query 10`() {
            val ast = parseToEqlAstOrFail("""nertag:person^(person.name:Picasso) lemma:(visit|explore)  nertag:place^(place.name:Paris) ctx:sent""")
            println(ast.toKotlinDef())
        }

        @Test
        @DisplayName("""nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) ctx:par""")
        fun `Query 11`() {
            val ast = parseToEqlAstOrFail("""nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) ctx:par""")
        }

        @Test
        @DisplayName("""influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) ctx:par && influencer.nerid != influencee.nerid""")
        fun `Query 12`() {
            val ast = parseToEqlAstOrFail("""influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) ctx:par && influencer.nerid != influencee.nerid""")
        }


        @Test
        @DisplayName("""a:=nertag:(person|artist) < lemma:visit < b:=nertag:(person|artist) nertag:place^place.name:Barcelona nertag:event^event.date:[1/1/1960..12/12/2012] ctx:par && a.nerid != b.nerid""")
        fun `Query 13`() {
            val ast = parseToEqlAstOrFail("""a:=nertag:(person|artist) < lemma:visit < b:=nertag:(person|artist) nertag:place^place.name:Barcelona nertag:event^event.date:[1/1/1960..12/12/2012] ctx:par && a.nerid != b.nerid""")
        }

        @Test
        @DisplayName("""(A B) ~ 5""")
        fun `Query 14`() {
            val ast = parseToEqlAstOrFail("""(A B) ~ 5""")
        }

        @Test
        @DisplayName("""(A | B) & C""")
        fun `Query 15`() {
            val ast = parseToEqlAstOrFail("""(A | B) & C""")
        }

        @Test
        @DisplayName("""index:(A|B|C)""")
        fun `Query 16`() {
            val ast = parseToEqlAstOrFail("""index:(A|B|C)""")
        }

        @Test
        @DisplayName("""index:A | index:B  | index:C""")
        fun `Query 17`() {
            val ast = parseToEqlAstOrFail("""index:A | index:B  | index:C""")
        }

        @Test
        @DisplayName("""date:[1/1/1970..2/2/2000]""")
        fun `Query 18`() {
            val ast = parseToEqlAstOrFail("""date:[1/1/1970..2/2/2000]""")
        }

        @Test
        @DisplayName("""nertag:person^person.age:[20..30]""")
        fun `Query 19`() {
            val ast = parseToEqlAstOrFail("""nertag:person^person.age:[20..30]""")
        }

        @Test
        @DisplayName("""nertag:person ^ person.birthplace:Brno""")
        fun `Query 20`() {
            val ast = parseToEqlAstOrFail("""nertag:person ^ person.birthplace:Brno""")
        }

        @Test
        @DisplayName("""Picasso visited | explored  Paris ctx:sent""")
        fun `Query 21`() {
            val ast = parseToEqlAstOrFail("""Picasso visited | explored  Paris ctx:sent""")
        }

        @Test
        @DisplayName("""(Picasso visited) | (explored  Paris) ctx:sent""")
        fun `Query 22`() {
            val ast = parseToEqlAstOrFail("""(Picasso visited) | (explored  Paris) ctx:sent""")
        }

    }
}