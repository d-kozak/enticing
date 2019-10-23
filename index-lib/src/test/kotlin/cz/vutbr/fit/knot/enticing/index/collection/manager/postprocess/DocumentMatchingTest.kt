package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.index.clientConfig
import cz.vutbr.fit.knot.enticing.index.corpusConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail


internal class DocumentMatchingTest : AbstractDocumentMatchingTest() {


    @DisplayName("food")
    @Test
    fun one() = forEachMatch("food") {
        interval("should contain food") { document, interval ->
            document.content[corpusConfig.indexes.getValue("token").columnIndex][interval.from].toLowerCase() == "food"
        }
        leaf("should contain food") { document, match ->
            document.content[corpusConfig.indexes.getValue("token").columnIndex][match.match.from].toLowerCase() == "food"
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
                .isEqualTo(MatchInfo.empty())
    }

    @Test
    @DisplayName("th*")
    fun withStar() {
        val doc = searchEngine.loadDocument(3)
        val query = "th*"
        val (ast, errors) = compiler.parseAndAnalyzeQuery(query, clientConfig.corpusConfiguration)
        assertThat(errors).isEmpty()
        val match = matchDocument(ast as EqlAstNode, doc, "token", clientConfig.corpusConfiguration, Interval.valueOf(0, doc.size() - 1))
        println(match)

        var fail = false
        val text = doc.content[corpusConfig.indexes.getValue("token").columnIndex]
        for ((interval) in match.intervals) {
            if (interval.size != 1) {
                System.err.println("all single match intervals should have len 1")
                fail = true
            } else if (!text[interval.from].toLowerCase().startsWith("th")) {
                System.err.println("'${text[interval.from]}' should start with 'th'")
                fail = true
            }
        }
        if (fail) fail { "" }
    }

    @Test
    @DisplayName("person.name:Mic*")
    fun person() {
        val doc = searchEngine.loadDocument(0)
        val query = "person.name:Mic*"
        val (ast, errors) = compiler.parseAndAnalyzeQuery(query, clientConfig.corpusConfiguration)
        assertThat(errors).isEmpty()
        val match = matchDocument(ast as EqlAstNode, doc, "token", clientConfig.corpusConfiguration, Interval.valueOf(0, doc.size() - 1))

        var fail = false
        val nertag = doc.content[corpusConfig.indexes.getValue("nertag").columnIndex]
        val name = doc.content[corpusConfig.entities.getValue("person").attributes.getValue("name").columnIndex]
        for ((interval) in match.intervals) {
            for (x in interval) {
                if (nertag[x] != "person") {
                    fail = true
                    System.err.println("at index $x there should be entity of type person")
                }
                if (!name[x].toLowerCase().startsWith("mic")) {
                    fail = true
                    System.err.println("name of person at $x should start with 'mic', was '${name[x]}'")
                }
            }
        }

        if (fail) fail { "" }
    }

    @Test
    @DisplayName("That < Motion")
    fun next() {
        val doc = searchEngine.loadDocument(3)
        val query = "That < Motion"
        val (ast, errors) = compiler.parseAndAnalyzeQuery(query, clientConfig.corpusConfiguration)
        assertThat(errors).isEmpty()
        val match = matchDocument(ast as EqlAstNode, doc, "token", clientConfig.corpusConfiguration, Interval.valueOf(0, doc.size() - 1))

        val text = doc.content[corpusConfig.indexes.getValue("token").columnIndex]
        val joined = text.joinToString(" ").toLowerCase()

        var fail = false
        for ((interval, leaves) in match) {
            val (from, to) = interval
            val that = joined.indexOf("that")
            val motion = joined.indexOf("motion")
            if (that < 0) {
                System.err.println("'that' not found in '$joined'")
                fail = true
            }
            if (motion < 0) {
                System.err.println("'motion' not found in '$joined'")
                fail = true
            }
            if (that + "that".length >= motion) {
                System.err.println("'that' should be before 'motion' in $joined")
                fail = true
            }

            for (leaf in leaves) {
                if (leaf.match.size != 1) {
                    System.err.println("leaves should have len 1")
                    fail = true
                }
                if (text[leaf.match.from].toLowerCase() !in setOf("that", "motion")) {
                    System.err.println("only leaves for 'that' and 'motion' expected, found:'${text[leaf.match.from].toLowerCase()}'")
                    fail = true
                }
            }
        }

        if (fail) fail { "" }
    }
}