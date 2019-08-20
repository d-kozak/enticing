package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.next

import cz.vutbr.fit.knot.enticing.dto.config.dsl.*
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.utils.testDocument
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class TestListener : DocumentIteratorListener {

    val content: MutableList<Pair<String, Any?>> = mutableListOf()

    override fun matchStart(queryInterval: Interval) {
        content.add("ms" to queryInterval)
    }

    override fun matchEnd() {
        content.add("me" to null)
    }

    override fun entityStart(attributes: List<String>, entityClass: String) {
        content.add("es" to Pair(attributes, entityClass))
    }

    override fun entityEnd() {
        content.add("ee" to null)
    }

    override fun word(indexes: List<String>) {
        content.add("w" to indexes)
    }
}

internal class StructuredDocumentIteratorTest {
    private val withEntities = corpusConfig("simple") {
        indexes {
            index("token")
            index("pos")
            index("nertag")
            index("param")
            index("len")
        }
        entities {
            "person" with attributes("name")
        }
        entityMapping {
            entityIndex = "nertag"
            lengthIndex = "len"

            attributeIndexes = 3 to 3
        }
    }.also { it.validate() }


    @Test
    fun `entity is split by match from entity starts first`() {
        val iterator = StructuredDocumentIterator(withEntities)
        val document = testDocument(
                "Country roads , take Jan Novak home",
                "1 2 3 4 5 6 7",
                "0 0 0 0 person 0 0",
                "0 0 0 0 Jan_Novak 0 0",
                "0 0 0 0 2 0 0"
        )
        val listener = TestListener()
        iterator.iterateDocument(document,
                matchStarts = mapOf(5 to Interval.valueOf(42)),
                matchEnds = setOf(6),
                listener = listener)
        assertThat(listener.content)
                .isEqualTo(listOf(
                        "w" to listOf("Country", "1", "0", "0", "0")
                ))

    }

}