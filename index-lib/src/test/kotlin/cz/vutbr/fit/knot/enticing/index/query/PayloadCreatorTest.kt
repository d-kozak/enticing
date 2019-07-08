package cz.vutbr.fit.knot.enticing.index.query

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.index
import cz.vutbr.fit.knot.enticing.dto.query.*
import cz.vutbr.fit.knot.enticing.dto.response.AnnotatedText
import cz.vutbr.fit.knot.enticing.dto.response.Payload
import cz.vutbr.fit.knot.enticing.dto.response.QueryMapping
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetPartsFields
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


internal class PayloadCreatorTest {

    private val templateQuery = SearchQuery(
            "foo bar baz",
            20,
            Offset(0, 0),
            TextMetadata.Predefined("none"),
            ResponseType.SNIPPET,
            ResponseFormat.JSON
    )

    private val noMetadataConfig = CorpusConfiguration("empty")
            .apply {
                indexes {
                    index("token")
                }
            }

    private val smallDocument = SnippetPartsFields(listOf(
            SnippetElement.Word(0, listOf("one")),
            SnippetElement.Word(1, listOf("two")),
            SnippetElement.Word(2, listOf("three"))),
            noMetadataConfig
    )


    @Nested
    inner class SnippetHtml {

        private val htmlQuery = templateQuery.copy(responseFormat = ResponseFormat.HTML)

        @Test
        fun `simple format no metadata`() {
            var payload = createPayload(htmlQuery, smallDocument, 0, 3)
            assertThat(payload)
                    .isEqualTo(Payload.Snippet.Html("<b>one two three</b>"))
            payload = createPayload(htmlQuery, smallDocument, 1, 3)
            assertThat(payload)
                    .isEqualTo(Payload.Snippet.Html("one <b>two three</b>"))
            payload = createPayload(htmlQuery, smallDocument, 1, 2)
            assertThat(payload)
                    .isEqualTo(Payload.Snippet.Html("one <b>two</b> three"))
        }
    }

    @Nested
    inner class SnippetJson {

        private val jsonQuery = templateQuery.copy(responseFormat = ResponseFormat.JSON)

        @Test
        fun `simple format no metadata`() {
            var payload = createPayload(jsonQuery, smallDocument, 0, 3)
            assertThat(payload)
                    .isEqualTo(Payload.Snippet.Json(AnnotatedText(
                            "one two three",
                            emptyMap(),
                            emptyList(),
                            listOf(QueryMapping(0 to 13, 0 to jsonQuery.query.length))
                    )))
            payload = createPayload(jsonQuery, smallDocument, 1, 3)
            assertThat(payload)
                    .isEqualTo(Payload.Snippet.Json(AnnotatedText(
                            "one two three",
                            emptyMap(),
                            emptyList(),
                            listOf(QueryMapping(4 to 13, 0 to jsonQuery.query.length))
                    )))
            payload = createPayload(jsonQuery, smallDocument, 1, 2)
            assertThat(payload)
                    .isEqualTo(Payload.Snippet.Json(AnnotatedText(
                            "one two three",
                            emptyMap(),
                            emptyList(),
                            listOf(QueryMapping(4 to 7, 0 to jsonQuery.query.length))
                    )))
        }
    }

    @Test
    fun `left should be smaller or equal to right`() {
        assertThrows<IllegalArgumentException> {
            createPayload(templateQuery, smallDocument, 2, 1)
        }
    }


    @Test
    fun `default index should be present in the document`() {
        assertThrows<IllegalArgumentException> {
            createPayload(templateQuery.copy(defaultIndex = "foo"), smallDocument, 1, 2)
        }
    }

}