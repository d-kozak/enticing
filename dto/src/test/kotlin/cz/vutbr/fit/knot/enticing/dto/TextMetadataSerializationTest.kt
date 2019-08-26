package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class TextMetadataSerializationTest {


    @Test
    fun parseTest() {
        val input = """{"indexes":{"names":["token","parlemma"],"type":"exact"},"entities":{"entities":{},"type":"exact"},"type":"exact"}"""
        assertThat(input.toDto<TextMetadata>())
                .isEqualTo(TextMetadata.ExactDefinition(Entities.ExactDefinition(emptyMap()), Indexes.ExactDefinition(listOf("token", "parlemma"))))
    }

    @Test
    fun twoWayTest() {
        val input = TextMetadata.ExactDefinition(Entities.ExactDefinition(emptyMap()), Indexes.ExactDefinition(listOf("token", "parlemma")))
        assertThat(input.toJson().toDto<TextMetadata>())
                .isEqualTo(input)
    }
}