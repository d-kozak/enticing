package cz.vutbr.fit.knot.enticing.indexer.mg4j

import it.unimi.dsi.fastutil.io.FastBufferedInputStream
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

    @Test
    fun `Check input stream`() {
        val collection = Mg4jSingleFileDocumentCollection(File("src/test/resources/input/small.mg4j"), dummyDocumentFactory)

        val buffer = ByteArray(1024)

        for ((index, content) in listOf(
                0L to "%%#DOC\t819d48be-5472-57cb-b0f6-437d774e1250",
                1L to "%%#DOC\t2fbfcdc7-e19b-5df7-8762-91a0e3afafc9",
                2L to "%%#DOC\t4f6ae197-717f-5e02-b1b7-18f5265b534f",
                15L to "%%#DOC\t332acc18-3c79-5086-8ade-12aafff18212"
        )) {
            val stream = collection.stream(index) as FastBufferedInputStream
            val lineSize = stream.readLine(buffer)
            assertThat(lineSize)
                    .isGreaterThan(0)
            val asString = String(buffer, 0, lineSize)
            assertThat(asString)
                    .isEqualTo(content)
        }
    }

    @Test
    fun `Check metadata`() {
        val collection = Mg4jSingleFileDocumentCollection(File("src/test/resources/input/small.mg4j"), dummyDocumentFactory)
        for ((index, title, uri) in listOf(
                Triple(2L, "Toy Soldiers Studio: II/20c Ptolemaic", "http://15mm25mm.blogspot.com/2014/07/ii20c-ptolemaic.html"),
                Triple(3L, " - Writing Treatments That Sell: How to Create and Market Your Story Ideas to the Motion Picture and TV Industry, Second Edition", "http://1d-film.ru/books/2595289/"),
                Triple(15L, "None", "http://90daysdrydreamingofpimms.blogspot.com/2017/11/day-62-freedom-and-falling-apart-car.html")
        )) {
            val metadata = collection.metadata(index)
            assertThat(metadata[DocumentMetaData.TITLE])
                    .isEqualTo(title)
            assertThat(metadata[DocumentMetaData.URI])
                    .isEqualTo(uri)
        }
    }
}