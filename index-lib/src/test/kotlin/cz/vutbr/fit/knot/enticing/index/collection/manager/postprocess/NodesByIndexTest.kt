package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.index.collectionManagerConfiguration
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("get nodes by index")
class NodeByIndexTest {

    private val compiler = EqlCompiler(SimpleStdoutLoggerFactory)

    @DisplayName("That Motion three")
    @Test
    fun simpleQuery() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("That Motion three", collectionManagerConfiguration.metadataConfiguration)
        Assertions.assertThat(errors).isEmpty()
        val nodes = getNodesByIndex(ast as EqlAstNode, "token")
        Assertions.assertThat(nodes.mapValues { it.value.map { it.content } })
                .isEqualTo(mapOf(
                        "token" to listOf("That", "Motion", "three")
                ))
    }

    @DisplayName("one two position:3 lemma:(job pony)")
    @Test
    fun multipleIndexes() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("one two position:(3 < 1) lemma:(job|pony)", collectionManagerConfiguration.metadataConfiguration)
        Assertions.assertThat(errors).isEmpty()
        val nodes = getNodesByIndex(ast as EqlAstNode, "token")
        Assertions.assertThat(nodes.mapValues { it.value.map { it.content } })
                .isEqualTo(mapOf(
                        "token" to listOf("one", "two"),
                        "position" to listOf("3", "1"),
                        "lemma" to listOf("job", "pony")
                ))
    }

}
