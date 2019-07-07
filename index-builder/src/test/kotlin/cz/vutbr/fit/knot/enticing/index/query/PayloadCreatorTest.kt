package cz.vutbr.fit.knot.enticing.index.query

import cz.vutbr.fit.knot.enticing.dto.query.*
import cz.vutbr.fit.knot.enticing.dto.response.Payload
import cz.vutbr.fit.knot.enticing.index.mg4j.DocumentContent
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


    private val smallDocument: DocumentContent = mapOf(
            "token" to listOf("one", "two", "three")
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
                    .isEqualTo(Payload.Snippet.Html("one <b>two</b>three"))
        }
    }

    @Test
    fun `left and right should be within bounds of document size`() {


        assertThrows<IllegalArgumentException> {
            createPayload(templateQuery, smallDocument, 1, 5)
        }
        assertThrows<IllegalArgumentException> {
            createPayload(templateQuery, smallDocument, 6, 7)
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