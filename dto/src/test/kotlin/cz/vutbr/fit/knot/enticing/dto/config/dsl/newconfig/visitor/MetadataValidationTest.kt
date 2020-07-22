package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.loadedConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.validateOrFail
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MetadataValidationTest {

    private val metadata = loadedConfiguration.corpuses.getValue("wiki-2018").metadataConfiguration

    @Test
    fun `invalid parent entity name`() {
        try {
            metadata.entities.getValue("date").parentEntityName = "foobar"
            assertThrows<IllegalStateException> {
                loadedConfiguration.validateOrFail()
            }
        } finally {
            metadata.entities.getValue("date").parentEntityName = null
        }
    }

    @Test
    fun `with cycle`() {
        try {
            metadata.entities.getValue("date").parentEntityName = "person"
            metadata.entities.getValue("person").parentEntityName = "artist"
            metadata.entities.getValue("artist").parentEntityName = "date"
            assertThrows<IllegalStateException> {
                loadedConfiguration.validateOrFail()
            }
        } finally {
            metadata.entities.getValue("date").parentEntityName = null
            metadata.entities.getValue("person").parentEntityName = null
            metadata.entities.getValue("artist").parentEntityName = null
        }
    }

    @Test
    fun `artist is a person`() {
        val artist = metadata.entities.getValue("artist")
        try {
            artist.parentEntityName = "person"
            loadedConfiguration.validateOrFail()
            assertThat(artist.fullName).isEqualTo("person->artist")
        } finally {
            artist.parentEntityName = null
        }
    }
}