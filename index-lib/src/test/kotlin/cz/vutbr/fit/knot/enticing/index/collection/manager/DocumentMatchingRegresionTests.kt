package cz.vutbr.fit.knot.enticing.index.collection.manager

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
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
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File


fun loadDocument(path: String): IndexedDocument {
    val factory = Mg4jDocumentFactory(dummyMetadataConfiguration, SimpleStdoutLoggerFactory)
    val collection = Mg4jSingleFileDocumentCollection(File(path), factory, SimpleStdoutLoggerFactory)
    return collection.document(0)
}

fun IndexedDocument.getText(match: DocumentMatch) = this.content[dummyMetadataConfiguration.indexes.getValue(dummyMetadataConfiguration.defaultIndex).columnIndex]
        .subList(match.interval)
        .joinToString(" ")

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
    fun `1922 in literature`() {
        val query = "nertag:artist"
        val doc = loadDocument("../data/regres/1922inLiterature.mg4j")
        val matchInfo = query(query, doc)
        println(matchInfo)
    }

    @Test
    fun `1922 in literature with global contraints`() {
        val query = "a:=nertag:person b:=nertag:person ctx:sent && a.url != b.url"
        val doc = loadDocument("../data/regres/1922inLiterature.mg4j")
        val matchInfo = query(query, doc)
        println(matchInfo)
    }

    @Nested
    @DisplayName("76 comics")
    inner class Comics {

        val document = loadDocument("../data/regres/76comics.mg4j")

        @Test
        fun person() {
            val query = "a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url"
            val matchInfo = query(query, document)
            assertThat(matchInfo.intervals).hasSize(2)
            println(matchInfo)
        }

        @Test
        fun artist() {
            val query = "document.uuid:'c9338c23-4580-5ab4-9d86-96de0c0dd15b' a:=nertag:artist < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url"
            val matchInfo = query(query, document)
            assertThat(matchInfo.intervals).hasSize(2)
            println(matchInfo)
        }
    }

    @Nested
    @DisplayName("'Ullo John! Gotta New Motor? ")
    inner class Ullo {

        val document = loadDocument("../data/regres/ulloGottaNewMotor.mg4j")

        @Test
        fun query() {
            val query = "a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url"
            val (intervals) = query(query, document)
            assertThat(intervals.size).isEqualTo(2)
            assertThat(document.getText(intervals[0]))
                    .isEqualTo("Sayle ( the presenter ) talks through the influence of the car on the post war working classes and also features villain John McVicar")
            assertThat(document.getText(intervals[1]))
                    .isEqualTo("Sayle ( the presenter ) talks through the influence of the car on the post war working classes and also features villain John McVicar ( who")

        }
    }
}