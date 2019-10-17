package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.index.boundary.EqlMatch
import cz.vutbr.fit.knot.enticing.index.clientConfig
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jSearchEngine
import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jQueryEngine
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class DocumentMatchingTest {

    val searchEngine = Mg4jSearchEngine(
            Mg4jCompositeDocumentCollection(clientConfig.corpusConfiguration, clientConfig.collections[0].mg4jFiles),
            initMg4jQueryEngine(clientConfig.collections[0], clientConfig.corpusConfiguration)
    )

    val compiler = EqlCompiler()

    companion object {

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            clientConfig.validate()
        }

    }


    @DisplayName("get nodes by index")
    @Nested
    inner class NodeByIndex {

        @DisplayName("That Motion three")
        @Test
        fun simpleQuery() {
            val (ast, errors) = compiler.parseAndAnalyzeQuery("That Motion three", clientConfig.corpusConfiguration)
            assertThat(errors).isEmpty()
            val nodes = getNodesByIndex(ast as EqlAstNode, "token")
            assertThat(nodes.mapValues { it.value.map { it.content } })
                    .isEqualTo(mapOf(
                            "token" to listOf("That", "Motion", "three")
                    ))
        }

        @DisplayName("one two position:3 lemma:(job pony)")
        @Test
        fun multipleIndexes() {
            val (ast, errors) = compiler.parseAndAnalyzeQuery("one two position:(3 < 1) lemma:(job|pony)", clientConfig.corpusConfiguration)
            assertThat(errors).isEmpty()
            val nodes = getNodesByIndex(ast as EqlAstNode, "token")
            assertThat(nodes.mapValues { it.value.map { it.content } })
                    .isEqualTo(mapOf(
                            "token" to listOf("one", "two"),
                            "position" to listOf("3", "1"),
                            "lemma" to listOf("job", "pony")
                    ))
        }

    }

    @DisplayName("That Motion three")
    @Test
    fun simpleQuery() {
        val doc = searchEngine.loadDocument(3)
        val query = "That Motion three"
        val (ast, errors) = compiler.parseAndAnalyzeQuery(query, clientConfig.corpusConfiguration)
        assertThat(errors).isEmpty()
        val match = matchDocument(ast as EqlAstNode, doc, "token", clientConfig.corpusConfiguration, Interval.valueOf(0, doc.size() - 1))
        assertThat(match)
                .isEqualTo(
                        listOf(
                                EqlMatch.IndexMatch(Interval.valueOf(0, 3), listOf(4, 42, 76, 121, 155)),
                                EqlMatch.IndexMatch(Interval.valueOf(5, 10), listOf(17))
                        )
                )
    }
}