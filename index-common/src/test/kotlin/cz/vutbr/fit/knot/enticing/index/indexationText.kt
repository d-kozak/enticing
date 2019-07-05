package cz.vutbr.fit.knot.enticing.index

import cz.vutbr.fit.knot.enticing.dto.query.*
import cz.vutbr.fit.knot.enticing.index.config.dsl.*
import it.unimi.di.big.mg4j.query.parser.QueryParserException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


val builderConfig = indexBuilder {
    inputDirectory("../data/mg4j")
    outputDirectory("../data/indexed")

    corpus("CC") {
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
    }
}

val clientConfig = indexClient {
    mg4jDirectory("../data/mg4j")
    indexDirectory("../data/indexed")

    corpus("CC") {
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
    }
}

class QueryExecutionTest {

    private val templateQuery = SearchQuery(
            "",
            20,
            Offset(0, 0),
            TextMetadata.Predefined("none"),
            ResponseType.SNIPPET,
            ResponseFormat.JSON
    )

    companion object {

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            startIndexing(builderConfig)
        }

    }

    @Test
    fun `valid queries`() {
        val queryEngine = initQueryExecutor(clientConfig)
        for (input in listOf(
                "hello",
                "john",
                "lemma:work{{lemma->token}}",
                "nertag:person{{nertag->token}}"
        )) {
            val query = templateQuery.copy(query = input)

            val response = queryEngine.query(query)
            assertThat(response.isSuccess)
        }
    }

    @Test
    fun `syntax error should be caught`() {
        val queryEngine = initQueryExecutor(clientConfig)
        val query = templateQuery.copy(query = "lemma:work{{lemma->")

        assertThrows<QueryParserException> {
            val result = queryEngine.query(query)
            assertThat(result.isFailure)
            result.rethrowException()
        }
    }

    @Test
    fun `forgotten remapping should be caught`() {
        val queryEngine = initQueryExecutor(clientConfig)
        val query = templateQuery.copy(query = "nertag:person")

        assertThrows<IllegalArgumentException> {
            val result = queryEngine.query(query)
            assertThat(result.isFailure)
            result.rethrowException()
        }
    }
}