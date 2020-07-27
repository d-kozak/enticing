package cz.vutbr.fit.knot.enticing.index.collection.manager

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.matchDocument
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jDocumentFactory
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jSingleFileDocumentCollection
import cz.vutbr.fit.knot.enticing.index.testconfig.dummyMetadataConfiguration
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File


fun loadDocument(path: String): IndexedDocument {
    val factory = Mg4jDocumentFactory(dummyMetadataConfiguration, SimpleStdoutLoggerFactory)
    val collection = Mg4jSingleFileDocumentCollection(File(path), factory, SimpleStdoutLoggerFactory)
    return collection.document(0)
}

//@Disabled
class DocumentMatchingRegresionTests {

    private val compiler = EqlCompiler(SimpleStdoutLoggerFactory)

    private fun query(query: String, document: IndexedDocument): MatchInfo {
        val ast = compiler.parseOrFail(query, dummyMetadataConfiguration) as EqlAstNode
        return matchDocument(ast, document, "token", 0, dummyMetadataConfiguration, document.interval)
    }

    @Test
    fun `formula ona document`() {
        val query = "a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url"
        val doc = loadDocument("../data/regres/formulaOneSeason.mg4j")
        val matchInfo = query(query, doc)
        assertThat(matchInfo.intervals).hasSize(1)
        val singleMatch = matchInfo.intervals[0]
        assertThat(singleMatch.interval).isEqualTo(Interval.valueOf(4420, 4438))
    }

    @Test
    fun `1922 in literature`(){
        val query = "nertag:artist"
        val doc = loadDocument("../data/regres/1922inLiterature.mg4j")
        val matchInfo = query(query, doc)
        println(matchInfo)
    }

    @Test
    fun `1922 in literature with global contraints`(){
        val query = "a:=nertag:person b:=nertag:person ctx:sent && a.url != b.url"
        val doc = loadDocument("../data/regres/1922inLiterature.mg4j")
        val matchInfo = query(query, doc)
        println(matchInfo)
    }
}