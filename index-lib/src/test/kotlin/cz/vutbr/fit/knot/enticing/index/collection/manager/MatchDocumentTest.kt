package cz.vutbr.fit.knot.enticing.index.collection.manager

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.PARAGRAPH_MARK
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.SENTENCE_MARK
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatch
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatchType
import cz.vutbr.fit.knot.enticing.index.boundary.DocumentId
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.matchDocument
import cz.vutbr.fit.knot.enticing.index.testconfig.dummyMetadataConfiguration
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.measure
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*


class MatchDocumentTest {

    val queryExecutor = TestQueryExecutor(dummyMetadataConfiguration)

    @Nested
    inner class Hello {

        @Test
        fun `one match`() {
            val document = TestDocument(42)
            document["token"][10] = "hello"
            val result = queryExecutor.doMatch("hello", document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals).allSatisfy { match ->
                assertThat(10 in match.interval)
            }
        }


        @Test
        fun `three matches`() {
            val document = TestDocument(42)
            document["token"][5] = "hello"
            document["token"][10] = "hello"
            document["token"][30] = "hello"
            val result = queryExecutor.doMatch("hello", document)
            assertThat(result.intervals).hasSize(3)
            assertThat(result.intervals).allSatisfy { match ->
                assertThat(10 in match.interval || 20 in match.interval || 30 in match.interval)
            }
        }

        @Test
        fun `1000 matches`() {
            val document = TestDocument(10_000)
            val range = 7 until 10_000 step 7
            for (i in range) {
                document["token"][i] = "hello"
            }
            val result = queryExecutor.doMatch("hello", document)
            assertThat(result.intervals.size).isGreaterThan(500)
        }

    }


    @DisplayName("lemma:hello")
    @Nested
    inner class LemmaHello {

        @Test
        fun `one match`() {
            val document = TestDocument(42)
            document["lemma"][10] = "hello"
            val result = queryExecutor.doMatch("lemma:hello", document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals).allSatisfy { match ->
                assertThat(10 in match.interval)
            }
        }
    }

    @Nested
    @DisplayName("statue lemma:go position:3")
    inner class One {

        private val query = "statue lemma:go position:3"

        @Test
        fun `one match`() {
            val document = TestDocument(10_000)
            document["lemma"][10] = "go"
            document["token"][20] = "statue"
            document["position"][30] = "3"
            val result = queryExecutor.doMatch(query, document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals).allSatisfy { match ->
                assertThat(10 in match.interval)
                assertThat(20 in match.interval)
                assertThat(30 in match.interval)
            }
        }

        @Test
        fun `all matches`() {
            val size = 100
            val document = TestDocument(size)
            repeat(size) {
                document["lemma"][it] = "go"
                document["token"][it] = "statue"
                document["position"][it] = "3"
            }
            val result = queryExecutor.doMatch(query, document)
            assertThat(result.intervals).isNotEmpty()
            assertThat(result.intervals.size).isGreaterThanOrEqualTo(100)
        }

    }

    @Nested
    @DisplayName("lemma:hello hi ctx:sent")
    inner class SentenceLimit {

        private val query = "lemma:hello hi ctx:sent"

        @Test
        fun `one match`() {
            val document = TestDocument(10_000)
            document["lemma"][10] = "hello"
            document["token"][20] = "hi"
            document["token"][21] = SENTENCE_MARK
            val result = queryExecutor.doMatch(query, document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals).allSatisfy { match ->
                assertThat(10 in match.interval)
                assertThat(20 in match.interval)
            }
        }

        @Test
        fun `no match cause sentence mark betweem`() {
            val document = TestDocument(10_000)
            document["lemma"][10] = "hello"
            document["token"][15] = SENTENCE_MARK
            document["token"][20] = "hi"
            val result = queryExecutor.doMatch(query, document)
            assertThat(result.intervals).isEmpty()
        }

        @Test
        fun `one match one blocked`() {
            val document = TestDocument(10_000)
            document["lemma"][10] = "hello"
            document["token"][15] = SENTENCE_MARK
            document["token"][20] = "hi"
            document["lemma"][23] = "hello"
            document["token"][25] = SENTENCE_MARK
            val result = queryExecutor.doMatch(query, document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals).allSatisfy { match ->
                assertThat(20 in match.interval)
                assertThat(23 in match.interval)
            }
        }
    }


    @Nested
    @DisplayName("lemma:hello hi ctx:par")
    inner class ParagraphLimit {

        private val query = "lemma:hello hi ctx:par"

        @Test
        fun `a lot of matches`() {
            val document = TestDocument(1_000)
            for (i in 0 until 997 step 3) {
                document["lemma"][i] = "hello"
                document["token"][i + 1] = PARAGRAPH_MARK
                document["token"][i + 2] = "hi"
            }
            val result = queryExecutor.doMatch(query, document)
            assertThat(result.intervals).hasSize(332)
            assertThat(result.intervals).allSatisfy {
                assertThat(it.interval.size).isEqualTo(2)
            }
        }

        @Test
        fun `no match all blocked`() {
            val document = TestDocument(1_000)
            for (i in 0 until 996 step 4) {
                document["lemma"][i] = "hello"
                document["token"][i + 1] = PARAGRAPH_MARK
                document["token"][i + 2] = "hi"
                document["token"][i + 3] = PARAGRAPH_MARK

            }
            val result = queryExecutor.doMatch(query, document)
            assertThat(result.intervals).isEmpty()
        }
    }

