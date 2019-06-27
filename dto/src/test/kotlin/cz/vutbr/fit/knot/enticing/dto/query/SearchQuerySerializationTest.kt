package cz.vutbr.fit.knot.enticing.dto.query

import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal val templateQuery = SearchQuery(
        "foo bar baz",
        42,
        Offset(10, 10),
        metadata = Metadata.Predefined("all"),
        responseType = ResponseType.SNIPPET,
        responseFormat = ResponseFormat.JSON,
        defaultIndex = "lemma"
)

class SearchQuerySerializationTest {


    @Test

    fun `metadata all`() {
        val input = templateQuery.copy(metadata = Metadata.Predefined("all"))

        val serialized = input.toJson()
        assertThat(serialized)
                .contains(""""metadata":{"type":"predef","value":"all"}""")

        val parsed = serialized.toDto<SearchQuery>()
        assertThat(parsed)
                .isEqualTo(input)
    }

    @Test
    fun `indexes all entities all`() {
        val input = templateQuery.copy(metadata = Metadata.ExactDefinition(
                entities = Entities.Predefined("none"),
                indexes = Indexes.Predefined("all")
        ))

        val serialized = input.toJson()
        assertThat(serialized)
                .contains(""""metadata":{"type":"exact","entities":{"type":"predef","value":"none"},"indexes":{"type":"predef","value":"all"}}""")
        val parsed = serialized.toDto<SearchQuery>()
        assertThat(parsed)
                .isEqualTo(input)
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
        val serialized = input.toJson()
        assertThat(serialized)
                .contains(""""metadata":{"type":"exact","entities":{"type":"exact","entities":{"person":{"type":"predef","value":"all"},"place":{"type":"predef","value":"none"}}}""")
        val parsed = serialized.toDto<SearchQuery>()
        assertThat(parsed)
                .isEqualTo(input)
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
        val serialized = input.toJson()
        assertThat(serialized)
                .contains(""""metadata":{"type":"exact","entities":{"type":"exact","entities":{"person":{"type":"exact","names":["one","two","three"]},"place":{"type":"exact","names":["three","four","five"]}}}""")

        val parsed = serialized.toDto<SearchQuery>()
        assertThat(parsed)
                .isEqualTo(input)
    }
}