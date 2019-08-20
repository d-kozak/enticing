package cz.vutbr.fit.knot.enticing.index

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.annotation.Warning
import cz.vutbr.fit.knot.enticing.dto.config.dsl.*
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.StringWithMetadata
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.index.collection.manager.computeExtensionIntervals
import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jCollectionManager
import it.unimi.di.big.mg4j.query.parser.QueryParserException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

val corpusConfig = corpusConfig("CC") {
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
}

val builderConfig = indexBuilder {
    inputDirectory("../data/mg4j")
    outputDirectory("../data/indexed")
    corpusConfiguration = corpusConfig

}

val clientConfig = indexClient {
    collections{
        collection("one"){
            mg4jDirectory("../data/mg4j")
            indexDirectory("../data/indexed")
        }
    }
    corpusConfiguration = corpusConfig
}

@Incomplete("more complete test suite needed")
class CollectionManagerTest {

    private val templateQuery = SearchQuery(
            "",
            20,
            mapOf("one" to Offset(0, 0)),
            TextMetadata.Predefined("all"),
            cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET,
            TextFormat.STRING_WITH_METADATA
    )

    companion object {

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            // it is necessary to validate the configuration, because some initialization happens at that phase
            builderConfig.validate()
            clientConfig.validate()
            startIndexing(builderConfig)
        }

    }

    @Test
    fun `valid queries`() {
        val queryEngine = initMg4jCollectionManager(clientConfig.corpusConfiguration, clientConfig.collections[0])
        for (input in listOf(
                "hello",
                "john",
                "lemma:work{{lemma->token}}",
                "nertag:person{{nertag->token}}",
                "job work"
        )) {
            val query = templateQuery.copy(query = input)

            val result = queryEngine.query(query)
            println(result)
        }
    }

    @Test
    fun `valid queries with new data format requested`() {
        val queryEngine = initMg4jCollectionManager(clientConfig.corpusConfiguration, clientConfig.collections[0])
        for (input in listOf(
                "hello",
                "john",
                "lemma:work{{lemma->token}}",
                "nertag:person{{nertag->token}}",
                "job work"
        )) {
            val query = templateQuery.copy(query = input, textFormat = TextFormat.TEXT_UNIT_LIST)

            val result = queryEngine.query(query)
        }
    }

    @Warning("it seems that different documents are being returned in test environment, why?")
    @Test
    fun problematicQuery() {
        val query = SearchQuery(query = "job work", snippetCount = 33, offset = mapOf("one" to Offset(document = 0, snippet = 0)), metadata = TextMetadata.Predefined(value = "all"), resultFormat = cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET, textFormat = TextFormat.TEXT_UNIT_LIST, defaultIndex = "token")
        val queryEngine = initMg4jCollectionManager(clientConfig.corpusConfiguration, clientConfig.collections[0])
        val result = queryEngine.query(query)
    }

    @Test
    fun `syntax error should be caught`() {
        val queryEngine = initMg4jCollectionManager(clientConfig.corpusConfiguration, clientConfig.collections[0])
        val query = templateQuery.copy(query = "lemma:work{{lemma->")

        assertThrows<QueryParserException> {
            val result = queryEngine.query(query)
        }
    }

    @Test
    fun `document retrieval test`() {
        val executor = initMg4jCollectionManager(clientConfig.corpusConfiguration, clientConfig.collections[0])
        for (i in 0..10) {
            val query = IndexServer.DocumentQuery("col1", i)

            val document = executor.getDocument(query)

            if (document.payload is ResultFormat.Snippet.StringWithMetadata) {
                val annotated = document.payload as ResultFormat.Snippet.StringWithMetadata
                assertThat(validateAnnotatedText(annotated.content))
                        .isEmpty()
            }
        }
    }

    @Test
    fun `context extension test`() {
        val executor = initMg4jCollectionManager(clientConfig.corpusConfiguration, clientConfig.collections[0])

        val (prefix, suffix, _) = executor.extendSnippet(
                IndexServer.ContextExtensionQuery("col1", 2, 5, 5, 10, textFormat = TextFormat.STRING_WITH_METADATA
                ))

        for (text in listOf(prefix, suffix)) {
            val annotated = text as ResultFormat.Snippet.StringWithMetadata
            validateAnnotatedText(annotated.content)
        }
    }

    @Test
    fun `real context extension request`() {
        val query = """{"collection":"name","docId":56,"defaultIndex":"token","location":10,"size":50,"extension":20}""".toDto<IndexServer.ContextExtensionQuery>()
        val executor = initMg4jCollectionManager(clientConfig.corpusConfiguration, clientConfig.collections[0])
        val (prefix, suffix, _) = executor.extendSnippet(query)

    }

    private fun validateAnnotatedText(text: StringWithMetadata): List<String> {
        val errors = mutableListOf<String>()
        for (position in text.positions) {
            val (id, _, subAnnotations) = position
            if (id !in text.annotations) {
                errors.add("$id not found in annotations")
            }
            for ((subId, _, subsub) in subAnnotations) {
                if (subId !in text.annotations) {
                    errors.add("$subId not found in annotations")
                }
                if (subsub.isNotEmpty()) {
                    errors.add("only two level nesting allowed, this one is deeper")
                }
            }
        }
        return errors
    }

    @Nested
    inner class ExtensionIntervals {


        @Test
        fun `both prefix and suffix available distributed evenly`() {
            val (prefix, suffix) = computeExtensionIntervals(10, 14, 10, 20)
            assertThat(prefix)
                    .isEqualTo(Interval.valueOf(5, 9))
            assertThat(suffix)
                    .isEqualTo(Interval.valueOf(15, 19))
        }

        @Test
        fun `no prefix available`() {
            val (prefix, suffix) = computeExtensionIntervals(0, 5, 10, 20)
            assertThat(prefix)
                    .isEqualTo(Interval.empty())
            assertThat(suffix)
                    .isEqualTo(Interval.valueOf(6, 15))
        }

        @Test
        fun `no suffix available`() {
            val (prefix, suffix) = computeExtensionIntervals(10, 15, 10, 16)
            assertThat(prefix)
                    .isEqualTo(Interval.valueOf(0, 9))
            assertThat(suffix)
                    .isEqualTo(Interval.empty())
        }

        @Test
        fun `all extra goes to prefix suffix is limited`() {
            val (prefix, suffix) = computeExtensionIntervals(10, 15, 10, 17)
            assertThat(prefix)
                    .isEqualTo(Interval.valueOf(1, 9))
            assertThat(suffix)
                    .isEqualTo(Interval.valueOf(16))
        }

        @Test
        fun `all extra goes to suffix prefix is limited`() {
            val (prefix, suffix) = computeExtensionIntervals(3, 5, 7, 17)
            assertThat(prefix)
                    .isEqualTo(Interval.valueOf(0, 2))
            assertThat(suffix)
                    .isEqualTo(Interval.valueOf(6, 9))
        }

        @Test
        fun `a bit of extra goes to prefix suffix is limited`() {
            val (prefix, suffix) = computeExtensionIntervals(5, 10, 7, 12)
            assertThat(prefix)
                    .isEqualTo(Interval.valueOf(0, 4))
            assertThat(suffix)
                    .isEqualTo(Interval.valueOf(11))
        }

        @Test
        fun `a bit of extra goes to suffix prefix is limited`() {
            val (prefix, suffix) = computeExtensionIntervals(3, 5, 7, 8)
            assertThat(prefix)
                    .isEqualTo(Interval.valueOf(0, 2))
            assertThat(suffix)
                    .isEqualTo(Interval.valueOf(6, 7))
        }

        @Test
        fun `left negative`() {
            assertThrows<IllegalArgumentException> {
                computeExtensionIntervals(-3, 5, 7, 8)
            }
        }

        @Test
        fun `right negative`() {
            assertThrows<IllegalArgumentException> {
                computeExtensionIntervals(3, -5, 7, 8)
            }
        }

        @Test
        fun `right smaller than left`() {
            assertThrows<IllegalArgumentException> {
                computeExtensionIntervals(3, 2, 7, 8)
            }
        }

        @Test
        fun `extension too small`() {
            assertThrows<IllegalArgumentException> {
                computeExtensionIntervals(3, 5, 0, 8)
            }
        }

        @Test
        fun `document size negative`() {
            assertThrows<IllegalArgumentException> {
                computeExtensionIntervals(3, 5, 3, -1)
            }
        }

        @Test
        fun `document size zero`() {
            assertThrows<IllegalArgumentException> {
                computeExtensionIntervals(3, 5, 3, 0)
            }
        }


    }

}