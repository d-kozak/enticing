package cz.vutbr.fit.knot.enticing.index.mg4j


import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnit
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.StructuredDocumentIterator
import cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.TextUnitListGeneratingVisitor
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.DocumentElement
import it.unimi.dsi.fastutil.io.FastBufferedInputStream
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File
import java.io.StringReader
import kotlin.math.max
import kotlin.math.min

private val corpusConfig = testConfiguration.corpusConfiguration
private val indexes = testConfiguration.indexes

fun assertStreamStartsWith(stream: Any, expected: String) {
    val actual = (stream as StringReader).readText().substring(0, expected.length)
    assertThat(actual)
            .isEqualTo(expected)
}

internal class Mg4jDocumentFactoryTest {


    @Test
    fun `load document test`() {
        val factory = Mg4jDocumentFactory(corpusConfig)
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), factory)

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
        val factory = Mg4jDocumentFactory(corpusConfig)
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), factory)

        val document = collection.document(0) as Mg4jDocument
        assertThat(document.uri())
                .isEqualTo("http://119.doorblog.jp/archives/51981348.html")


        assertStreamStartsWith(document.content(corpusConfig.indexes.size), "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 N P 0 0 0 0 0 0 0 0 0 0 N P 0 0 N P 0 0 0 N P 0 N P 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 N P 0 0 0 0 0 0 0 N P 0 0 0 0 0 0 0 0 0 0 0 0 0 N P")
    }


    @Test
    fun `glue index is present in snippet parts fields`() {
        val factory = Mg4jDocumentFactory(corpusConfig)
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), factory)

        val document = collection.document(0) as Mg4jDocument
        val snippetPartsFields = document.loadStructuredContent(filteredConfig = corpusConfig)
    }

    @Test
    fun `nerlength should be used to extend entities over multiple words`() {
        val factory = Mg4jDocumentFactory(corpusConfig)
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), factory)

        for (docId in 0L..10L) {
            val document = collection.document(docId)

            val content = document.loadStructuredContent(filteredConfig = corpusConfig)
            val nertag = content["nertag"]
            val nerlength = content["nerlength"].map { it.toInt() }

            val result = nertag.zip(nerlength)

            var i = 0
            while (i < result.size) {
                val (type, count) = result[i]
                if (count != 0 && count != -1) {
                    for (j in i + 1 until count) {
                        if (type != result[j].first) {
                            val sublist = result.subList(max(0, j - 5), min(j + 5, result.size))
                            System.err.println("... $sublist ...")
                            throw AssertionError("Expected type $type at index $j")
                        }
                    }
                    i += count
                } else {
                    i++
                }
            }
        }
    }

    @Test
    fun `all indexes should have the same length`() {
        val factory = Mg4jDocumentFactory(corpusConfig)
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), factory)

        for (i in 0L..10L) {
            val document = collection.document(i) as Mg4jDocument
            val wholeContent = document.loadStructuredContent(filteredConfig = corpusConfig).toMap()

            val sizePerIndex = wholeContent.values.map { it.size }
            val sizeSet = sizePerIndex.toSet()
            if (sizeSet.size != 1) {
                for ((name, content) in wholeContent) {
                    System.err.println("$name has size ${content.size}")
                }

                throw AssertionError("All indexes should have equal size, in this case(document ${document.title()} at $i) the result is $sizePerIndex")
            }
        }
    }

    @Nested
    inner class NerlenWorksBackwards {

        @Test
        fun `use only lowlevel factory-getdocument method`() {
            val document = initMockDocument()
            val nertagReader = (document.content(corpusConfig.entityIndex!!) as StringReader)
            assertThat(nertagReader.readText()).isEqualTo("0 0 0 person person 0 0 0")
            val nerlenReader = (document.content(corpusConfig.entityLengthIndex!!) as StringReader)
            assertThat(nerlenReader.readText()).isEqualTo("0 0 0 2 -1 0 0 0")
            for (i in corpusConfig.entityIndex!! + 1 until corpusConfig.entityLengthIndex!!) {
                val reader = (document.content(i) as StringReader)
                assertThat(reader.readText()).isEqualTo("0 0 0 X X 0 0 0")
            }
        }

        @Test
        fun `use  structured text`() {
            val document = initMockDocument()
            val elements = document.loadStructuredContent(0, document.size(), corpusConfig).elements
            assertThat(elements)
                    .hasSize(7)
            val harry = elements[3]
            assertThat(harry)
                    .isInstanceOf(DocumentElement.Entity::class.java)
            assertThat((harry as DocumentElement.Entity).words)
                    .hasSize(2)

            val words = document.iterator().asSequence().toList()
            assertThat(words).hasSize(8)
        }

        @Test
        fun `with eql result creator`() {
            val document = initMockDocument()
            val iterator = StructuredDocumentIterator(corpusConfig)
            val visitor = TextUnitListGeneratingVisitor(corpusConfig, "token", Interval.valueOf(0, document.size()), document)
            iterator.iterateDocument(document, emptyMap(), emptySet(), visitor)
            val result = visitor.build() as cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat.Snippet.TextUnitList
            val elems = result.content.content
            assertThat(elems).hasSize(7)
            val harry = elems[3]
            assertThat(harry).isInstanceOf(TextUnit.Entity::class.java)

        }

        private fun initMockDocument(): Mg4jDocument {
            val factory = Mg4jDocumentFactory(corpusConfig)
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
        processLine(input, output, 0, corpusConfig)
        val allEmpty = output.all { it.isEmpty() }
        println(allEmpty)
        assertThat(allEmpty).isTrue()

    }

    @Test
    fun `content of each index loaded by low level content should be identic to loading it using higher level readContentAt`() {
        val factory = Mg4jDocumentFactory(corpusConfig)
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), factory)

        for (i in 0L..10L) {
            val document = collection.document(i) as Mg4jDocument
            val wholeContent = document.loadStructuredContent(filteredConfig = corpusConfig)
            for (index in indexes) {
                val highLevel = wholeContent[index.name].joinToString(separator = " ")
                val lowLevel = (document.content(index.columnIndex) as StringReader).readText()
                assertThat(highLevel)
                        .isEqualTo(lowLevel)
            }
        }
    }

    @Test
    fun `number of fields test`() {
        val factory = Mg4jDocumentFactory(corpusConfig)
        assertThat(factory.numberOfFields())
                .isEqualTo(indexes.size)
    }

    @Test
    fun `field name test`() {
        val factory = Mg4jDocumentFactory(corpusConfig)
        for (i in 0 until indexes.size) {
            assertThat(factory.fieldName(i))
                    .isEqualTo(indexes[i].name)
        }
    }

    @Test
    fun `field index test`() {
        val factory = Mg4jDocumentFactory(corpusConfig)
        for (i in 0 until indexes.size) {
            assertThat(factory.fieldIndex(indexes[i].name))
                    .isEqualTo(i)
        }
    }

    @Test
    fun `field type test`() {
        val factory = Mg4jDocumentFactory(corpusConfig)
        for (i in 0 until indexes.size) {
            assertThat(factory.fieldType(i))
                    .isEqualTo(indexes[i].type.mg4jType)
        }
    }
}