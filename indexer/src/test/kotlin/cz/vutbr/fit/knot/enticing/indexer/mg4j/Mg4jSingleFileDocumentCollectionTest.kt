package cz.vutbr.fit.knot.enticing.indexer.mg4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

val dummyDocumentFactory = Mg4jDocumentFactory(emptyList())

internal class Mg4jSingleFileDocumentCollectionTest {


    @Test
    fun `Load collection and check size`() {

        val inputFiles = listOf(
                "src/test/resources/input/small.mg4j" to 16L,
                "src/test/resources/input/cc1.mg4j" to 146L,
                "src/test/resources/input/cc2.mg4j" to 146L,
                "src/test/resources/input/cc3.mg4j" to 158L
        )
        for ((path, count) in inputFiles) {
            val collection = Mg4jSingleFileDocumentCollection(File(path), dummyDocumentFactory)
            assertThat(collection.size())
                    .isEqualTo(count)
        }
    }
}