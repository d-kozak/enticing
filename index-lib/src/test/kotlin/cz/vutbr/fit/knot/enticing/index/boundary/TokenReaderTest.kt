package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.index.utils.testDocument
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class IndexDocumentIteratorText {

    @Test
    fun `simple iteration`() {
        val dummyDocument = testDocument("one two three four five", "1 2 3 4 5")
        assertThat(dummyDocument.toList())
                .isEqualTo(listOf(
                        listOf("one", "1"),
                        listOf("two", "2"),
                        listOf("three", "3"),
                        listOf("four", "4"),
                        listOf("five", "5")
                ))
    }

    @Nested
    inner class WordReaderTest {

        @Test
        fun `read sentence`() {
            val input = listOf(
                    "one two three four five",
                    "1 2 3 4 5")
            val reader = WordReader(input, ' ')
            assertThat(reader.nextWord()).isEqualTo(listOf("one", "1"))
            assertThat(reader.nextWord()).isEqualTo(listOf("two", "2"))
            assertThat(reader.nextWord()).isEqualTo(listOf("three", "3"))
            assertThat(reader.nextWord()).isEqualTo(listOf("four", "4"))
            assertThat(reader.nextWord()).isEqualTo(listOf("five", "5"))
            assertThat(reader.nextWord()).isNull()
            assertThat(reader.nextWord()).isNull()
            assertThat(reader.nextWord()).isNull()
        }
    }


    @Nested
    inner class TokenReaderTest {

        @Test
        fun `read sentence separator space`() {
            val input = "Hello darkness , my old friend"
            val reader = TokenReader(input, ' ')
            assertThat(reader.nextToken()).isEqualTo("Hello")
            assertThat(reader.nextToken()).isEqualTo("darkness")
            assertThat(reader.nextToken()).isEqualTo(",")
            assertThat(reader.nextToken()).isEqualTo("my")
            assertThat(reader.nextToken()).isEqualTo("old")
            assertThat(reader.nextToken()).isEqualTo("friend")
            assertThat(reader.nextToken()).isNull()
            assertThat(reader.nextToken()).isNull()
            assertThat(reader.nextToken()).isNull()
        }

        @Test
        fun `read empty sentence returns null`() {
            val reader = TokenReader("", ' ')
            assertThat(reader.nextToken()).isNull()
            assertThat(reader.nextToken()).isNull()
            assertThat(reader.nextToken()).isNull()
        }

        @Test
        fun `read sentence separator tab`() {
            val input = "Last\tChristmas\t,\tI\tgave\tyou\tmy\theart"
            val reader = TokenReader(input, '\t')
            assertThat(reader.nextToken()).isEqualTo("Last")
            assertThat(reader.nextToken()).isEqualTo("Christmas")
            assertThat(reader.nextToken()).isEqualTo(",")
            assertThat(reader.nextToken()).isEqualTo("I")
            assertThat(reader.nextToken()).isEqualTo("gave")
            assertThat(reader.nextToken()).isEqualTo("you")
            assertThat(reader.nextToken()).isEqualTo("my")
            assertThat(reader.nextToken()).isEqualTo("heart")
            assertThat(reader.nextToken()).isNull()
            assertThat(reader.nextToken()).isNull()
            assertThat(reader.nextToken()).isNull()
        }
    }
}

