package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import cz.vutbr.fit.knot.enticing.eql.compiler.forEachQuery
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.parseToEqlAst
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal fun parseToEqlAstOrFail(input: String): EqlAstNode {
    val (ast, errors) = parseToEqlAst(input)
    assertThat(errors).isEmpty()
    return ast as EqlAstNode
}

@Incomplete("fish when ast is finalized")
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
    @DisplayName("Examples from the Language Specification")
    inner class ExamplesFromLanguageSpec {

        @Test
        @DisplayName("Picasso visited Paris")
        fun `Query 1`() {
            val ast = parseToEqlAstOrFail("Picasso visited Paris")
            assertThat(ast)
                    .isEqualTo(RootNode(QueryNode(listOf(QueryElemNode.SimpleNode("Picasso", SimpleQueryType.STRING, Interval.valueOf(0, 6)),
                            QueryElemNode.SimpleNode("visited", SimpleQueryType.STRING, Interval.valueOf(8, 14)),
                            QueryElemNode.SimpleNode("Paris", SimpleQueryType.STRING, Interval.valueOf(16, 20))
                    ), null, Interval.Companion.valueOf(0, 20)), null,
                            Interval.valueOf(0, 20)))
        }

        @Test
        @DisplayName("""Picasso < visited < Paris""")
        fun `Query 2`() {
            val ast = parseToEqlAstOrFail("""Picasso < visited < Paris""")
            assertThat(ast)
                    .isEqualTo(RootNode(QueryNode(listOf(QueryElemNode.OrderNode(
                            QueryElemNode.OrderNode(
                                    QueryElemNode.SimpleNode("Picasso", SimpleQueryType.STRING, Interval.valueOf(0, 6)),
                                    QueryElemNode.SimpleNode("visited", SimpleQueryType.STRING, Interval.valueOf(10, 16)),
                                    Interval.valueOf(0, 16)
                            ),
                            QueryElemNode.SimpleNode("Paris", SimpleQueryType.STRING, Interval.valueOf(20, 24)), Interval.valueOf(0, 24))), null, Interval.valueOf(0, 24)), null, Interval.valueOf(0, 24)))
        }

        @Test
        @DisplayName("""Picasso visited Paris - _PAR_""")
        fun `Query 4`() {
            val ast = parseToEqlAstOrFail("""Picasso visited Paris - _PAR_""")
            println(ast.toKotlinDef())
            assertThat(ast)
                    .isEqualTo(RootNode(QueryNode(listOf(
                            QueryElemNode.SimpleNode("Picasso", SimpleQueryType.STRING, Interval.valueOf(0, 6)),
                            QueryElemNode.RestrictionNode(
                                    QueryElemNode.SimpleNode("visited", SimpleQueryType.STRING, Interval.valueOf(8, 14)),
                                    QueryElemNode.SimpleNode("Paris", SimpleQueryType.STRING, Interval.valueOf(16, 20)),
                                    RestrictionTypeNode.ContextNode(
                                            ContextRestrictionType.Paragraph, Interval.valueOf(22, 28)), Interval.valueOf(8, 28)))
                            , null, Interval.valueOf(0, 28)), null,
                            Interval.valueOf(0, 28))
                    )
        }

        @Test
        @DisplayName("""(Picasso visited Paris) - _PAR_""")
        fun `Query 4v1`() {
            val ast = parseToEqlAstOrFail("""(Picasso visited Paris) - _PAR_""")

            assertThat(ast)
                    .isEqualTo(
                            RootNode(QueryNode(listOf(
                                    QueryElemNode.ParenNode(QueryNode(listOf(
                                            QueryElemNode.SimpleNode("Picasso", SimpleQueryType.STRING, Interval.valueOf(1, 7)),
                                            QueryElemNode.SimpleNode("visited", SimpleQueryType.STRING, Interval.valueOf(9, 15)),
                                            QueryElemNode.SimpleNode("Paris", SimpleQueryType.STRING, Interval.valueOf(17, 21))), null, Interval.valueOf(1, 21)),
                                            RestrictionTypeNode.ContextNode(ContextRestrictionType.Paragraph, Interval.valueOf(24, 30)), Interval.valueOf(0, 30))), null, Interval.valueOf(0, 30)),
                                    null, Interval.valueOf(0, 30))
                    )
        }

        @Test
        @DisplayName("""Picasso visited Paris - _SENT_""")
        fun `Query 5`() {
            val ast = parseToEqlAstOrFail("""Picasso visited Paris - _SENT_""")
            assertThat(ast).isEqualTo(RootNode(QueryNode(listOf(
                    QueryElemNode.SimpleNode("Picasso", SimpleQueryType.STRING, Interval.valueOf(0, 6)),
                    QueryElemNode.RestrictionNode(
                            QueryElemNode.SimpleNode("visited", SimpleQueryType.STRING, Interval.valueOf(8, 14)),
                            QueryElemNode.SimpleNode("Paris", SimpleQueryType.STRING, Interval.valueOf(16, 20)),
                            RestrictionTypeNode.ContextNode(ContextRestrictionType.Sentence, Interval.valueOf(22, 29)), Interval.valueOf(8, 29))), null, Interval.valueOf(0, 29)),
                    null, Interval.valueOf(0, 29)))
        }

        @Test
        @DisplayName("""(Picasso ( visited | explored )  Paris) - _SENT_""")
        fun `Query 6`() {
            val ast = parseToEqlAstOrFail("""Picasso ( visited | explored )  Paris - _SENT_""")
            assertThat(ast).isEqualTo(RootNode(QueryNode(listOf(
                    QueryElemNode.SimpleNode("Picasso", SimpleQueryType.STRING, Interval.valueOf(0, 6)),
                    QueryElemNode.RestrictionNode(
                            QueryElemNode.ParenNode(QueryNode(listOf(
                                    QueryElemNode.BooleanNode(
                                            QueryElemNode.SimpleNode("visited", SimpleQueryType.STRING, Interval.valueOf(10, 16)),
                                            BooleanOperator.OR,
                                            QueryElemNode.SimpleNode("explored", SimpleQueryType.STRING, Interval.valueOf(20, 27)),
                                            Interval.valueOf(10, 27))),
                                    null, Interval.valueOf(10, 27)), null, Interval.valueOf(8, 29))
                            , QueryElemNode.SimpleNode("Paris", SimpleQueryType.STRING, Interval.valueOf(32, 36)),
                            RestrictionTypeNode.ContextNode(ContextRestrictionType.Sentence, Interval.valueOf(38, 45)), Interval.valueOf(8, 45))),
                    null, Interval.valueOf(0, 45)), null,
                    Interval.valueOf(0, 45)))
        }


        @Test
        @DisplayName("""(Picasso ( lemma:visit | lemma:explore )  Paris) - _SENT_""")
        fun `Query 7`() {
            val ast = parseToEqlAstOrFail("""(Picasso ( lemma:visit | lemma:explore )  Paris ) - _SENT_""")
            println("assertThat(ast).isEqualTo(${ast.toKotlinDef()})")
        }

        @Test
        @DisplayName("""Picasso lemma:(visit|explore) Paris - _SENT_""")
        fun `Query 8`() {
            val ast = parseToEqlAstOrFail("""Picasso lemma:(visit|explore) Paris - _SENT_""")
        }

        @Test
        @DisplayName("""nertag:person^(person.name:Picasso) lemma:(visit|explore) Paris - _SENT_""")
        fun `Query 9`() {
            val ast = parseToEqlAstOrFail("""nertag:person^(person.name:Picasso) lemma:(visit|explore) Paris - _SENT_""")
        }

        @Test
        @DisplayName("""nertag:person^(person.name:Picasso) lemma:(visit|explore)  nertag:place^(place.name:Paris) - _SENT_""")
        fun `Query 10`() {
            val ast = parseToEqlAstOrFail("""nertag:person^(person.name:Picasso) lemma:(visit|explore)  nertag:place^(place.name:Paris) - _SENT_""")
            println(ast.toKotlinDef())
        }

        @Test
        @DisplayName("""nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) - _PAR_""")
        fun `Query 11`() {
            val ast = parseToEqlAstOrFail("""nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) - _PAR_""")
        }

        @Test
        @DisplayName("""influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) - _PAR_ && influencer.nerid != influencee.nerid""")
        fun `Query 12`() {
            val ast = parseToEqlAstOrFail("""influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) - _PAR_ && influencer.nerid != influencee.nerid""")
        }


        @Test
        @DisplayName("""a:=nertag:(person|artist) < lemma:visit < b:=nertag:(person|artist) nertag:place^place.name:Barcelona nertag:event^event.date:[1/1/1960..12/12/2012] - _PAR_ && a.nerid != b.nerid""")
        fun `Query 13`() {
            val ast = parseToEqlAstOrFail("""a:=nertag:(person|artist) < lemma:visit < b:=nertag:(person|artist) nertag:place^place.name:Barcelona nertag:event^event.date:[1/1/1960..12/12/2012] - _PAR_ && a.nerid != b.nerid""")
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
        @DisplayName("""Picasso visited | explored  Paris - _SENT_""")
        fun `Query 21`() {
            val ast = parseToEqlAstOrFail("""Picasso visited | explored  Paris - _SENT_""")
        }

        @Test
        @DisplayName("""(Picasso visited) | (explored  Paris) - _SENT_""")
        fun `Query 22`() {
            val ast = parseToEqlAstOrFail("""(Picasso visited) | (explored  Paris) - _SENT_""")
        }

    }
}