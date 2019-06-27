package cz.vutbr.fit.knot.enticing.dto.query

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class DtoSerializationTest {

    @Nested
    inner class SearchQuery {
        private val templateQuery = SearchQuery(
                "foo bar baz",
                42,
                Offset(10, 10),
                metadata = Metadata.Predefined("all"),
                responseType = ResponseType.SNIPPET,
                responseFormat = ResponseFormat.JSON,
                defaultIndex = "lemma"
        )

        @Test
        fun `metadata all`() {
            val input = templateQuery.copy(metadata = Metadata.Predefined("all"))
            val content = """"metadata":{"type":"predef","value":"all"}"""
            assertSerialization(input, content)
        }

        @Test
        fun `indexes all entities all`() {
            val input = templateQuery.copy(metadata = Metadata.ExactDefinition(
                    entities = Entities.Predefined("none"),
                    indexes = Indexes.Predefined("all")
            ))
            val content = """"metadata":{"type":"exact","entities":{"type":"predef","value":"none"},"indexes":{"type":"predef","value":"all"}}"""
            assertSerialization(input, content)
        }

        @Test
        fun `indexes exact entities for person place`() {
            val input = templateQuery.copy(metadata = Metadata.ExactDefinition(
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
            val input = templateQuery.copy(metadata = Metadata.ExactDefinition(
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


}