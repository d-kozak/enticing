package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.index.boundary.EqlMatch
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.clientConfig
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jSearchEngine
import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jQueryEngine
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*

data class IntervalCheck private constructor(val name: String) {
    lateinit var check: (IndexedDocument, Interval) -> Boolean

    constructor(name: String, check: (IndexedDocument, Interval) -> Boolean) : this(name) {
        this.check = check
    }
}

data class LeafCheck private constructor(val name: String) {
    lateinit var check: (IndexedDocument, EqlMatch) -> Boolean

    constructor(name: String, check: (IndexedDocument, EqlMatch) -> Boolean) : this(name) {
        this.check = check
    }
}

class CheckDsl {
    val intervalChecks = mutableListOf<IntervalCheck>()
    val leafChecks = mutableListOf<LeafCheck>()

    fun interval(name: String, check: (IndexedDocument, Interval) -> Boolean) {
        intervalChecks.add(IntervalCheck(name, check))
    }

    fun leaf(name: String, check: (IndexedDocument, EqlMatch) -> Boolean) {
        leafChecks.add(LeafCheck(name, check))
    }
}

abstract class AbstractDocumentMatchingTest {

    private val collection = Mg4jCompositeDocumentCollection(clientConfig.corpusConfiguration, clientConfig.collections[0].mg4jFiles)
    protected val searchEngine = Mg4jSearchEngine(
            collection,
            initMg4jQueryEngine(clientConfig.collections[0], clientConfig.corpusConfiguration)
    )

    protected val documentCount = collection.size()

    protected val compiler = EqlCompiler()

    companion object {

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            clientConfig.validate()
        }

    }

    protected fun forEachMatch(query: String, init: CheckDsl.() -> Unit) {
        val checks = CheckDsl()
        checks.apply(init)
        forEachMatchInternal(query, checks.intervalChecks, checks.leafChecks)
    }

    private fun forEachMatchInternal(query: String, intervalChecks: List<IntervalCheck>, leafChecks: List<LeafCheck>) {
        val (ast, errors) = compiler.parseAndAnalyzeQuery(query, clientConfig.corpusConfiguration)
        Assertions.assertThat(errors).isEmpty()

        val failedDocs = mutableListOf<IndexedDocument>()
        for (i in 0 until documentCount) {
            val failedChecks = mutableListOf<String>()
            val doc = searchEngine.loadDocument(i.toInt())
            println("testing on document [$i] '${doc.title}'")
            val match = matchDocument(ast.deepCopy() as EqlAstNode, doc, "token", clientConfig.corpusConfiguration, Interval.valueOf(0, doc.size() - 1))

            for ((interval, leafMatch) in match) {
                for (check in intervalChecks) {
                    if (!check.check(doc, interval)) {
                        failedChecks.add("INT: ${check.name}")
                    }
                }
                for (leaf in leafMatch) {
                    for (check in leafChecks) {
                        if (!check.check(doc, leaf)) {
                            failedChecks.add("LEAF: ${check.name}")
                        }
                    }
                }
            }

            if (failedChecks.isNotEmpty()) {
                System.err.println("Checsks '$failedChecks' failed on document [$i] '${doc.title}'")
                failedDocs.add(doc)
            }
        }

        if (failedDocs.isNotEmpty()) {
            fail { "Query '$query' failed on documents ${failedDocs.map { it.title }}" }
        }
    }

    @DisplayName("get nodes by index")
    @Nested
    inner class NodeByIndex {

        @DisplayName("That Motion three")
        @Test
        fun simpleQuery() {
            val (ast, errors) = compiler.parseAndAnalyzeQuery("That Motion three", clientConfig.corpusConfiguration)
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
            val (ast, errors) = compiler.parseAndAnalyzeQuery("one two position:(3 < 1) lemma:(job|pony)", clientConfig.corpusConfiguration)
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

}