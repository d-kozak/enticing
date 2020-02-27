package cz.vutbr.fit.knot.enticing.index.mg4j


import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnit
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.StructuredDocumentIterator
import cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.TextUnitListGeneratingVisitor
import cz.vutbr.fit.knot.enticing.index.testconfig.dummyLogger
import it.unimi.dsi.fastutil.io.FastBufferedInputStream
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File
import java.io.StringReader

private val indexes = fullTestMetadataConfig.indexes.values.toList()

fun assertStreamStartsWith(stream: Any, expected: String) {
    val actual = (stream as StringReader).readText().substring(0, expected.length)
    assertThat(actual)
            .isEqualTo(expected)
}

internal class Mg4jDocumentFactoryTest {

    @Nested
    inner class NewWiki {
        @Test
        fun `load document test`() {
            val factory = Mg4jDocumentFactory(fullTestMetadataConfig, dummyLogger)
            val collection = Mg4jSingleFileDocumentCollection(File("../data/new_wiki/new_wiki.mg4j"), factory, dummyLogger)
            for (i in 0 until collection.size()) {
                val document = collection.document(i)
                println(document.title)
            }
        }

    }


    @Test
    fun `load document test`() {
        val factory = Mg4jDocumentFactory(fullTestMetadataConfig, dummyLogger)
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), factory, dummyLogger)

        val document = collection.document(2)
        assertThat(document.title())
                .isEqualTo("Toy Soldiers Studio: II/20c Ptolemaic")
        assertThat(document.uri())
                .isEqualTo("http://15mm25mm.blogspot.com/2014/07/ii20c-ptolemaic.html")


        assertStreamStartsWith(document.content(0), "0 0 1 2 0 0 1 2 3 4 5 6 7 8")
        assertStreamStartsWith(document.content(1), "§ ¶ II/20c Ptolemaic § ¶ 166 BC - 54 BC ( Hellenistic - Egyptian ) DBA 2.2Army Composition : ( 14 elements ) 1")
    }

    @Test
    fun `special glue index is present`() {
        val factory = Mg4jDocumentFactory(fullTestMetadataConfig, dummyLogger)
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), factory, dummyLogger)

        val document = collection.document(0) as Mg4jDocument
        assertThat(document.uri())
                .isEqualTo("http://119.doorblog.jp/archives/51981348.html")


        assertStreamStartsWith(document.content(fullTestMetadataConfig.indexOf("_glue")), "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 N P 0 0 0 0 0 0 0 0 0 0 N P 0 0 N P 0 0 0 N P 0 N P 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 N P 0 0 0 0 0 0 0 N P 0 0 0 0 0 0 0 0 0 0 0 0 0 N P")
    }





    @Nested
    inner class NerlenWorksBackwards {

        @Test
        fun `use only lowlevel factory-getdocument method`() {
            val document = initMockDocument()
            val nertagReader = (document.content(fullTestMetadataConfig.entityIndex!!.columnIndex) as StringReader)
            assertThat(nertagReader.readText()).isEqualTo("0 0 0 person person 0 0 0")
            val nerlenReader = (document.content(fullTestMetadataConfig.lengthIndex!!.columnIndex) as StringReader)
            assertThat(nerlenReader.readText()).isEqualTo("0 0 0 2 -1 0 0 0")
            for (i in fullTestMetadataConfig.entityIndex!!.columnIndex + 1 until fullTestMetadataConfig.lengthIndex!!.columnIndex) {
                val reader = (document.content(i) as StringReader)
                assertThat(reader.readText()).isEqualTo("0 0 0 X X 0 0 0")
            }
        }

        @Test
        fun `with eql result creator`() {
            val document = initMockDocument()
            val iterator = StructuredDocumentIterator(fullTestMetadataConfig, dummyLogger)
            val visitor = TextUnitListGeneratingVisitor(fullTestMetadataConfig, "token", Interval.valueOf(0, document.size()), document, dummyLogger)
            iterator.iterateDocument(document, emptyMap(), emptySet(), visitor)
            val result = visitor.build() as cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat.Snippet.TextUnitList
            val elems = result.content.content
            assertThat(elems).hasSize(7)
            val harry = elems[3]
            assertThat(harry).isInstanceOf(TextUnit.Entity::class.java)

        }

        private fun initMockDocument(): Mg4jDocument {
            val factory = Mg4jDocumentFactory(fullTestMetadataConfig, dummyLogger)
            val inputStream = FastBufferedInputStream("""
                    %%#DOC	2c25c27f-60b1-541b-a5fc-287c7c4318c5
                    %%#PAGE Disclaimer - Automated Exemption System	http://aes.faa.gov/AES/Help
                    %%#PAR 1 wx1
                    %%#SEN 1 wx1
                    1	Hello	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
                    2	Harry	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
                    3	Potter	0	0	0	0	0	0	0	0	0	0	0	0	person	X	X	X	X	X	X	X	X	X	X	X	2
                    4	How	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
                    5	are	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
                    6	you	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
                """.trimIndent().byteInputStream())
            val metadata = Reference2ObjectArrayMap<Enum<*>, Any>()
            metadata[DocumentMetadata.ID] = 42
            metadata[DocumentMetadata.TITLE] = "title"
            metadata[DocumentMetadata.URI] = "uri"
            return factory.getDocument(inputStream, metadata)
        }

    }

    @Test
    fun `if token is missing line should be skipped`() {
        val input = """43		CC	0	1	NMOD	In	in	-42	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0"""

        val output = indexes.map { mutableListOf<String>() }
        processLine(input, output, 0, fullTestMetadataConfig, dummyLogger)
        val allEmpty = output.all { it.isEmpty() }
        println(allEmpty)
        assertThat(allEmpty).isTrue()

    }

    @Test
    fun `number of fields test`() {
        val factory = Mg4jDocumentFactory(fullTestMetadataConfig, dummyLogger)
        assertThat(factory.numberOfFields())
                .isEqualTo(indexes.size)
    }

    @Test
    fun `field name test`() {
        val factory = Mg4jDocumentFactory(fullTestMetadataConfig, dummyLogger)
        for (i in 0 until indexes.size) {
            assertThat(factory.fieldName(i))
                    .isEqualTo(indexes[i].name)
        }
    }

    @Test
    fun `field index test`() {
        val factory = Mg4jDocumentFactory(fullTestMetadataConfig, dummyLogger)
        for (i in 0 until indexes.size) {
            assertThat(factory.fieldIndex(indexes[i].name))
                    .isEqualTo(i)
        }
    }
}