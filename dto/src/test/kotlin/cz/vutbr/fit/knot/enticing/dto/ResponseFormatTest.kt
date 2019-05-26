package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ResponseFormatTest {

    @Test
    fun `To JSON test`() {
        val nertags: NertagsFormat = NertagsFormat.SimpleDefinition("foo")

        val serialized = nertags.toJson()
        println(serialized)

        val parsed = serialized.toDto<NertagsFormat>()
        assertThat(parsed).isEqualTo(nertags)
    }
}