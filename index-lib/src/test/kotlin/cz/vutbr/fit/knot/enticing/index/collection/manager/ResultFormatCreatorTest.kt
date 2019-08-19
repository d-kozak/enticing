package cz.vutbr.fit.knot.enticing.index.collection.manager

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.*
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.*
import cz.vutbr.fit.knot.enticing.dto.format.text.Annotation
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.createPayload
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.DocumentElement
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.StructuredDocumentContent
import cz.vutbr.fit.knot.enticing.index.mg4j.testConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


internal class ResultFormatCreatorTest {

    private val templateQuery = SearchQuery(
            "foo bar baz",
            20,
            mapOf("one" to Offset(0, 0)),
            TextMetadata.Predefined("none"),
            ResponseType.FULL,
            ResponseFormat.ANNOTATED_TEXT
    )

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
    inner class SnippetHtml {

        private val htmlQuery = templateQuery.copy(responseFormat = ResponseFormat.HTML)

        @Test
        fun `simple format no metadata`() {
            var payload = createPayload(htmlQuery, noMetadata, listOf(Interval.valueOf(0, 2)), testConfiguration.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.Html("<b>one two three</b>"))
            payload = createPayload(htmlQuery, noMetadata, listOf(Interval.valueOf(1, 2)), testConfiguration.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.Html("one <b>two three</b>"))
            payload = createPayload(htmlQuery, noMetadata, listOf(Interval.valueOf(1, 1)), testConfiguration.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.Html("one <b>two</b> three"))
        }

        @Test
        fun `two other indexes`() {
            val payload = createPayload(htmlQuery, simpleStructure, listOf(Interval.valueOf(1, 2)), testConfiguration.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.Html("""<span eql-word eql-lemma="1" eql-url="google.com">one</span> <b><span eql-word eql-lemma="2" eql-url="yahoo.com">two</span> <span eql-word eql-lemma="3" eql-url="localhost">three</span></b>"""))
        }

        @Test
        fun `with one entity`() {
            val payload = createPayload(htmlQuery, withEntities, listOf(Interval.valueOf(1, 2)), testConfiguration.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.Html("""<span eql-word eql-lemma="1" eql-url="google.com" eql-nertag="0" eql-param="0">one</span> <b><span eql-word eql-lemma="2" eql-url="yahoo.com" eql-nertag="0" eql-param="0">two</span> <span eql-word eql-lemma="3" eql-url="localhost" eql-nertag="0" eql-param="0">three</span></b> <span eql-entity eql-name="harry"><span eql-word eql-lemma="3" eql-url="localhost" eql-nertag="person" eql-param="harry">harry</span><span eql-word eql-lemma="3" eql-url="localhost" eql-nertag="0" eql-param="0">potter</span></span>"""))
        }
    }

    @Nested
    inner class SnippetAnnotated {

        private val jsonQuery = templateQuery.copy(responseFormat = ResponseFormat.ANNOTATED_TEXT)

        @Test
        fun `simple format no metadata`() {
            var payload = createPayload(jsonQuery, noMetadata, listOf(Interval.valueOf(0, 2)), testConfiguration.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.Annotated(StringWithMetadata(
                            "one two three",
                            emptyMap(),
                            emptyList(),
                            listOf(QueryMapping(0 to 13, 0 to 1))
                    )))
            payload = createPayload(jsonQuery, noMetadata, listOf(Interval.valueOf(1, 2)), testConfiguration.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.Annotated(StringWithMetadata(
                            "one two three",
                            emptyMap(),
                            emptyList(),
                            listOf(QueryMapping(4 to 13, 0 to 1))
                    )))
            payload = createPayload(jsonQuery, noMetadata, listOf(Interval.valueOf(1, 1)), testConfiguration.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.Annotated(StringWithMetadata(
                            "one two three",
                            emptyMap(),
                            emptyList(),
                            listOf(QueryMapping(4 to 7, 0 to 1))
                    )))
        }

        @Test
        fun `two other indexes`() {
            val payload = createPayload(jsonQuery, simpleStructure, listOf(Interval.valueOf(1, 2)), testConfiguration.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.Annotated(StringWithMetadata(
                            "one two three",
                            mapOf(
                                    "w-0" to Annotation("w-0", mapOf("lemma" to "1", "url" to "google.com")),
                                    "w-1" to Annotation("w-1", mapOf("lemma" to "2", "url" to "yahoo.com")),
                                    "w-2" to Annotation("w-2", mapOf("lemma" to "3", "url" to "localhost"))
                            ),
                            listOf(AnnotationPosition("w-0", 0 to 3), AnnotationPosition("w-1", 4 to 7), AnnotationPosition("w-2", 8 to 13)),
                            listOf(QueryMapping(4 to 13, 0 to 1))
                    )))
        }

        @Test
        fun `with one entity`() {
            val payload = createPayload(jsonQuery, withEntities, listOf(Interval.valueOf(1, 2)), testConfiguration.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.Annotated(StringWithMetadata(
                            "one two three harry potter",
                            mapOf(
                                    "w-0" to Annotation("w-0", mapOf("lemma" to "1", "url" to "google.com", "nertag" to "0", "param" to "0")),
                                    "w-1" to Annotation("w-1", mapOf("lemma" to "2", "url" to "yahoo.com", "nertag" to "0", "param" to "0")),
                                    "w-2" to Annotation("w-2", mapOf("lemma" to "3", "url" to "localhost", "nertag" to "0", "param" to "0")),
                                    "w-3" to Annotation("w-3", mapOf("lemma" to "3", "url" to "localhost", "nertag" to "person", "param" to "harry")),
                                    "w-4" to Annotation("w-4", mapOf("lemma" to "3", "url" to "localhost", "nertag" to "0", "param" to "0")),
                                    "e-5" to Annotation("e-5", mapOf("nertag" to "person", "name" to "harry"))
                            ),
                            listOf(AnnotationPosition("w-0", 0 to 3), AnnotationPosition("w-1", 4 to 7), AnnotationPosition("w-2", 8 to 13),
                                    AnnotationPosition("e-5", 14 to 26, listOf(AnnotationPosition("w-3", 14 to 19), AnnotationPosition("w-4", 20 to 26)))),
                            listOf(QueryMapping(4 to 13, 0 to 1))
                    )))
        }

    }

