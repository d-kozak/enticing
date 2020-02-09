package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.metadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.utils.testDocument
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


private class TestVisitor : DocumentVisitor {

    val content: MutableList<Pair<String, Any?>> = mutableListOf()

    override fun visitMatchStart(queryInterval: Interval) {
        content.add("ms" to queryInterval)
    }

    override fun visitMatchEnd() {
        content.add("me" to null)
    }

    override fun visitEntityStart(attributes: List<String>, entityClass: String) {
        content.add("es" to Pair(attributes, entityClass))
    }

    override fun visitEntityEnd() {
        content.add("ee" to null)
    }

    override fun visitWord(indexes: List<String>) {
        content.add("w" to indexes)
    }
}

internal class StructuredDocumentIteratorTest {
    private val withEntities = metadataConfiguration {
        indexes {
            index("token")
            index("pos")
            index("nertag")
            attributeIndexes(1)
            index("len")
        }
        entities {
            "person" with attributes("name")
        }
        lengthIndexName = "len"
    }.also { it.validateOrFail() }


    @Test
    fun `entity and match start at the same index and match goes on`() {
        val iterator = StructuredDocumentIterator(withEntities)
        val document = testDocument(8,
                "Country roads , take Jan Novak home foo",
                "1 2 3 4 5 6 7 8",
                "0 0 0 0 person 0 0 0",
                "0 0 0 0 Jan_Novak 0 0 0",
                "0 0 0 0 2 0 0 0"
        )
        val visitor = TestVisitor()
        iterator.iterateDocument(document,
                matchStarts = mapOf(4 to Interval.valueOf(42)),
                matchEnds = setOf(7),
                visitor = visitor)
        assertThat(visitor.content)
                .isEqualTo(listOf(
                        "w" to listOf("Country", "1", "0", "0", "0"),
                        "w" to listOf("roads", "2", "0", "0", "0"),
                        "w" to listOf(",", "3", "0", "0", "0"),
                        "w" to listOf("take", "4", "0", "0", "0"),
                        "ms" to Interval.valueOf(42),
                        "es" to (listOf("Jan_Novak") to "person"),
                        "w" to listOf("Jan", "5", "person", "Jan_Novak", "2"),
                        "w" to listOf("Novak", "6", "0", "0", "0"),
                        "ee" to null,
                        "w" to listOf("home", "7", "0", "0", "0"),
                        "w" to listOf("foo", "8", "0", "0", "0"),
                        "me" to null)
                )
    }

    @Test
    fun `entity is within the match`() {
        val iterator = StructuredDocumentIterator(withEntities)
        val document = testDocument(8,
                "Country roads , take Jan Novak home foo",
                "1 2 3 4 5 6 7 8",
                "0 0 0 0 person 0 0 0",
                "0 0 0 0 Jan_Novak 0 0 0",
                "0 0 0 0 2 0 0 0"
        )
        val visitor = TestVisitor()
        iterator.iterateDocument(document,
                matchStarts = mapOf(1 to Interval.valueOf(42)),
                matchEnds = setOf(6),
                visitor = visitor)
        assertThat(visitor.content)
                .isEqualTo(listOf(
                        "w" to listOf("Country", "1", "0", "0", "0"),
                        "ms" to Interval.valueOf(42),
                        "w" to listOf("roads", "2", "0", "0", "0"),
                        "w" to listOf(",", "3", "0", "0", "0"),
                        "w" to listOf("take", "4", "0", "0", "0"),
                        "es" to (listOf("Jan_Novak") to "person"),
                        "w" to listOf("Jan", "5", "person", "Jan_Novak", "2"),
                        "w" to listOf("Novak", "6", "0", "0", "0"),
                        "ee" to null,
                        "w" to listOf("home", "7", "0", "0", "0"),
                        "me" to null,
                        "w" to listOf("foo", "8", "0", "0", "0"))
                )
    }


    @Test
    fun `entity is split by match and entity starts first`() {
        val iterator = StructuredDocumentIterator(withEntities)
        val document = testDocument(8,
                "Country roads , take Jan Novak home foo",
                "1 2 3 4 5 6 7 8",
                "0 0 0 0 person 0 0 0",
                "0 0 0 0 Jan_Novak 0 0 0",
                "0 0 0 0 2 0 0 0"
        )
        val visitor = TestVisitor()
        iterator.iterateDocument(document,
                matchStarts = mapOf(5 to Interval.valueOf(42)),
                matchEnds = setOf(6),
                visitor = visitor)
        assertThat(visitor.content)
                .isEqualTo(listOf(
                        "w" to listOf("Country", "1", "0", "0", "0"),
                        "w" to listOf("roads", "2", "0", "0", "0"),
                        "w" to listOf(",", "3", "0", "0", "0"),
                        "w" to listOf("take", "4", "0", "0", "0"),
                        "es" to (listOf("Jan_Novak") to "person"),
                        "w" to listOf("Jan", "5", "person", "Jan_Novak", "2"),
                        "ee" to null,
                        "ms" to Interval.valueOf(from = 42, to = 42),
                        "es" to (listOf("Jan_Novak") to "person"),
                        "w" to listOf("Novak", "6", "0", "0", "0"),
                        "ee" to null,
                        "w" to listOf("home", "7", "0", "0", "0"),
                        "me" to null,
                        "w" to listOf("foo", "8", "0", "0", "0")))

    }

    @Test
    fun `entity is split because it continues after the matched region`() {
        val iterator = StructuredDocumentIterator(withEntities)
        val document = testDocument(4,
                "Jan Novak home foo",
                "1 2 3 4",
                "person 0 0 0",
                "Jan_Novak 0 0 0",
                "2 0 0 0"
        )
        val visitor = TestVisitor()
        iterator.iterateDocument(document,
                matchStarts = mapOf(0 to Interval.valueOf(42)),
                matchEnds = setOf(0),
                visitor = visitor)
        assertThat(visitor.content)
                .isEqualTo(listOf(
                        "ms" to Interval.valueOf(from = 42, to = 42),
                        "es" to (listOf("Jan_Novak") to "person"),
                        "w" to listOf("Jan", "1", "person", "Jan_Novak", "2"),
                        "ee" to null,
                        "me" to null,
                        "es" to (listOf("Jan_Novak") to "person"),
                        "w" to listOf("Novak", "2", "0", "0", "0"),
                        "ee" to null,
                        "w" to listOf("home", "3", "0", "0", "0"),
                        "w" to listOf("foo", "4", "0", "0", "0")
                ))
    }

}