package cz.vutbr.fit.knot.enticing.dto.query

import cz.vutbr.fit.knot.enticing.dto.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class SerializationTest {

    @Nested
    inner class SearchQueryTest {
        private val templateQuery = SearchQuery(
                "foo bar baz",
                42,
                Offset(10, 10),
                metadata = TextMetadata.Predefined("all"),
                responseType = ResponseType.FULL,
                responseFormat = ResponseFormat.ANNOTATED_TEXT,
                defaultIndex = "lemma"
        )

        @Test
        fun `metadata all`() {
            val input = templateQuery.copy(metadata = TextMetadata.Predefined("all"))
            val content = """"metadata":{"type":"predef","value":"all"}"""
            assertSerialization(input, content)
        }

        @Test
        fun `indexes all entities all`() {
            val input = templateQuery.copy(metadata = TextMetadata.ExactDefinition(
                    entities = Entities.Predefined("none"),
                    indexes = Indexes.Predefined("all")
            ))
            val content = """"metadata":{"type":"exact","entities":{"type":"predef","value":"none"},"indexes":{"type":"predef","value":"all"}}"""
            assertSerialization(input, content)
        }

        @Test
        fun `indexes exact entities for person place`() {
            val input = templateQuery.copy(metadata = TextMetadata.ExactDefinition(
                    entities = Entities.ExactDefinition(mapOf(
                            "person" to Indexes.Predefined("all"),
                            "place" to Indexes.Predefined("none")
                    )),
                    indexes = Indexes.ExactDefinition(listOf("one", "two", "three"))
            ))
            val content = """"metadata":{"type":"exact","entities":{"type":"exact","entities":{"person":{"type":"predef","value":"all"},"place":{"type":"predef","value":"none"}}}"""
            assertSerialization(input, content)
        }

        @Test
        fun `indexes exact entities exact`() {
            val input = templateQuery.copy(metadata = TextMetadata.ExactDefinition(
                    entities = Entities.ExactDefinition(mapOf(
                            "person" to Indexes.ExactDefinition(listOf("one", "two", "three")),
                            "place" to Indexes.ExactDefinition(listOf("three", "four", "five"))
                    )),
                    indexes = Indexes.ExactDefinition(listOf("one", "two", "three"))
            ))
            val content = """"metadata":{"type":"exact","entities":{"type":"exact","entities":{"person":{"type":"exact","names":["one","two","three"]},"place":{"type":"exact","names":["three","four","five"]}}}"""
            assertSerialization(input, content)
        }
    }


    @Nested
    inner class SearchResultTest {

        @Test
        fun `snippet html`() {
            val input = IndexServer.SearchResult(
                    matched = listOf(IndexServer.Snippet(
                            collection = "collection1",
                            documentId = 23,
                            documentTitle = "title1",
                            location = 10,
                            canExtend = false,
                            size = 20,
                            url = "google.com",
                            payload = Payload.FullResponse.Html("hello html")
                    )),
                    offset = Offset(10, 20)
            )
            val content = """"payload":{"type":"html","content":"hello html"}"""
            assertSerialization(input, content)
        }

        @Test
        fun `snippet json`() {
            val input = IndexServer.SearchResult(
                    matched = listOf(IndexServer.Snippet(
                            collection = "collection1",
                            documentId = 23,
                            documentTitle = "title1",
                            location = 10,
                            canExtend = false,
                            size = 20,
                            url = "google.com",
                            payload = Payload.FullResponse.Annotated(AnnotatedText(
                                    "foo bar baz",
                                    emptyMap(),
                                    emptyList(),
                                    emptyList()
                            ))
                    )),
                    offset = Offset(10, 20)
            )
            val content = """"payload":{"type":"annotated","content":{"text":"foo bar baz","annotations":{},"positions":[],"queryMapping":[]}}"""
            assertSerialization(input, content)
        }

        @Test
        fun `identifier list`() {
            val input = IndexServer.SearchResult(
                    matched = listOf(IndexServer.Snippet(
                            collection = "collection1",
                            documentId = 23,
                            documentTitle = "title3",
                            location = 10,
                            canExtend = false,
                            size = 20,
                            url = "google.com",
                            payload = Payload.Identifiers(
                                    listOf(
                                            Identifier("x", Payload.FullResponse.Annotated(AnnotatedText(
                                                    "foo bar baz",
                                                    emptyMap(),
                                                    emptyList(),
                                                    emptyList()
                                            )))
                                    )
                            )
                    )),
                    offset = Offset(10, 20)
            )
            val content = """"payload":{"type":"identifiers","list":[{"identifier":"x","snippet":{"type":"annotated","content":{"text":"foo bar baz","annotations":{},"positions":[],"queryMapping":[]}}}]}"""
            assertSerialization(input, content)
        }
    }

}