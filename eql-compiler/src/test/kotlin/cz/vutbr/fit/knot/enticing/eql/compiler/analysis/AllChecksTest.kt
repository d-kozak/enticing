package cz.vutbr.fit.knot.enticing.eql.compiler.analysis

import cz.vutbr.fit.knot.enticing.dto.config.dsl.*
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.forEachQuery
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.SemanticError
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal val config = corpusConfig("CC") {
    indexes {
        "position" whichIs "Position of the word in the document"
        "token" whichIs "Original word in the document"
        "tag" whichIs "tag"
        "lemma" whichIs "Lemma of the word"
        "parpos" whichIs "parpos"
        "function" whichIs "function"
        "parwrod" whichIs "parword"
        "parlemma" whichIs "parlemma"
        "paroffset" whichIs "paroffset"
        "link" whichIs "link"
        "length" whichIs "length"
        "docuri" whichIs "docuri"
        "lower" whichIs "lower"
        "nerid" whichIs "nerid"
        "nertag" whichIs "nertag"
        params(9)
        "nertype" whichIs "nertype"
        "nerlength" whichIs "nerlength"
    }

    entities {
        "person" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "profession", "nationality")

        "artist" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "role", "nationality")

        "location" with attributes("url", "image", "name", "country")

        "artwork" with attributes("url", "image", "name", "form", "datebegun", "datecompleted", "movement", "genre", "author")

        "event" with attributes("url", "image", "name", "startdate", "enddate", "location")

        "museum" with attributes("url", "image", "name", "type", "established", "director", "location")

        "family" with attributes("url", "image", "name", "role", "nationality", "members")

        "group" with attributes("url", "image", "name", "role", "nationality")

        "nationality" with attributes("url", "image", "name", "country")

        "date" with attributes("url", "image", "year", "month", "day")

        "interval" with attributes("url", "image", "fromyear", "frommonth", "fromday", "toyear", "tomonth", "today")

        "form" with attributes("url", "image", "name")

        "medium" with attributes("url", "image", "name")

        "mythology" with attributes("url", "image", "name")

        "movement" with attributes("url", "image", "name")

        "genre" with attributes("url", "image", "name")

    }
    entityMapping {
        entityIndex = "nertag"
        attributeIndexes = 15 to 24
        extraAttributes("nertype", "nerlength")
    }
}.also {
    val errors = mutableListOf<String>()
    it.validate(errors)
    assertThat(errors).isEmpty()
}

fun assertHasError(errors: List<CompilerError>, id: String, count: Int = 1) {
    assertThat(errors).hasSize(count)
    val error = errors[0]
    error as SemanticError
    assertThat(error.analysisId).isEqualTo(id)
    println("Encountered expected error $error")
}

class AllChecksTest {

    val compiler = EqlCompiler()

    @Test
    fun `queries from file`() {
        forEachQuery("semantic_ok.eql") {
            val (_, errors) = compiler.parseAndAnalyzeQuery(it, config)
            if (errors.isNotEmpty()) {
                System.err.println(errors)
                false
            } else true
        }
    }

    @Test
    fun `invalid proximity`() {
        val (tree, errors) = compiler.parseAndAnalyzeQuery("one two ~ three", config)
        assertHasError(errors, "PROX-1")
        assertThat(errors).hasSize(1)
        val check = errors[0]
        assertThat(check)
                .isInstanceOf(SemanticError::class.java)
        check as SemanticError

        assertThat(check.location)
                .isEqualTo(Interval.valueOf(10, 14))
    }

    @Nested
    inner class InvalidIndex {

        @Test
        fun `simple query`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("lenna:word", config) // should be lemma
            assertHasError(errors, "IND-1")
        }

        @Test
        fun `with or`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("index:(A|B|C)", config) // should be lemma
            assertHasError(errors, "IND-1")
        }

        @Test
        fun `index inside or`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("index:A | index:B  | index:C", config) // should be lemma
            assertHasError(errors, "IND-1", 3)
        }
    }

    @Nested
    inner class InvalidEntity {

        @Test
        fun `simple query`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("personek.name:honza", config) // should be person
            assertHasError(errors, "ENT-1")
        }

        @Test
        fun `more complex query`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("nertag:person^(person1.name:Picasso) lemma:(visit|explore) Paris - _SENT_", config)
            assertHasError(errors, "ENT-1")
        }
    }

}