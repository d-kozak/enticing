package cz.vutbr.fit.knot.enticing.index.mg4j

import it.unimi.dsi.fastutil.io.FastBufferedInputStream
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File

val testDocumentFactory = Mg4jDocumentFactory(fullTestMetadataConfig)

internal class Mg4jSingleFileDocumentCollectionTest {


    @Test
    fun `Load collection and check size`() {

        val inputFiles = listOf(
                "../data/mg4j/small.mg4j" to 16L,
                "../data/mg4j/cc1.mg4j" to 146L,
                "../data/mg4j/cc2.mg4j" to 146L,
                "../data/mg4j/cc3.mg4j" to 158L
        )
        for ((path, count) in inputFiles) {
            val collection = Mg4jSingleFileDocumentCollection(File(path), testDocumentFactory)
            assertThat(collection.size())
                    .isEqualTo(count)
        }
    }

    @Nested
    inner class ReadRawDocument {

        private val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), testDocumentFactory)

        @Test
        fun `whole document`() {
            assertThat(collection.getRawDocument(2))
                    .startsWith("""
                    %%#DOC	4f6ae197-717f-5e02-b1b7-18f5265b534f
                    %%#PAGE Toy Soldiers Studio: II/20c Ptolemaic	http://15mm25mm.blogspot.com/2014/07/ii20c-ptolemaic.html
                    %%#PAR 1 wx1
                    %%#SEN 1 wx1
                    1	II/20c	NN	II/20c	0	ROOT	0	0	0	0	0	0	ii/20c	0	0	0	0	0	0	0	0	0	0	0	0	0	0
                    2	Ptolemaic	JJ	Ptolemaic	0	ROOT	0	0	0	0	0	0	ptolemaic	0	0	0	0	0	0	0	0	0	0	0	0	0	0
                    %%#PAR 2 wx2
                    %%#SEN 2 wx2
                """.trimIndent())
        }

        @Test
        fun `first line`() {
            assertThat(collection.getRawDocument(2, from = 0, to = 1)).isEqualTo("%%#DOC\t4f6ae197-717f-5e02-b1b7-18f5265b534f\n")
        }

        @Test
        fun `inside the document`() {
            assertThat(collection.getRawDocument(2, from = 3, to = 6)).isEqualTo("""
                    %%#SEN 1 wx1
                    1	II/20c	NN	II/20c	0	ROOT	0	0	0	0	0	0	ii/20c	0	0	0	0	0	0	0	0	0	0	0	0	0	0
                    2	Ptolemaic	JJ	Ptolemaic	0	ROOT	0	0	0	0	0	0	ptolemaic	0	0	0	0	0	0	0	0	0	0	0	0	0	0
                    
                    """.trimIndent())
        }

    }



    @Test
    fun `Check input stream`() {
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), testDocumentFactory)

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
            val str = String(buffer, 0, lineSize)
            assertThat(str)
                    .isEqualTo(content)
        }
    }

    @Test
    fun `Check metadata`() {
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), testDocumentFactory)
        for ((index, title, uri) in listOf(
                Triple(2L, "Toy Soldiers Studio: II/20c Ptolemaic", "http://15mm25mm.blogspot.com/2014/07/ii20c-ptolemaic.html"),
                Triple(3L, " - Writing Treatments That Sell: How to Create and Market Your Story Ideas to the Motion Picture and TV Industry, Second Edition", "http://1d-film.ru/books/2595289/"),
                Triple(15L, "None", "http://90daysdrydreamingofpimms.blogspot.com/2017/11/day-62-freedom-and-falling-apart-car.html")
        )) {
            val metadata = collection.metadata(index)
            assertThat(metadata[DocumentMetadata.TITLE])
                    .isEqualTo(title)
            assertThat(metadata[DocumentMetadata.URI])
                    .isEqualTo(uri)
        }
    }
}