    @Nested
    inner class NewFormat {

        private val newFormatQuery = templateQuery.copy(responseFormat = ResponseFormat.NEW_ANNOTATED_TEXT)

        @Test
        fun `simple format no metadata`() {
            var payload = createPayload(newFormatQuery, noMetadata, listOf(Interval.valueOf(0, 2)), noMetadata.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.NewAnnotated(TextUnitList(
                            TextUnit.QueryMatch(Interval.valueOf(0, 1),
                                    listOf(TextUnit.Word("one"),
                                            TextUnit.Word("two"),
                                            TextUnit.Word("three")))
                    )))

            payload = createPayload(newFormatQuery, noMetadata, listOf(Interval.valueOf(1, 2)), noMetadata.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.NewAnnotated(TextUnitList(
                            TextUnit.Word("one"),
                            TextUnit.QueryMatch(Interval.valueOf(0, 1),
                                    listOf(
                                            TextUnit.Word("two"),
                                            TextUnit.Word("three")))
                    )))
            payload = createPayload(newFormatQuery, noMetadata, listOf(Interval.valueOf(1, 1)), noMetadata.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.NewAnnotated(TextUnitList(
                            TextUnit.Word("one"),
                            TextUnit.QueryMatch(Interval.valueOf(0, 1),
                                    listOf(TextUnit.Word("two"))),
                            TextUnit.Word("three")
                    )))
        }

        @Test
        fun `two other indexes`() {
            val payload = createPayload(newFormatQuery, simpleStructure, listOf(Interval.valueOf(1, 2)), simpleStructure.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.NewAnnotated(TextUnitList(
                            TextUnit.Word("one", "1", "google.com"),
                            TextUnit.QueryMatch(Interval.valueOf(0, 1),
                                    listOf(
                                            TextUnit.Word("two", "2", "yahoo.com"),
                                            TextUnit.Word("three", "3", "localhost")))
                    )))
        }

        @Test
        fun `with one entity no intervals`() {
            val payload = createPayload(newFormatQuery, withEntities, emptyList(), withEntities.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.NewAnnotated(TextUnitList(
                            TextUnit.Word("one", "1", "google.com", "0", "0"),
                            TextUnit.Word("two", "2", "yahoo.com", "0", "0"),
                            TextUnit.Word("three", "3", "localhost", "0", "0"),
                            TextUnit.Entity(attributes = listOf("harry"), entityClass = "person",
                                    words = listOf(TextUnit.Word("harry", "3", "localhost", "person", "harry"),
                                            TextUnit.Word("potter", "3", "localhost", "0", "0"))))))
        }

        @Test
        fun `with one entity that is broken by interval`() {
            val payload = createPayload(newFormatQuery, withEntities, listOf(Interval.valueOf(2, 3)), withEntities.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.NewAnnotated(TextUnitList(
                            TextUnit.Word("one", "1", "google.com", "0", "0"),
                            TextUnit.Word("two", "2", "yahoo.com", "0", "0"),
                            TextUnit.QueryMatch(Interval.valueOf(0, 1), listOf(
                                    TextUnit.Word("three", "3", "localhost", "0", "0"),
                                    TextUnit.Entity(attributes = listOf("harry"), entityClass = "person",
                                            words = listOf(TextUnit.Word("harry", "3", "localhost", "person", "harry"))
                                    ))),
                            TextUnit.Entity(attributes = listOf("harry"), entityClass = "person",
                                    words = listOf(TextUnit.Word("potter", "3", "localhost", "0", "0")))
                    )))
        }


        @Test
        fun `with one entity that is broken by two intervals`() {
            val payload = createPayload(newFormatQuery, withEntities, listOf(Interval.valueOf(2, 3), Interval.valueOf(4, 4)), withEntities.corpusConfiguration)
            assertThat(payload)
                    .isEqualTo(ResultFormat.FullResponse.NewAnnotated(TextUnitList(
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
                            ))))
        }


    }

    @Test
    fun `default index should be present in the document`() {
        assertThrows<IllegalArgumentException> {
            createPayload(templateQuery.copy(defaultIndex = "foo"), noMetadata, listOf(Interval.valueOf(1, 2)), testConfiguration.corpusConfiguration)
        }
    }

}