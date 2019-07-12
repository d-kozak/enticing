package cz.vutbr.fit.knot.enticing.dto.utils

import cz.vutbr.fit.knot.enticing.dto.query.DocumentQuery
import cz.vutbr.fit.knot.enticing.dto.query.TextMetadata
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ExtendedTest {

    @Nested
    inner class Jackson {


        @Test
        fun `document query`() {
            val query = DocumentQuery("col1", 1, TextMetadata.Predefined("none"), query = "foo") with ExtraInfo("google.com")

            val serialized = query.toJson()

            println(serialized)

            val parsed = serialized.toDto<Extended<DocumentQuery, ExtraInfo>>()
            assertThat(parsed)
                    .isEqualTo(query)
        }
    }
}