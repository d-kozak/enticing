package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.mg4jFiles
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.dto.interval.substring
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatch
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.integration.builderConfig
import cz.vutbr.fit.knot.enticing.index.integration.collectionManagerConfiguration
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jSearchEngine
import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jQueryEngine
import cz.vutbr.fit.knot.enticing.index.startIndexing
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.fail

abstract class Check(val name: String, val document: IndexedDocument, val metadataConfiguration: MetadataConfiguration) {
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
        var content = document.content[metadataConfiguration.indexes.getValue(index).columnIndex]
        content = if (interval != null) content.subList(interval.from, interval.to + 1) else content
        return content.joinToString(" ") { it.toLowerCase() }
    }


    fun cellsAt(index: String, interval: Interval? = null): List<String> {
        val content = document.content[metadataConfiguration.indexes.getValue(index).columnIndex]
        return (if (interval != null) content.subList(interval.from, interval.to + 1) else content).map { it.toLowerCase() }
    }

    fun attributeCellsAt(entity: String, attribute: String, interval: Interval? = null): List<String> {
        val content = document.content[metadataConfiguration.entities.getValue(entity).ownAttributes.getValue(attribute).index.columnIndex]
        return (if (interval != null) content.subList(interval.from, interval.to + 1) else content).map { it.toLowerCase() }
    }

    fun multipleEntityAttributeCellsAt(entities: Set<String>, attribute: String, interval: Interval? = null): List<String> {
        val contentAt = cellsAt(metadataConfiguration.entityIndexName, interval).toSet()
        check(contentAt.size == 1) { "entity should be the same across the region" }
        val matchedEntity = contentAt.first()
        if (matchedEntity !in entities) {
            checkFailed("could not find matchedEntity $matchedEntity in $contentAt")
        }
        return attributeCellsAt(matchedEntity, attribute, interval)
    }
}

class IntervalCheck constructor(name: String, document: IndexedDocument, metadataConfiguration: MetadataConfiguration, val interval: Interval, val leafMatch: List<EqlMatch>, private val checkStrategy: IntervalCheck.() -> Boolean) : Check(name, document, metadataConfiguration) {
    override fun doCheck(): Boolean = this.let(checkStrategy)

    fun verifyLeafCount(n: Int, msgFactory: (() -> String)? = null): Boolean = if (msgFactory != null) verifyLeafCount(n..n, msgFactory) else verifyLeafCount(n..n)

    fun verifyLeafCount(range: IntRange, msgFactory: () -> String = { "the number of leaves should be $range, was ${leafMatch.size}, leaves: $leafMatch" }): Boolean = verify(leafMatch.size in range, msgFactory)

}

class LeafCheck constructor(name: String, document: IndexedDocument, metadataConfiguration: MetadataConfiguration, val leafMatch: EqlMatch, private val checkStrategy: LeafCheck.() -> Boolean) : Check(name, document, metadataConfiguration) {
    override fun doCheck(): Boolean = this.let(checkStrategy)
}

class GlobalConstraintCheck(name: String, document: IndexedDocument, metadataConfiguration: MetadataConfiguration, val identifiers: Map<String, EqlMatch>, private val checkStrategy: GlobalConstraintCheck.() -> Boolean) : Check(name, document, metadataConfiguration) {
    override fun doCheck(): Boolean = this.let(checkStrategy)
}


class CheckDsl {
    val intervalChecks = mutableListOf<Pair<String, IntervalCheck.() -> Boolean>>()
    val leafChecks = mutableListOf<Pair<String, LeafCheck.() -> Boolean>>()
    val identifierChecks = mutableListOf<Pair<String, LeafCheck.() -> Boolean>>()
    var globalConstraintChecks: Triple<String, Set<String>, GlobalConstraintCheck.() -> Boolean>? = null

    fun forEachInterval(name: String, check: IntervalCheck.() -> Boolean) {
        intervalChecks.add(name to check)
    }

    fun forEachLeaf(name: String, check: LeafCheck.() -> Boolean) {
        leafChecks.add(name to check)
    }

    fun forEachIdentifier(name: String, check: LeafCheck.() -> Boolean) {
        identifierChecks.add(name to check)
    }

    fun verifyGlobalConstraint(name: String, vararg identifiers: String, check: GlobalConstraintCheck.() -> Boolean) {
        globalConstraintChecks = Triple(name, identifiers.toSet(), check)
    }

