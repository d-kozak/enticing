package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.loadedConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.validateOrFail
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
}