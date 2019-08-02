package cz.vutbr.fit.knot.enticing.index.mg4j


import org.assertj.core.api.Assertions.assertThat
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
        val snippetPartsFields = document.loadSnippetPartsFields(filteredConfig = corpusConfig)
    }

    @Test
    fun `nerlength should be used to extend entities over multiple words`() {
        val factory = Mg4jDocumentFactory(corpusConfig)
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), factory)

        for (docId in 0L..10L) {
            val document = collection.document(docId) as Mg4jDocument

            val content = document.loadSnippetPartsFields(filteredConfig = corpusConfig)
            val nertag = content["nertag"]
            val nerlength = content["nerlength"].map { it.toInt() }

            val result = nertag.zip(nerlength)

            var i = 0
            while (i < result.size) {
                val (type, count) = result[i]
                if (count != 0) {
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
            val wholeContent = document.loadSnippetPartsFields(filteredConfig = corpusConfig).toMap()

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

    @Test
    fun `if token is missing line should be skipped`() {
        val input = """43		CC	0	1	NMOD	In	in	-42	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0"""

        val output = indexes.map { StringBuilder() }
        processLine(input, output, 0, corpusConfig)
        val allEmpty = output.map { it.isEmpty() }.all { it }
        println(allEmpty)
        assertThat(allEmpty).isTrue()

    }

    @Test
    fun `content of each index loaded by low level content should be identic to loading it using higher level readContentAt`() {
        val factory = Mg4jDocumentFactory(corpusConfig)
        val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/small.mg4j"), factory)

        for (i in 0L..10L) {
            val document = collection.document(i) as Mg4jDocument
            val wholeContent = document.loadSnippetPartsFields(filteredConfig = corpusConfig)
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