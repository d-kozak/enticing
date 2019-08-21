package cz.vutbr.fit.knot.enticing.index.collection.manager

import cz.vutbr.fit.knot.enticing.dto.Offset
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.dto.TextMetadata
import cz.vutbr.fit.knot.enticing.dto.config.dsl.*
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.*
import cz.vutbr.fit.knot.enticing.dto.format.text.Annotation
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.EqlMatch
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.index.collection.manager.format.result.EqlResultCreator
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.DocumentElement
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.StructuredDocumentContent
import cz.vutbr.fit.knot.enticing.index.utils.testDocument
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class ResultFormatCreatorTest {

    private val templateQuery = SearchQuery(
            "foo bar baz",
            20,
            mapOf("one" to Offset(0, 0)),
            TextMetadata.Predefined("all"),
            cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET,
            TextFormat.STRING_WITH_METADATA
    )

    private val noMetadataConfig = corpusConfig("empty") {
        indexes {
            index("token")
        }
    }

    private val noMetadataDocument = testDocument(3, "one two three")

    private val noMetadata = StructuredDocumentContent(listOf(
            DocumentElement.Word(0, listOf("one")),
            DocumentElement.Word(1, listOf("two")),
            DocumentElement.Word(2, listOf("three"))),
            corpusConfig("empty") {
                indexes {
                    index("token")
                }
            }
    )

    private val simpleStructureConfig = corpusConfig("simple") {
        indexes {
            index("token")
            index("lemma")
            index("url")
        }
    }

    private val simpleStructure = StructuredDocumentContent(listOf(
            DocumentElement.Word(0, listOf("one", "1", "google.com")),
            DocumentElement.Word(1, listOf("two", "2", "yahoo.com")),
            DocumentElement.Word(2, listOf("three", "3", "localhost"))),
            corpusConfig("simple") {
                indexes {
                    index("token")
                    index("lemma")
                    index("url")
                }
            }
    )

    private val simpleStructureDocument = testDocument(3, "one two three", "1 2 3", "google.com yahoo.com localhost")

    private val withEntitiesConfig = corpusConfig("simple") {
        indexes {
            index("token")
            index("lemma")
            index("url")
            index("nertag")
            index("param")
            index("len")
        }
        entities {
            "person" with attributes("name")
        }
        entityMapping {
            entityIndex = "nertag"
            lengthIndex = "len"
            attributeIndexes = 4 to 4
        }
    }.also { it.validate() }

    private val withEntitiesDocument = testDocument(5, "one two three harry potter", "1 2 3 3 3", "google.com yahoo.com localhost localhost localhost", "0 0 0 person 0", "0 0 0 harry 0", "0 0 0 2 0")

    private val withEntities = StructuredDocumentContent(listOf(
            DocumentElement.Word(0, listOf("one", "1", "google.com", "0", "0")),
            DocumentElement.Word(1, listOf("two", "2", "yahoo.com", "0", "0")),
            DocumentElement.Word(2, listOf("three", "3", "localhost", "0", "0")),
            DocumentElement.Entity(3, "person", listOf("harry"),
                    words = listOf(DocumentElement.Word(3, listOf("harry", "3", "localhost", "person", "harry")),
                            DocumentElement.Word(4, listOf("potter", "3", "localhost", "0", "0")))
            )),
            corpusConfig("simple") {
                indexes {
                    index("token")
                    index("lemma")
                    index("url")
                    index("nertag")
                    index("param")
                }
                entities {
                    "person" with attributes("name")
                }
                entityMapping {
                    entityIndex = "nertag"
                    attributeIndexes = 4 to 4
                }
            }.also { it.validate() }
    )


    @Nested
    inner class SearchResultHtml {

        private val htmlQuery = templateQuery.copy(textFormat = TextFormat.HTML)

        @Test
        fun `simple format no metadata`() {
            val resultCreator = EqlResultCreator(noMetadataConfig)

            var payload = resultCreator.singleResult(noMetadataDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(0, 2)), emptyList()))), htmlQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.Html("<b><span eql-word>one</span><span eql-word>two</span><span eql-word>three</span></b>", 0, 3, false))
            payload = resultCreator.singleResult(noMetadataDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(1, 2)), emptyList()))), htmlQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.Html("<span eql-word>one</span><b><span eql-word>two</span><span eql-word>three</span></b>", 0, 3, false))
            payload = resultCreator.singleResult(noMetadataDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(2)), emptyList()))), htmlQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.Html("<span eql-word>one</span><span eql-word>two</span><b><span eql-word>three</span></b>", 0, 3, false))
        }

        @Test
        fun `two other indexes`() {
            val resultCreator = EqlResultCreator(simpleStructureConfig)

            val payload = resultCreator.singleResult(simpleStructureDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(1, 2)), emptyList()))), htmlQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.Html("""<span eql-word eql-lemma="1" eql-url="google.com">one</span><b><span eql-word eql-lemma="2" eql-url="yahoo.com">two</span><span eql-word eql-lemma="3" eql-url="localhost">three</span></b>""", 0, 3, false))
        }

        @Test
        fun `with one entity`() {
            val resultCreator = EqlResultCreator(withEntitiesConfig)

            val payload = resultCreator.singleResult(withEntitiesDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(1, 2)), emptyList()))), htmlQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.Html("""<span eql-word eql-lemma="1" eql-url="google.com" eql-nertag="0" eql-param="0" eql-len="0">one</span><b><span eql-word eql-lemma="2" eql-url="yahoo.com" eql-nertag="0" eql-param="0" eql-len="0">two</span><span eql-word eql-lemma="3" eql-url="localhost" eql-nertag="0" eql-param="0" eql-len="0">three</span></b><span eql-entity eql-name="harry"><span eql-word eql-lemma="3" eql-url="localhost" eql-nertag="person" eql-param="harry" eql-len="2">harry</span><span eql-word eql-lemma="3" eql-url="localhost" eql-nertag="0" eql-param="0" eql-len="0">potter</span></span>""", 0, 5, false))
        }
    }

    @Nested
    inner class SearchResultStringWithMetadata {

        private val jsonQuery = templateQuery.copy(textFormat = TextFormat.STRING_WITH_METADATA)

        @Test
        fun `simple format no metadata`() {
            val resultCreator = EqlResultCreator(noMetadataConfig)

            var payload = resultCreator.singleResult(noMetadataDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(0, 2)), emptyList()))), jsonQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.StringWithMetadata(StringWithMetadata(
                            "one two three",
                            emptyMap(),
                            emptySet(),
                            setOf(QueryMapping(0 to 12, 1 to 1))
                    ), 0, 3, false))
            payload = resultCreator.singleResult(noMetadataDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1, 2), listOf(Interval.valueOf(1, 2)), emptyList()))), jsonQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.StringWithMetadata(StringWithMetadata(
                            "one two three",
                            emptyMap(),
                            emptySet(),
                            setOf(QueryMapping(4 to 12, 1 to 2))
                    ), 0, 3, false))
            payload = resultCreator.singleResult(noMetadataDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(2), listOf(Interval.valueOf(1)), emptyList()))), jsonQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.StringWithMetadata(StringWithMetadata(
                            "one two three",
                            emptyMap(),
                            emptySet(),
                            setOf(QueryMapping(4 to 6, 2 to 2))
                    ), 0, 3, false))
        }

        @Test
        fun `two other indexes`() {
            val resultCreator = EqlResultCreator(simpleStructureConfig)
            val payload = resultCreator.singleResult(simpleStructureDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(1, 2)), emptyList()))), jsonQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.StringWithMetadata(StringWithMetadata(
                            "one two three",
                            mapOf(
                                    "w-0" to Annotation("w-0", mapOf("lemma" to "1", "url" to "google.com")),
                                    "w-1" to Annotation("w-1", mapOf("lemma" to "2", "url" to "yahoo.com")),
                                    "w-2" to Annotation("w-2", mapOf("lemma" to "3", "url" to "localhost"))
                            ),
                            setOf(AnnotationPosition("w-0", 0 to 3), AnnotationPosition("w-1", 4 to 7), AnnotationPosition("w-2", 8 to 13)),
                            setOf(QueryMapping(4 to 12, 1 to 1))
                    ), 0, 3, false))
        }

        @Test
        fun `with one entity`() {
            val resultCreator = EqlResultCreator(withEntitiesConfig)
            val payload = resultCreator.singleResult(withEntitiesDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(1, 2)), emptyList()))), jsonQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.StringWithMetadata(StringWithMetadata(
                            "one two three harry potter",
                            mapOf(
                                    "w-0" to Annotation("w-0", mapOf("lemma" to "1", "url" to "google.com", "nertag" to "0", "param" to "0", "len" to "0")),
                                    "w-1" to Annotation("w-1", mapOf("lemma" to "2", "url" to "yahoo.com", "nertag" to "0", "param" to "0", "len" to "0")),
                                    "w-2" to Annotation("w-2", mapOf("lemma" to "3", "url" to "localhost", "nertag" to "0", "param" to "0", "len" to "0")),
                                    "w-3" to Annotation("w-3", mapOf("lemma" to "3", "url" to "localhost", "nertag" to "person", "param" to "harry", "len" to "2")),
                                    "w-4" to Annotation("w-4", mapOf("lemma" to "3", "url" to "localhost", "nertag" to "0", "param" to "0", "len" to "0")),
                                    "e-5" to Annotation("e-5", mapOf("name" to "harry", "nertag" to "person"))
                            ),
                            setOf(AnnotationPosition("w-0", 0 to 3), AnnotationPosition("w-1", 4 to 7), AnnotationPosition("w-2", 8 to 13),
                                    AnnotationPosition("w-3", 14 to 19), AnnotationPosition("w-4", 20 to 26), AnnotationPosition("e-5", 14 to 26)),
                            setOf(QueryMapping(4 to 12, 1 to 1))
                    ), 0, 5, false))
        }

    }

    @Nested
    inner class NewFormat {

        private val textUnitListQuery = templateQuery.copy(textFormat = TextFormat.TEXT_UNIT_LIST)

        @Test
        fun `simple format no metadata`() {
            val resultCreator = EqlResultCreator(noMetadataConfig)
            var payload = resultCreator.singleResult(noMetadataDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(0, 2)), emptyList()))), textUnitListQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.TextUnitList(TextUnitList(
                            TextUnit.QueryMatch(Interval.valueOf(0, 1),
                                    listOf(TextUnit.Word("one"),
                                            TextUnit.Word("two"),
                                            TextUnit.Word("three")))
                    ), 0, 3, false))

            payload = resultCreator.singleResult(noMetadataDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(1, 2)), emptyList()))), textUnitListQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.TextUnitList(TextUnitList(
                            TextUnit.Word("one"),
                            TextUnit.QueryMatch(Interval.valueOf(0, 1),
                                    listOf(
                                            TextUnit.Word("two"),
                                            TextUnit.Word("three")))
                    ), 0, 3, false))
            payload = resultCreator.singleResult(noMetadataDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(1)), emptyList()))), textUnitListQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.TextUnitList(TextUnitList(
                            TextUnit.Word("one"),
                            TextUnit.QueryMatch(Interval.valueOf(0, 1),
                                    listOf(TextUnit.Word("two"))),
                            TextUnit.Word("three")
                    ), 0, 3, false))
        }

        @Test
        fun `two other indexes`() {
            val resultCreator = EqlResultCreator(simpleStructureConfig)
            val payload = resultCreator.singleResult(simpleStructureDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(1, 2)), emptyList()))), textUnitListQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.TextUnitList(TextUnitList(
                            TextUnit.Word("one", "1", "google.com"),
                            TextUnit.QueryMatch(Interval.valueOf(0, 1),
                                    listOf(
                                            TextUnit.Word("two", "2", "yahoo.com"),
                                            TextUnit.Word("three", "3", "localhost")))
                    ), 0, 3, false))
        }

        @Test
        fun `with one entity no intervals`() {
            val resultCreator = EqlResultCreator(withEntitiesConfig)
            val payload = resultCreator.singleResult(withEntitiesDocument, MatchInfo.empty(), textUnitListQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.TextUnitList(TextUnitList(
                            TextUnit.Word("one", "1", "google.com", "0", "0"),
                            TextUnit.Word("two", "2", "yahoo.com", "0", "0"),
                            TextUnit.Word("three", "3", "localhost", "0", "0"),
                            TextUnit.Entity(attributes = listOf("harry"), entityClass = "person",
                                    words = listOf(TextUnit.Word("harry", "3", "localhost", "person", "harry"),
                                            TextUnit.Word("potter", "3", "localhost", "0", "0")))), 0, 3, false))
        }

        @Test
        fun `with one entity that is broken by interval`() {
            val resultCreator = EqlResultCreator(withEntitiesConfig)
            val payload = resultCreator.singleResult(withEntitiesDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(2, 3)), emptyList()))), textUnitListQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.TextUnitList(TextUnitList(
                            TextUnit.Word("one", "1", "google.com", "0", "0"),
                            TextUnit.Word("two", "2", "yahoo.com", "0", "0"),
                            TextUnit.QueryMatch(Interval.valueOf(0, 1), listOf(
                                    TextUnit.Word("three", "3", "localhost", "0", "0"),
                                    TextUnit.Entity(attributes = listOf("harry"), entityClass = "person",
                                            words = listOf(TextUnit.Word("harry", "3", "localhost", "person", "harry"))
                                    ))),
                            TextUnit.Entity(attributes = listOf("harry"), entityClass = "person",
                                    words = listOf(TextUnit.Word("potter", "3", "localhost", "0", "0")))
                    ), 0, 3, false))
        }


        @Test
        fun `with one entity that is broken by two intervals`() {
            val resultCreator = EqlResultCreator(withEntitiesConfig)
            val payload = resultCreator.singleResult(withEntitiesDocument, MatchInfo(emptyList(), listOf(EqlMatch.IdentifierMatch(Interval.valueOf(1), listOf(Interval.valueOf(2, 3), Interval.valueOf(4)), emptyList()))), textUnitListQuery)
            assertThat(payload)
                    .isEqualTo(ResultFormat.Snippet.TextUnitList(TextUnitList(
                            TextUnit.Word("one", "1", "google.com", "0", "0"),
                            TextUnit.Word("two", "2", "yahoo.com", "0", "0"),
                            TextUnit.QueryMatch(Interval.valueOf(0, 1), listOf(
                                    TextUnit.Word("three", "3", "localhost", "0", "0"),
                                    TextUnit.Entity(attributes = listOf("harry"), entityClass = "person",
                                            words = listOf(TextUnit.Word("harry", "3", "localhost", "person", "harry"))
                                    ))),
                            TextUnit.QueryMatch(Interval.valueOf(0, 1), listOf(
                                    TextUnit.Entity(attributes = listOf("harry"), entityClass = "person",
                                            words = listOf(TextUnit.Word("potter", "3", "localhost", "0", "0"))
                                    ))
                            )), 0, 3, false))
        }


    }
}