package cz.vutbr.fit.knot.enticing.indexer.mg4j

import cz.vutbr.fit.knot.enticing.indexer.configuration.testConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import java.io.InputStream

private val indexes = testConfiguration.indexes

fun assertStreamStartsWith(stream: Any, expected: String) {
    val actual = (stream as InputStream).bufferedReader().use { it.readLine().substring(0, expected.length) }
    assertThat(actual)
            .isEqualTo(expected)
}

internal class Mg4jDocumentFactoryTest {


    @Test
    fun `load document test`() {
        val factory = Mg4jDocumentFactory(indexes)
        val collection = Mg4jSingleFileDocumentCollection(File("src/test/resources/input/small.mg4j"), factory)

        val document = collection.document(2)
        assertThat(document.title())
                .isEqualTo("Toy Soldiers Studio: II/20c Ptolemaic")
        assertThat(document.uri())
                .isEqualTo("http://15mm25mm.blogspot.com/2014/07/ii20c-ptolemaic.html")


        assertStreamStartsWith(document.content(0), "1 2 1 2 3 4 5 6 7 8")
        assertStreamStartsWith(document.content(1), "II/20c Ptolemaic 166 BC - 54 BC ( Hellenistic|G__ - Egyptian )|G__ DBA|G__ 2.2Army Composition :|G__ ( 14|G__ elements )|G")
    }

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