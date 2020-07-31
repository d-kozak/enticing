package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.testconfig.metadataConfiguration
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File


class EqlQueryGeneratingVisitorTest {

    private val compiler = EqlCompiler(SimpleStdoutLoggerFactory)

    @Test
    fun `queries from file`() {
        val queries = File("src/test/resources/semantic_ok.eql")
                .readLines()
                .filter { it.isNotBlank() && !it.startsWith("#") }
        val transformed = queries.map { compiler.parseOrFail(it, metadataConfiguration) as EqlAstNode }
                .map { it.toEqlQuery() }

        for (i in queries.indices) {
            if (queries[i].toLowerCase() != transformed[i]) {
                System.err.println("Collision queries vs transformed at: ")
                System.err.println("\t '${queries[i]}' vs '${transformed[i]}'")
            }
        }

        assertThat(transformed).isEqualTo(queries.map { it.toLowerCase() })
    }

    private fun assertTransformation(query: String) {
        val ast = compiler.parseOrFail(query, metadataConfiguration) as EqlAstNode
        val serialized = ast.toEqlQuery()
        assertThat(serialized).isEqualTo(query)
    }


    @Test
    @DisplayName("not_related letter ink ~ 3")
    fun t1() {
        assertTransformation("(not_related letter) ink ~ 3")
    }

    @Test
    @DisplayName("(Picasso visited) | (explored Paris)")
    fun t2() {
        assertTransformation("(picasso visited) | (explored paris)")
    }

    @Test
    @DisplayName("(Picasso visited) | (explored Paris) ctx:sent")
    fun t3() {
        assertTransformation("((picasso visited) | (explored paris)) ctx:sent")
    }


}