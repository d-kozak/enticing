package cz.vutbr.fit.knot.enticing.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MergeCorpusFormatTest {


    @Test
    fun `merge two formats`() {
        val left = CorpusFormat("foo",
                listOf("token", "url", "lemma"),
                mapOf("person" to listOf("name", "age", "gender"),
                        "location" to listOf("country", "name")))

        val right = CorpusFormat("foo",
                listOf("token", "image", "url"),
                mapOf("person" to listOf("age", "name", "image"),
                        "date" to listOf("year", "month", "day"),
                        "location" to emptyList()
                ))

        assertThat(mergeCorpusFormats(left, right))
                .isEqualTo(CorpusFormat("foo", listOf("token", "url"),
                        mapOf("person" to listOf("name", "age"),
                                "location" to emptyList())))
    }


    @Test
    fun `merge three formats`() {
        val one = CorpusFormat("foo",
                listOf("token", "url", "lemma"),
                mapOf("person" to listOf("name", "age", "gender"),
                        "location" to listOf("country", "name")))

        val two = CorpusFormat("bar",
                listOf("token", "image", "url"),
                mapOf("person" to listOf("age", "name", "image"),
                        "date" to listOf("year", "month", "day"),
                        "location" to emptyList()
                ))

        val three = CorpusFormat("baz",
                listOf("url"),
                mapOf("person" to listOf("image"),
                        "date" to listOf("year", "month", "day")
                ))

        assertThat(mergeCorpusFormats(one, two, three))
                .isEqualTo(CorpusFormat("foo-bar-baz", listOf("url"),
                        mapOf("person" to listOf())))
    }


}