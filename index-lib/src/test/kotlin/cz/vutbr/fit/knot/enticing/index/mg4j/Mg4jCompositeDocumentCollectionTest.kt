package cz.vutbr.fit.knot.enticing.index.mg4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File

val files = listOf(
        "../data/mg4j/cc1.mg4j",
        "../data/mg4j/cc2.mg4j",
        "../data/mg4j/cc3.mg4j"
).map { File(it) }

internal class Mg4jCompositeDocumentCollectionTest {


    @Test
    fun `load and check size test`() {
        val collection = Mg4jCompositeDocumentCollection(testConfiguration.corpusConfiguration, files)
        assertThat(collection.size())
                .isEqualTo(146 + 146 + 158)
    }

    @Test
    fun `load from first collection`() {
        val collection = Mg4jCompositeDocumentCollection(testConfiguration.corpusConfiguration, files)
        assertThat(collection.document(2).title())
                .isEqualTo("Toy Soldiers Studio: II/20c Ptolemaic")
        assertThat(collection.document(145).title())
                .isEqualTo("celebrity weight loss secrets revealed best way to lose weight in your 40s ¦ best way lose weight fast after 40 diet for women over 40")
    }

    @Test
    fun `load from second collection`() {
        val collection = Mg4jCompositeDocumentCollection(testConfiguration.corpusConfiguration, files)
        assertThat(collection.document(146 + 0).title())
                .isEqualTo("Disclaimer - Automated Exemption System")
        assertThat(collection.document(146 + 3).title())
                .isEqualTo("Missouri Rice Research ¦ Rice Weed Control 101")

        assertThat(collection.document(146 + 145).title())
                .isEqualTo("Billboard Radio China - 2018 American Music Awards Performances Ranked")
    }

    @Test
    fun `load from third collection`() {
        val collection = Mg4jCompositeDocumentCollection(testConfiguration.corpusConfiguration, files)
        assertThat(collection.document(146 + 146 + 0).title())
                .isEqualTo("Beijing City Guide")
        assertThat(collection.document(146 + 146 + 7).title())
                .isEqualTo("Google Turkiye - Best Prices 2019")

        assertThat(collection.document(146 + 146 + 157).title())
                .isEqualTo("Barton upon Humber")
    }

    @Test
    fun `load from multiple collections in random order`() {
        val collection = Mg4jCompositeDocumentCollection(testConfiguration.corpusConfiguration, files)
        val input = mutableListOf(2L to "Toy Soldiers Studio: II/20c Ptolemaic",
                145L to "celebrity weight loss secrets revealed best way to lose weight in your 40s ¦ best way lose weight fast after 40 diet for women over 40",
                146L + 3L to "Missouri Rice Research ¦ Rice Weed Control 101",
                146L + 146L + 7L to "Google Turkiye - Best Prices 2019"
        )
        input.shuffle()

        for ((index, title) in input) {
            assertThat(collection.document(index).title())
                    .isEqualTo(title)
        }
    }

    @Nested
    inner class DocumentRanges {
        @Test
        fun `simple example`() {
            assertThat(initDocumentRanges(listOf(3L, 3L, 3L)))
                    .isEqualTo(longArrayOf(2, 5, 8))
        }

        @Test
        fun `bigger example`() {
            assertThat(initDocumentRanges(listOf(146L, 146L, 158L)))
                    .isEqualTo(longArrayOf(145, 291, 449))
        }

    }

}