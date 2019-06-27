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
}