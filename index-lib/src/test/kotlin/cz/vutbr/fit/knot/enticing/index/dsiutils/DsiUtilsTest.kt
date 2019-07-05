package cz.vutbr.fit.knot.enticing.index.dsiutils

import cz.vutbr.fit.knot.enticing.index.mg4j.WhitespaceWordReader
import it.unimi.dsi.lang.MutableString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * These tests don't test any components of the system directly, but they serve to verify my assumptions about
 * the underlying mg4j library and it's additional libraries
 */
class DsiUtilsTest {

    @Test
    fun `whitespace word reader test`() {
        val inputReader = "Once      upon a \n\ntime %$%$^ in a galaxy far away from         here".reader()
        val whitespaceReader = WhitespaceWordReader()
        val combined = whitespaceReader.setReader(inputReader)

        val word = MutableString()
        val nonWord = MutableString()

        val words = mutableListOf<String>()
        while (combined.next(word, nonWord)) {
            println("word: $word, nonword: $nonWord")
            words.add(word.toString())
        }
        assertThat(words)
                .isEqualTo(
                        listOf("Once", "upon", "a", "time", "%$%$^", "in", "a", "galaxy", "far", "away", "from", "here")
                )
    }
}