    operator fun component1() = intervalChecks
    operator fun component2() = leafChecks
    operator fun component3() = identifierChecks
    operator fun component4() = globalConstraintChecks
}

abstract class AbstractDocumentMatchingTest {

    private val collection = Mg4jCompositeDocumentCollection(collectionManagerConfiguration.metadataConfiguration, collectionManagerConfiguration.mg4jDir.mg4jFiles, SimpleStdoutLoggerFactory)
    protected val searchEngine = Mg4jSearchEngine(
            collection,
            initMg4jQueryEngine(collectionManagerConfiguration)
            , SimpleStdoutLoggerFactory
    )

    protected val documentCount = collection.size()

    protected val compiler = EqlCompiler(SimpleStdoutLoggerFactory)

    companion object {

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            startIndexing(builderConfig, SimpleStdoutLoggerFactory)
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
        forEachMatchInternal(query, testRange, checks)
    }

    private fun forEachMatchInternal(query: String, documentRange: LongRange, checks: CheckDsl) {
        val (ast, errors) = compiler.parseAndAnalyzeQuery(query, collectionManagerConfiguration.metadataConfiguration)
        Assertions.assertThat(errors).isEmpty()

        val (intervalChecks, leafChecks, _, globalConstraintChecks) = checks
        val identifierChecks = checks.identifierChecks.associate { it }

        val globalConstraintIdentifiers = checks.globalConstraintChecks?.second ?: emptySet()
        val failedDocs = mutableListOf<IndexedDocument>()
        var matchCount = 0
        for (i in documentRange) {
            val failedChecks = mutableListOf<String>()
            val doc = searchEngine.loadDocument(i.toInt())
            println("testing on document [$i] '${doc.title}'")
            val match = matchDocument(ast.deepCopy() as EqlAstNode, doc, "token", 0, collectionManagerConfiguration.metadataConfiguration, Interval.valueOf(0, doc.size() - 1))
            if (match.intervals.isNotEmpty()) {
                matchCount++
            }
            for ((interval, leafMatch) in match) {
                intervalChecks.map { (name, check) -> IntervalCheck(name, doc, collectionManagerConfiguration.metadataConfiguration, interval, leafMatch, check) }
                        .filter { !it.performCheck() }
                        .forEach { failedChecks.add("INT: ${it.name}") }
                val encounteredIdentifiers = mutableMapOf<String, EqlMatch>()
                val encounteredGlobalConstraintIdentifiers = mutableMapOf<String, EqlMatch>()
                for (leaf in leafMatch) {
                    leafChecks.map { (name, check) -> LeafCheck(name, doc, collectionManagerConfiguration.metadataConfiguration, leaf, check) }
                            .filter { !it.performCheck() }
                            .forEach { failedChecks.add("LEAF: ${it.name}") }
                    val maybeIdentifier = query.substring(leaf.queryInterval)
                    if (maybeIdentifier in globalConstraintIdentifiers) {
                        encounteredGlobalConstraintIdentifiers[maybeIdentifier] = leaf
                    }
                    identifierChecks[maybeIdentifier]?.let {
                        val name = maybeIdentifier
                        encounteredIdentifiers[name] = leaf
                        val check = LeafCheck(name, doc, collectionManagerConfiguration.metadataConfiguration, leaf, it)
                        if (!check.performCheck()) {
                            failedChecks.add("ID: $name")
                        }
                    }
                }
                if (encounteredIdentifiers.size != identifierChecks.size) {
                    val missingIdentifiers = identifierChecks.keys - encounteredIdentifiers.keys
                    failedChecks.add("ID-MISS: identifiers $missingIdentifiers were not matched")
                }

                if (globalConstraintIdentifiers.isNotEmpty()) {
                    if (globalConstraintIdentifiers.size == encounteredGlobalConstraintIdentifiers.size) {
                        val check = GlobalConstraintCheck(globalConstraintChecks!!.first, doc, collectionManagerConfiguration.metadataConfiguration, encounteredGlobalConstraintIdentifiers, globalConstraintChecks!!.third)
                        if (!check.performCheck()) {
                            failedChecks.add("GLOBAL: ${globalConstraintChecks!!.first}")
                        }
                    } else {
                        val missing = globalConstraintIdentifiers - encounteredGlobalConstraintIdentifiers.keys
                        failedChecks.add("GLOBAL-MISS: Global constraint identifier $missing were not matched")
                    }
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