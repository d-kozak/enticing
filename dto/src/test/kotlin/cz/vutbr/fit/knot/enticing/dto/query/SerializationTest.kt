package cz.vutbr.fit.knot.enticing.dto.query

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.format.result.Identifier
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.StringWithMetadata
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class SerializationTest {

    @Nested
    inner class SearchQueryTest {
        private val templateQuery = SearchQuery(
                "foo bar baz",
                42,
                mapOf("one" to Offset(10, 10)),
                metadata = TextMetadata.Predefined("all"),
                resultFormat = cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET,
                textFormat = TextFormat.STRING_WITH_METADATA,
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
    inner class IndexResultListTest {

        @Test
        fun `snippet html`() {
            val input = IndexServer.IndexResultList(
                    searchResults = listOf(IndexServer.SearchResult(
                            collection = "collection1",
                            documentId = 23,
                            documentTitle = "title1",
                            url = "google.com",
                            payload = ResultFormat.Snippet.Html("hello html", 0, 0, false)
                    )),
                    offset = mapOf("one" to Offset(10, 20))
            )
            val content = """"payload":{"type":"html","content":"hello html"}"""
            assertSerialization(input, content)
        }

        @Test
        fun `snippet json`() {
            val input = IndexServer.IndexResultList(
                    searchResults = listOf(IndexServer.SearchResult(
                            collection = "collection1",
                            documentId = 23,
                            documentTitle = "title1",
                            url = "google.com",
                            payload = ResultFormat.Snippet.StringWithMetadata(StringWithMetadata(
                                    "foo bar baz",
                                    emptyMap(),
                                    emptyList(),
                                    emptyList()
                            ), 0, 0, false)
                    )),
                    offset =mapOf("one" to Offset(10, 20))
            )
            val content = """"payload":{"type":"annotated","content":{"text":"foo bar baz","annotations":{},"positions":[],"queryMapping":[]}}"""
            assertSerialization(input, content)
        }

        @Test
        fun `identifier list`() {
            val input = IndexServer.IndexResultList(
                    searchResults = listOf(IndexServer.SearchResult(
                            collection = "collection1",
                            documentId = 23,
                            documentTitle = "title3",
                            url = "google.com",
                            payload = ResultFormat.IdentifierList(
                                    listOf(
                                            Identifier("x", ResultFormat.Snippet.StringWithMetadata(StringWithMetadata(
                                                    "foo bar baz",
                                                    emptyMap(),
                                                    emptyList(),
                                                    emptyList()
                                            ), 0, 0, false))
                                    )
                            )
                    )),
                    offset = mapOf("one" to Offset(10, 20))
            )
            val content = """"payload":{"type":"identifiers","list":[{"identifier":"x","snippet":{"type":"annotated","content":{"text":"foo bar baz","annotations":{},"positions":[],"queryMapping":[]}}}]}"""
            assertSerialization(input, content)
        }
    }

}