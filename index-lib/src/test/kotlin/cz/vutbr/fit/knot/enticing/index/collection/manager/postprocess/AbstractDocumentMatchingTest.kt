package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
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
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.fail

abstract class Check(val name: String, val document: IndexedDocument, val corpusConfiguration: CorpusConfiguration) {
    var success: Boolean = true

    fun checkFailed(reason: String): Boolean {
        System.err.println(reason)
        success = false
        return false
    }

    fun verify(check: Boolean, msgFactory: () -> String): Boolean {
        val res = check
        if (!res) checkFailed(msgFactory())
        return res
    }

    fun performCheck(): Boolean = doCheck() && success

    protected abstract fun doCheck(): Boolean

    fun textAt(index: String, interval: Interval? = null): String {
        var content = document.content[corpusConfiguration.indexes.getValue(index).columnIndex]
        content = if (interval != null) content.subList(interval.from, interval.to + 1) else content
        return content.joinToString(" ") { it.toLowerCase() }
    }


    fun cellsAt(index: String, interval: Interval? = null): List<String> {
        val content = document.content[corpusConfiguration.indexes.getValue(index).columnIndex]
        return (if (interval != null) content.subList(interval.from, interval.to + 1) else content).map { it.toLowerCase() }
    }

    fun attributeCellsAt(entity: String, attribute: String, interval: Interval? = null): List<String> {
        val content = document.content[corpusConfiguration.entities.getValue(entity).attributes.getValue(attribute).columnIndex]
        return (if (interval != null) content.subList(interval.from, interval.to + 1) else content).map { it.toLowerCase() }
    }
}

class IntervalCheck constructor(name: String, document: IndexedDocument, corpusConfiguration: CorpusConfiguration, val interval: Interval, val leafMatch: List<EqlMatch>, private val checkStrategy: IntervalCheck.() -> Boolean) : Check(name, document, corpusConfiguration) {
    override fun doCheck(): Boolean = this.let(checkStrategy)
}

class LeafCheck constructor(name: String, document: IndexedDocument, corpusConfiguration: CorpusConfiguration, val leafMatch: EqlMatch, private val checkStrategy: LeafCheck.() -> Boolean) : Check(name, document, corpusConfiguration) {
    override fun doCheck(): Boolean = this.let(checkStrategy)
}


class CheckDsl {
    val intervalChecks = mutableListOf<Pair<String, IntervalCheck.() -> Boolean>>()
    val leafChecks = mutableListOf<Pair<String, LeafCheck.() -> Boolean>>()

    fun forEachInterval(name: String, check: IntervalCheck.() -> Boolean) {
        intervalChecks.add(name to check)
    }

    fun forEachLeaf(name: String, check: LeafCheck.() -> Boolean) {
        leafChecks.add(name to check)
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

    protected fun forEachMatch(query: String, documentId: Long? = null, documentRange: LongRange? = null, init: CheckDsl.() -> Unit) {
        val testRange = when {
            documentRange != null -> documentRange
            documentId != null -> documentId..documentId
            else -> 0 until documentCount
        }
        val checks = CheckDsl()
        checks.apply(init)
        forEachMatchInternal(query, testRange, checks.intervalChecks, checks.leafChecks)
    }

    private fun forEachMatchInternal(query: String, documentRange: LongRange, intervalChecks: List<Pair<String, IntervalCheck.() -> Boolean>>, leafChecks: List<Pair<String, LeafCheck.() -> Boolean>>) {
        val (ast, errors) = compiler.parseAndAnalyzeQuery(query, clientConfig.corpusConfiguration)
        Assertions.assertThat(errors).isEmpty()

        val failedDocs = mutableListOf<IndexedDocument>()
        var matchCount = 0
        for (i in documentRange) {
            val failedChecks = mutableListOf<String>()
            val doc = searchEngine.loadDocument(i.toInt())
            println("testing on document [$i] '${doc.title}'")
            val match = matchDocument(ast.deepCopy() as EqlAstNode, doc, "token", clientConfig.corpusConfiguration, Interval.valueOf(0, doc.size() - 1))
            if (match.intervals.isNotEmpty()) {
                matchCount++
            }
            for ((interval, leafMatch) in match) {
                intervalChecks.map { (name, check) -> IntervalCheck(name, doc, clientConfig.corpusConfiguration, interval, leafMatch, check) }
                        .filter { !it.performCheck() }
                        .forEach { failedChecks.add("INT: ${it.name}") }
                for (leaf in leafMatch) {
                    leafChecks.map { (name, check) -> LeafCheck(name, doc, clientConfig.corpusConfiguration, leaf, check) }
                            .filter { !it.performCheck() }
                            .forEach { failedChecks.add("LEAF: ${it.name}") }
                }
            }

            if (failedChecks.isNotEmpty()) {
                System.err.println("Checks '$failedChecks' failed on document [$i] '${doc.title}'")
                failedDocs.add(doc)
            }
        }
        println("Query '$query' was matched on $matchCount documents")
        if (failedDocs.isNotEmpty()) {
            fail { "Query '$query' failed on documents $failedDocs" }
        }
    }
}