    @Nested
    @DisplayName("(lemma:hello hi position:10) ctx:sent")
    inner class SentenceLimitThreeParts {

        private val query = "(lemma:hello hi position:10) ctx:sent"

        @Test
        fun `no match`() {
            val document = TestDocument(10_000)
            document["token"][10] = "hi"
            document["position"][11] = "10"
            document["token"][21] = SENTENCE_MARK
            document["lemma"][22] = "hello"
            val result = queryExecutor.doMatch(query, document)
            assertThat(result.intervals).isEmpty()
        }

        @Test
        fun `one match`() {
            val document = TestDocument(10_000)
            document["token"][10] = "hi"
            document["position"][11] = "10"
            document["token"][21] = SENTENCE_MARK
            document["lemma"][22] = "hello"
            document["token"][23] = "hi"
            document["position"][34] = "10"
            val result = queryExecutor.doMatch(query, document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals).allSatisfy { match ->
                assertThat(22 in match.interval)
                assertThat(23 in match.interval)
                assertThat(24 in match.interval)
            }
        }
    }


    @Nested
    @DisplayName("lemma:word position:10 ~ 3")
    inner class ProximityTest {
        private val query = "lemma:word position:10 ~ 3"

        @Test
        fun `no match more far away`() {
            val document = TestDocument(10_000)
            document["lemma"][1455] = "word"
            document["position"][1459] = "10"
            val result = queryExecutor.doMatch(query, document)
            assertThat(result.intervals).isEmpty()
        }

        @Test
        fun `no match really far away`() {
            val document = TestDocument(10_000)
            document["lemma"][1455] = "word"
            document["position"][1469] = "10"
            val result = queryExecutor.doMatch(query, document)
            assertThat(result.intervals).isEmpty()
        }

        @Test
        fun `one match`() {
            val document = TestDocument(10_000)
            document["lemma"][1456] = "word"
            document["position"][1458] = "10"
            val result = queryExecutor.doMatch(query, document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals).allSatisfy { match ->
                assertThat(1456 in match.interval)
                assertThat(1457 in match.interval)
            }
        }

    }

    @Nested
    inner class WithEntities {
        @Test
        fun noMatch() {
            val document = TestDocument(10_000)
            val result = queryExecutor.doMatch("1:=person.name:John 2:=person.name:Rupert 3:=location.name:Barcelona && (1.url != 2.url) | !(3.name = 1.name)", document)
            assertThat(result.intervals).isEmpty()

        }
    }

    @Nested
    inner class EntityExpansion {

        @Test
        fun `entity contains multiple words, but only one result should be returned, covering the whole entity`() {
            val document = TestDocument(1000)
            for (i in 10..20) {
                document["nertag"][i] = "person"
                document["param2"][i] = "John"
                document["nerid"][i] = "foobar"
            }
            val result = queryExecutor.doMatch("person.name:John", document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals[0].interval).isEqualTo(Interval.valueOf(10, 20))
            assertThat(result.intervals[0].eqlMatch).isEqualTo(listOf(EqlMatch(Interval.valueOf(0, 15), Interval.valueOf(10, 20), EqlMatchType.ENTITY)))
        }
    }
}

class TestQueryExecutor(private val metadataConfiguration: MetadataConfiguration) {

    private val eqlCompiler = EqlCompiler(SimpleStdoutLoggerFactory)

    private val logger = SimpleStdoutLoggerFactory.logger { }

    fun doMatch(query: String, document: TestDocument): MatchInfo = logger.measure("matchDocument", "$query='query',documentSize=${document.size}") {
        matchDocument(eqlCompiler.parseOrFail(query, metadataConfiguration) as EqlAstNode, document, metadataConfiguration.defaultIndex, metadataConfiguration, Interval.valueOf(0, document.size))
    }

}


class TestDocument(documentSize: Int, val metadataConfiguration: MetadataConfiguration = dummyMetadataConfiguration) : IndexedDocument {
    override val id: DocumentId = 42
    override val uuid: String = UUID.randomUUID().toString()
    override val title: String = "testDocument"
    override val uri: String = "tess.test"
    override val size: Int
        get() = mutableContent.first().size


    operator fun get(token: String): MutableList<String> {
        val index = metadataConfiguration.indexes[token] ?: throw IllegalArgumentException("unknown index $token")
        return mutableContent[index.columnIndex]
    }

    /**
     * Mutable content is exposed so that the tests can tweak the document as necessary
     */
    val mutableContent = List(metadataConfiguration.indexes.size) { loremIpsum(documentSize) }

    override val content: List<List<String>>
        get() = mutableContent

}


private fun loremIpsum(size: Int): MutableList<String> {
    val div = size / LOREM_IPSUM.size
    val rem = size % LOREM_IPSUM.size
    val res = mutableListOf<String>()
    repeat(div) {
        res.addAll(LOREM_IPSUM)
    }
    res.addAll(LOREM_IPSUM.subList(0, rem))
    return res
}

/**
 * dummy text for the test document
 */
private val LOREM_IPSUM = "Lorem ipsum dolor sit amet , consectetuer adipiscing elit .".split(" ")