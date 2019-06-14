package cz.vutbr.fit.knot.enticing.indexer.mg4j

import cz.vutbr.fit.knot.enticing.indexer.configuration.testConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private val indexes = testConfiguration.indexes

internal class Mg4jDocumentFactoryTest {

    @Test
    fun `number of fields test`() {
        val factory = Mg4jDocumentFactory(indexes)
        assertThat(factory.numberOfFields())
                .isEqualTo(indexes.size)
    }

    @Test
    fun `field name test`() {
        val factory = Mg4jDocumentFactory(indexes)
        for (i in 0 until indexes.size) {
            assertThat(factory.fieldName(i))
                    .isEqualTo(indexes[i].name)
        }
    }

    @Test
    fun `field index test`() {
        val factory = Mg4jDocumentFactory(indexes)
        for (i in 0 until indexes.size) {
            assertThat(factory.fieldIndex(indexes[i].name))
                    .isEqualTo(i)
        }
    }

    @Test
    fun `field type test`() {
        val factory = Mg4jDocumentFactory(indexes)
        for (i in 0 until indexes.size) {
            assertThat(factory.fieldType(i))
                    .isEqualTo(indexes[i].type.mg4jType)
        }
    }
}