package cz.vutbr.fit.knot.enticing.dto.query

import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import org.assertj.core.api.Assertions.assertThat

inline fun <reified T> assertSerialization(input: T, content: String) {
    val serialized = input.toJson()
    assertThat(serialized)
            .contains(content)
    val parsed = serialized.toDto<T>()
    assertThat(parsed)
            .isEqualTo(input)
}