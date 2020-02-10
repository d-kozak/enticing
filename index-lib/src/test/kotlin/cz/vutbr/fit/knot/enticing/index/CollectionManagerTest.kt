package cz.vutbr.fit.knot.enticing.index

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.annotation.Warning
import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.metadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.StringWithMetadata
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnit
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompilerException
import cz.vutbr.fit.knot.enticing.index.collection.manager.computeExtensionIntervals
import cz.vutbr.fit.knot.enticing.index.mg4j.CollectionManagerConfiguration
import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jCollectionManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import java.io.File

val metadataConfiguration = metadataConfiguration {
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
        attributeIndexes(10)
        "nertype" whichIs "nertype"
        "nerlength" whichIs "nerlength"
//        "_glue" whichIs "glue" todo glue
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

        extraAttributes("nertype", "nerlength")
    }
}

val builderConfig = IndexBuilderConfig(
        "CC",
        "col1",
        metadataConfiguration,
        File("../data/mg4j"),
        File("../data/indexed")
)


val collectionManagerConfiguration = CollectionManagerConfiguration(
        "CC",
        "col1",
        File("../data/indexed"),
        File("../data/mg4j"),
        metadataConfiguration
)

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
//            builderConfig.validate() todo fix
//            clientConfig.validate()
            startIndexing(builderConfig)
        }

    }

    @Test
    fun `valid queries with all text formats`() {
        val queryEngine = initMg4jCollectionManager(collectionManagerConfiguration)
        for (textFormat in TextFormat.values()) {
            for (input in listOf(
                    "hello",
                    "john",
                    "lemma:work",
                    "nertag:person",
                    "job work"
            )) {
                val query = templateQuery.copy(query = input, textFormat = textFormat)
                query.eqlAst = EqlCompiler().parseOrFail(input, collectionManagerConfiguration.metadataConfiguration)
                val result = queryEngine.query(query)
            }
        }
    }

    @Test
    @Disabled
    fun allQueries() {
        val queryEngine = initMg4jCollectionManager(collectionManagerConfiguration)
        val compiler = EqlCompiler()
        val badQueries = mutableListOf<String>()
        for (line in File("../eql-compiler/src/test/resources/semantic_ok.eql").readLines()) {
            if (line.isEmpty() || line.startsWith("#")) continue
            println("query: $line")
            try {
                val query = templateQuery.copy(line)
                val (ast, errors) = compiler.parseAndAnalyzeQuery(line, collectionManagerConfiguration.metadataConfiguration)
                assertThat(errors).isEmpty()
                query.eqlAst = ast
                val result = queryEngine.query(query)
            } catch (e: Throwable) {
                e.printStackTrace()
                badQueries.add(line)
            }
        }

        if (badQueries.isNotEmpty()) {
            println("Following queries failed:")
            badQueries.forEach(::println)
            fail { badQueries.toString() }
        }
    }


    @Test
    fun exampleQuery() {
        val queryEngine = initMg4jCollectionManager(collectionManagerConfiguration)
        val input = "nertag:person (killed|visited)"
        val query = templateQuery.copy(query = input, textFormat = TextFormat.TEXT_UNIT_LIST)
        query.eqlAst = EqlCompiler().parseOrFail(input, collectionManagerConfiguration.metadataConfiguration)
        val result = queryEngine.query(query)
    }


    private fun <T : ResultFormat> check(query: SearchQuery, resultFormatClass: Class<T>, check: (T) -> Unit) {
        val queryEngine = initMg4jCollectionManager(collectionManagerConfiguration)
        val updatedQuery = if (query.query.isEmpty()) query.copy(query = "nertag:person (killed|visited)") else query
        updatedQuery.eqlAst = EqlCompiler().parseOrFail(updatedQuery.query, collectionManagerConfiguration.metadataConfiguration)
        for (result in queryEngine.query(updatedQuery).searchResults) {
            assertThat(result.payload).isInstanceOf(resultFormatClass)
            check(result.payload as T)
        }
    }

    @Nested
    inner class SelectedTextMetadata {


        @Nested
        inner class OnlyThreeIndexes {

            private val metadata = TextMetadata.ExactDefinition(Entities.ExactDefinition(emptyMap()), Indexes.ExactDefinition(listOf("token", "lemma", "position")))

            @Test
            fun html() {
                val shouldNotContain = listOf("parpos", "function", "parword", "parlemma", "paroffset", "link", "length").map { """$it=""" } + "eql-entity "
                val query = templateQuery.copy(textFormat = TextFormat.HTML, metadata = metadata)
                check(query, ResultFormat.Snippet.Html::class.java) {
                    assertThat(it.content).doesNotContain(shouldNotContain)
                }
            }


            @Test
            fun stringWithMetadata() {
                val query = templateQuery.copy(textFormat = TextFormat.STRING_WITH_METADATA, metadata = metadata)
                check(query, ResultFormat.Snippet.StringWithMetadata::class.java) {
                    for ((_, annotation) in it.content.annotations) {
                        assertThat(annotation.content.keys).contains("lemma", "position")
                                .hasSize(2)
                    }
                }
            }

            @Test
            fun textUnitList() {
                fun checkWord(word: TextUnit.Word) {
                    assertThat(word.indexes).hasSize(3)
                }

                fun checkQueryMatch(match: TextUnit.QueryMatch) {
                    for (unit in match.content) {
                        when (unit) {
                            is TextUnit.Word -> checkWord(unit)
                            is TextUnit.Entity -> throw AssertionError("no entities wanted")
                            else -> throw AssertionError("only words expected here")
                        }
                    }
                }

                val query = templateQuery.copy(textFormat = TextFormat.TEXT_UNIT_LIST, metadata = metadata)
                check(query, ResultFormat.Snippet.TextUnitList::class.java) {
                    for (unit in it.content.content) {
                        when (unit) {
                            is TextUnit.Word -> checkWord(unit)
                            is TextUnit.Entity -> throw AssertionError("no entities wanted")
                            is TextUnit.QueryMatch -> checkQueryMatch(unit)
                        }
                    }
                }
            }
        }

        @Nested
        inner class SomeIndexesTwoEntities {

            private val metadata = TextMetadata.ExactDefinition(Entities.ExactDefinition(mapOf(
                    "person" to Indexes.ExactDefinition(listOf("name", "profession", "nationality")),
                    "date" to Indexes.ExactDefinition(listOf("year", "day"))
            )), Indexes.ExactDefinition(listOf("token", "lemma", "position", "parlemma")))

            @Test
            @Incomplete("This does not really test whether only given entities are there, but how to test it? parse the html and expect the structure?")
            fun html() {
                val shouldNotContain = listOf("parpos", "function", "parword", "paroffset", "link", "length").map { """$it=""" }
                val query = templateQuery.copy(textFormat = TextFormat.HTML, metadata = metadata)
                check(query, ResultFormat.Snippet.Html::class.java) {
                    assertThat(it.content).doesNotContain(shouldNotContain)
                }
            }

            @Test
            fun stringWithMetadata() {
                val query = templateQuery.copy(textFormat = TextFormat.STRING_WITH_METADATA, metadata = metadata)
                check(query, ResultFormat.Snippet.StringWithMetadata::class.java) {
                    for ((_, annotation) in it.content.annotations) {
                        when (val nertag = annotation.content["nertag"]) {
                            null -> assertThat(annotation.content.keys).contains("lemma", "position", "parlemma")
                                    .hasSize(3)
                            "person" -> assertThat(annotation.content.keys).contains("name", "profession", "nationality")
                                    .hasSize(4)
                            "date" -> assertThat(annotation.content.keys).contains("year", "day")
                                    .hasSize(3)
                            else -> throw AssertionError("Entity of type $nertag should not be included")
                        }
                    }
                }
            }

            @Test
            fun textUnitList() {
                fun checkWord(word: TextUnit.Word) {
                    assertThat(word.indexes).hasSize(4)
                }

                fun checkEntity(entity: TextUnit.Entity) {
                    entity.words.forEach(::checkWord)
                    when (entity.entityClass) {
                        "person" -> assertThat(entity.attributes).hasSize(3)
                        "date" -> assertThat(entity.attributes).hasSize(2)
                        else -> throw AssertionError("Entity of type ${entity.entityClass} should not be included")
                    }
                }

                fun checkQueryMatch(match: TextUnit.QueryMatch) {
                    for (unit in match.content) {
                        when (unit) {
                            is TextUnit.Word -> checkWord(unit)
                            is TextUnit.Entity -> checkEntity(unit)
                            else -> throw AssertionError("only words and entities expected here")
                        }
                    }
                }

                val query = templateQuery.copy(textFormat = TextFormat.TEXT_UNIT_LIST, metadata = metadata)
                check(query, ResultFormat.Snippet.TextUnitList::class.java) {
                    for (unit in it.content.content) {
                        when (unit) {
                            is TextUnit.Word -> checkWord(unit)
                            is TextUnit.Entity -> checkEntity(unit)
                            is TextUnit.QueryMatch -> checkQueryMatch(unit)
                        }
                    }
                }
            }


        }


    }

    @Nested
    @Disabled
    @WhatIf("We might actually want these to be included, because they will allow clients to format the text accordingly")
    inner class ParagraphsAndSentenceMarksShouldBeRemoved {

        @Test
        fun plainText() {
            val query = templateQuery.copy(textFormat = TextFormat.PLAIN_TEXT)
            check(query, ResultFormat.Snippet.PlainText::class.java) {
                assertThat(it.content).doesNotContain("§", "¶")
            }
        }

        @Test
        fun html() {
            val query = templateQuery.copy(textFormat = TextFormat.HTML)
            check(query, ResultFormat.Snippet.Html::class.java) {
                assertThat(it.content).doesNotContain("§", "¶")
            }
        }

        @Test
        fun stringWithMetadata() {
            val query = templateQuery.copy(textFormat = TextFormat.STRING_WITH_METADATA)
            check(query, ResultFormat.Snippet.StringWithMetadata::class.java) {
                assertThat(it.content.text).doesNotContain("§", "¶")
            }
        }

        @Test
        fun textUnitList() {
            fun checkWord(word: TextUnit.Word) {
                assertThat(word.indexes[metadataConfiguration.indexes.getValue("token").columnIndex]).doesNotContain("§", "¶")
            }

            fun checkEntity(entity: TextUnit.Entity) {
                entity.words.forEach(::checkWord)
            }

            fun checkQueryMatch(match: TextUnit.QueryMatch) {
                for (unit in match.content) {
                    when (unit) {
                        is TextUnit.Word -> checkWord(unit)
                        is TextUnit.Entity -> checkEntity(unit)
                        else -> throw AssertionError("only words and entities expected here")
                    }
                }
            }

            val query = templateQuery.copy(textFormat = TextFormat.TEXT_UNIT_LIST)
            check(query, ResultFormat.Snippet.TextUnitList::class.java) {
                for (unit in it.content.content) {
                    when (unit) {
                        is TextUnit.Word -> checkWord(unit)
                        is TextUnit.Entity -> checkEntity(unit)
                        is TextUnit.QueryMatch -> checkQueryMatch(unit)
                    }
                }
            }
        }
    }

    @Warning("it seems that different documents are being returned in test environment, why?")
    @Test
    fun problematicQuery() {
        val input = "job work"
        val query = SearchQuery(query = input, snippetCount = 33, offset = mapOf("one" to Offset(document = 0, snippet = 0)), metadata = TextMetadata.Predefined(value = "all"), resultFormat = cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET, textFormat = TextFormat.TEXT_UNIT_LIST, defaultIndex = "token")
        val queryEngine = initMg4jCollectionManager(collectionManagerConfiguration)
        query.eqlAst = EqlCompiler().parseOrFail(input, collectionManagerConfiguration.metadataConfiguration)
        val result = queryEngine.query(query)
    }

    @Test
    fun `syntax error should be caught`() {
        val queryEngine = initMg4jCollectionManager(collectionManagerConfiguration)
        val input = "lemma:(work|)"
        val query = templateQuery.copy(query = input)
        assertThrows<EqlCompilerException> {
            query.eqlAst = EqlCompiler().parseOrFail(input, collectionManagerConfiguration.metadataConfiguration)
        }
    }

    @Test
    fun `document retrieval test`() {
        val executor = initMg4jCollectionManager(collectionManagerConfiguration)
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
        val executor = initMg4jCollectionManager(collectionManagerConfiguration)

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
        val executor = initMg4jCollectionManager(collectionManagerConfiguration)
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