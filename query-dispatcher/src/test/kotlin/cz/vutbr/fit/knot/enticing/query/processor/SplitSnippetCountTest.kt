package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SplitSnippetCountTest {


    private val query = SearchQuery("abc")

    @Test
    fun `100_000 even split between 5`() {
        val afterSplit = (0..4).map { i ->
            splitSnippetCount(i, 5, 100_000, query)
        }
        assertThat(afterSplit)
                .allMatch {
                    it.snippetCount == 20_000
                }
    }


    @Test
    fun `100 even split between 5`() {
        val afterSplit = (0..4).map { i ->
            splitSnippetCount(i, 5, 100, query)
        }
        assertThat(afterSplit)
                .allMatch {
                    it.snippetCount == 20
                }
    }

    @Test
    fun `20 even split between 5`() {
        val afterSplit = (0..4).map { i ->
            splitSnippetCount(i, 5, 20, query)
        }
        assertThat(afterSplit)
                .allMatch {
                    it.snippetCount == 4
                }
    }

    @Test
    fun `101 uneven split between 5`() {
        val afterSplit = (0..4).map { i ->
            splitSnippetCount(i, 5, 101, query)
        }
        assertThat(afterSplit[0].snippetCount).isEqualTo(21)
        assertThat(afterSplit.subList(1, afterSplit.size))
                .allMatch {
                    it.snippetCount == 20
                }
    }
}