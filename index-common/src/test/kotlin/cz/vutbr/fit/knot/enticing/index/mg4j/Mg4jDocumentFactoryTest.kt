package cz.vutbr.fit.knot.enticing.index.mg4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import java.io.InputStreamReader

private val indexes = testConfiguration.indexes

fun assertStreamStartsWith(stream: Any, expected: String) {
    val actual = (stream as InputStreamReader).readText().substring(0, expected.length)
    assertThat(actual)
            .isEqualTo(expected)
}

internal class Mg4jDocumentFactoryTest {


    @Test
    fun `load document test`() {
        val factory = Mg4jDocumentFactory(indexes)
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), factory)

        val document = collection.document(2)
        assertThat(document.title())
                .isEqualTo("Toy Soldiers Studio: II/20c Ptolemaic")
        assertThat(document.uri())
                .isEqualTo("http://15mm25mm.blogspot.com/2014/07/ii20c-ptolemaic.html")


        assertStreamStartsWith(document.content(0), "1 2 1 2 3 4 5 6 7 8")
        assertStreamStartsWith(document.content(1), "II/20c Ptolemaic 166 BC - 54 BC ( Hellenistic|G__ - Egyptian )|G__ DBA|G__ 2.2Army Composition :|G__ ( 14|G__ elements )|G")
    }

    @Test
    fun `all indexes should have the same length`() {
        val factory = Mg4jDocumentFactory(indexes)
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), factory)

        for (i in 0..10) {
            val document = collection.document(1) as Mg4jDocument
            val wholeContent = document.wholeContent()

            val sizePerIndex = wholeContent.values.map { it.size }
            val sizeSet = sizePerIndex.toSet()
            if (sizeSet.size != 1) {
                throw AssertionError("All indexes should have equal size, in this case the result is $sizePerIndex")
            }
        }
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