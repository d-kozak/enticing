package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.config
import cz.vutbr.fit.knot.enticing.eql.compiler.forEachQuery
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class Mgj4QueryGeneratingVisitorTest {

    val compiler = EqlCompiler(SimpleStdoutLoggerFactory)

    @Test
    fun `queries from file`() {
        forEachQuery("semantic_ok.eql") {
            val (ast, errors) = compiler.parseAndAnalyzeQuery(it, config)
            assertThat(errors).isEmpty()
            println(ast.toMgj4Query())
            true
        }
    }

    @Nested
    inner class EscapingQueries {


        @Test
        fun url() {
            val (ast, errors) = compiler.parseAndAnalyzeQuery("person.url:'https://en.wikipedia.org/wiki/Bertrand_de_Turre'", config)
            assertThat(errors).isEmpty()
            assertThat(ast.toMgj4Query()).isEqualTo("""((nertag:person{{nertag->token}}) ^ (param0:(https\://en.wikipedia.org/wiki/Bertrand_de_Turre){{param0->token}}))""")
        }
    }

    @Nested
    @DisplayName("Examples from the Language Specification")
    inner class ExamplesFromLanguageSpec {

        @Test
        @DisplayName("Picasso visited Paris")
        fun `Query 1`() {
            val (ast, errors) = compiler.parseAndAnalyzeQuery("Picasso visited Paris", config)
            assertThat(errors).isEmpty()
            assertThat(ast.toMgj4Query()).isEqualTo("(Picasso & visited & Paris)")

        }

        @Test
        @DisplayName("""Picasso < visited < Paris""")
        fun `Query 2`() {
            val (ast, errors) = compiler.parseAndAnalyzeQuery("Picasso < visited < Paris", config)
            assertThat(errors).isEmpty()
            assertThat(ast.toMgj4Query()).isEqualTo("(Picasso < (visited < Paris))")
        }

        @Test
        @DisplayName("""(Picasso visited Paris) ctx:par""")
        fun `Query 4`() {
            val (ast, errors) = compiler.parseAndAnalyzeQuery("(Picasso visited Paris) ctx:par", config)
            assertThat(errors).isEmpty()
            assertThat(ast.toMgj4Query()).isEqualTo("((((Picasso & visited & Paris)))  - §)")
        }


        @Test
        @DisplayName("""(Picasso visited Paris)  ctx:sent""")
        fun `Query 5`() {
            val (ast, errors) = compiler.parseAndAnalyzeQuery("(Picasso visited Paris) ctx:sent", config)
            assertThat(errors).isEmpty()
            assertThat(ast.toMgj4Query()).isEqualTo("((((Picasso & visited & Paris)))  - ¶)")
        }

        @Test
        @DisplayName("""(person.name:Picasso lemma:(visit|explore) Paris) ctx:sent""")
        fun `Query 9`() {
            val (ast, errors) = compiler.parseAndAnalyzeQuery("(person.name:Picasso lemma:(visit|explore) Paris) ctx:sent", config)
            assertThat(errors).isEmpty()
            assertThat(ast.toMgj4Query()).isEqualTo("((((((nertag:person{{nertag->token}}) ^ (param2:(Picasso){{param2->token}})) & (lemma:((visit | explore)){{lemma->token}}) & Paris)))  - ¶)")
        }

        @Test
        @DisplayName("""(person.name:Picasso lemma:(visit|explore)  location.name:Paris) context:sentence""")
        fun `Query 10`() {
            val (ast, errors) = compiler.parseAndAnalyzeQuery("(person.name:Picasso lemma:(visit|explore)  location.name:Paris) context:sentence", config)
            assertThat(errors).isEmpty()
            assertThat(ast.toMgj4Query()).isEqualTo("((((((nertag:person{{nertag->token}}) ^ (param2:(Picasso){{param2->token}})) & (lemma:((visit | explore)){{lemma->token}}) & ((nertag:location{{nertag->token}}) ^ (param2:(Paris){{param2->token}})))))  - ¶)")
        }


        @Test
        @DisplayName("""(influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist)) context:paragraph && influencer.url != influencee.url""")
        fun `Query 12`() {
            val (ast, errors) = compiler.parseAndAnalyzeQuery("(influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist)) context:paragraph && influencer.url != influencee.url", config)
            assertThat(errors).isEmpty()
            assertThat(ast.toMgj4Query()).isEqualTo("(((((nertag:((person | artist)){{nertag->token}}) < (((lemma:((influence | impact)){{lemma->token}}) | ((lemma:(paid){{lemma->token}}) < (lemma:(tribute){{lemma->token}}))) < (nertag:((person | artist)){{nertag->token}})))))  - §)")
        }
    }
}