package cz.vutbr.fit.knot.enticing.index.regres

import cz.vutbr.fit.knot.enticing.dto.interval.subList
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.DocumentMatch
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.matchDocument
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jDocumentFactory
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jSingleFileDocumentCollection
import cz.vutbr.fit.knot.enticing.index.testconfig.dummyMetadataConfiguration
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import java.io.File

fun loadDocument(path: String): IndexedDocument {
    val factory = Mg4jDocumentFactory(dummyMetadataConfiguration, SimpleStdoutLoggerFactory)
    val collection = Mg4jSingleFileDocumentCollection(File(path), factory, SimpleStdoutLoggerFactory)
    return collection.document(0)
}

private const val REGRES_FOLDER = "../data/regres/"


fun IndexedDocument.loadText(match: DocumentMatch) = this.content[dummyMetadataConfiguration.indexes.getValue(dummyMetadataConfiguration.defaultIndex).columnIndex]
        .subList(match.interval)
        .joinToString(" ")


abstract class AbstractDocumentMatchingRegressionTest(documentName: String) {

    protected val testedDocument = loadDocument("$REGRES_FOLDER$documentName.mg4j")

    private val compiler = EqlCompiler(SimpleStdoutLoggerFactory)

    protected fun submitQuery(query: String): MatchInfo {
        val ast = compiler.parseOrFail(query, dummyMetadataConfiguration) as EqlAstNode
        return matchDocument(ast, testedDocument, "token", 0, dummyMetadataConfiguration, testedDocument.interval)
    }
}