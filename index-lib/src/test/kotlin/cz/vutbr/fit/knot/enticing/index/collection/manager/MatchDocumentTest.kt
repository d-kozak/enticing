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
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jDocumentFactory
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jSingleFileDocumentCollection
import cz.vutbr.fit.knot.enticing.index.testconfig.dummyMetadataConfiguration
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.measure
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*


class MatchDocumentTest {

    val queryExecutor = TestQueryExecutor(dummyMetadataConfiguration)

    val nertagIndex = dummyMetadataConfiguration.entityIndex!!.columnIndex

    val neridIndex = dummyMetadataConfiguration.indexOf("nerid")

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
                document["nerlength"][i] = "-1"
            }
            document["nerlength"][10] = "11"
            val result = queryExecutor.doMatch("person.name:John", document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals[0].interval).isEqualTo(Interval.valueOf(10, 20))
            assertThat(result.intervals[0].eqlMatch).isEqualTo(listOf(EqlMatch(Interval.valueOf(0, 15), Interval.valueOf(10, 20), EqlMatchType.Entity)))
        }

        @Test
        fun `same as previous but using nertag index`() {
            val document = TestDocument(1000)
            for (i in 10..20) {
                document["nertag"][i] = "person"
                document["nerid"][i] = "foobar"
                document["nerlength"][i] = "-1"
            }
            document["nerlength"][10] = "11"
            val result = queryExecutor.doMatch("nertag:person", document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals[0].interval).isEqualTo(Interval.valueOf(10, 20))
            assertThat(result.intervals[0].eqlMatch).isEqualTo(listOf(EqlMatch(Interval.valueOf(0, 12), Interval.valueOf(10, 20), EqlMatchType.Entity)))
        }

    }

    @Nested
    inner class RealExamples {

        @Test
        @DisplayName("(influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist)) ctx:sent doc.url:'http://en.wikipedia.org/wiki/1947–48_Civil_War_in_Mandatory_Palestine' && influencer.url != influencee.url")
        fun first() {
            val document = loadDocument("palestine_war.mg4j", 0)
            val result = queryExecutor.doMatch("(influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist)) ctx:sent doc.url:'http://en.wikipedia.org/wiki/1947–48_Civil_War_in_Mandatory_Palestine' && influencer.url != influencee.url", document)

            val errors = mutableListOf<String>()

            for ((_, eqlMatch) in result.intervals) {
                for ((_, matchInterval) in eqlMatch) {
                    for (i in matchInterval) {
                        val entity = document.content[nertagIndex][i]
                        if (entity != "0") {
                            if (i - 1 >= 0 && document.content[neridIndex][i] == document.content[neridIndex][i - 1] && i - 1 !in matchInterval) errors.add("Word at index [$i] contains entity $entity that should be extended backward, but was not")
                            if (i + 1 < document.size && document.content[neridIndex][i] == document.content[neridIndex][i + 1] && i + 1 !in matchInterval) errors.add("Word at index [$i] contains entity $entity that should be extended forward, but was not")
                        }
                    }
                }
            }

            if (errors.isNotEmpty()) {
                println(errors.joinToString("\n"))
                throw AssertionError()
            }
        }
    }


    @Nested
    inner class Not {


        @Test
        @DisplayName("job lemma:work")
        fun `not is not used, should match`() {
            val document = TestDocument(50)
            document["lemma"][30] = "work"
            document["token"][10] = "job"

            val result = queryExecutor.doMatch("job lemma:work", document)
            assertThat(result.intervals).isNotEmpty()
            assertThat(result.intervals[0].interval).isEqualTo(Interval.valueOf(10, 30))
        }

        @Test
        @DisplayName("!job lemma:work")
        fun `simple global not, should not match anything`() {
            val document = TestDocument(50)
            document["lemma"][30] = "work"
            document["token"][10] = "job"

            val result = queryExecutor.doMatch("!job lemma:work", document)
            assertThat(result.intervals).isEmpty()
        }

        @Test
        @DisplayName("job !lemma:work")
        fun `not over index operator`() {
            val document = TestDocument(50)
            document["lemma"][30] = "work"
            document["token"][10] = "job"

            val result = queryExecutor.doMatch("job !lemma:work", document)
            assertThat(result.intervals).isEmpty()
        }


        @Test
        @DisplayName("(milk !water) | (rice !pasta)")
        fun `nested not`() {
            val document = TestDocument(50)
            document["token"][10] = "milk"
            document["token"][20] = "water"
            document["token"][30] = "rice"

            val result = queryExecutor.doMatch("(milk !water) | (rice !pasta)", document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals[0].interval).isEqualTo(Interval.valueOf(30))
        }

        @Test
        @DisplayName("!water | milk")
        fun `not in or, when given word is really in the document`() {
            val document = TestDocument(50)
            document["token"][10] = "milk"
            document["token"][20] = "water"

            val result = queryExecutor.doMatch("!water | milk", document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals[0].interval).isEqualTo(Interval.valueOf(10))
        }

        @Test
        @DisplayName("!sand | milk")
        fun `not in or, when given word is not in the document`() {
            val document = TestDocument(50)
            document["token"][10] = "milk"
            document["token"][20] = "water"

            val result = queryExecutor.doMatch("!water | milk", document)
            assertThat(result.intervals).hasSize(1)
            assertThat(result.intervals[0].interval).isEqualTo(Interval.valueOf(10))
        }
    }

    /**
     * Query A & A currently returns the same interval twice. If one in on index X and the other on index Y, it returns both [X,Y] and [Y,X].
     * This leads to duplication, it has to be corrected
     *
     */
    @Nested
    inner class NoDuplicitiesInAnd {


        @Test
        @DisplayName("person.name:John person.name:John")
        fun `without global constraints`() {
            val document = TestDocument(100)

            document["nertag"][3] = "person"
            document["param0"][3] = "google.com"
            document["param2"][3] = "John"

            document["nertag"][7] = "person"
            document["param0"][7] = "yahoo.com"
            document["param2"][7] = "John"

            val result = queryExecutor.doMatch("person.name:John person.name:John", document)
            assertThat(result.intervals).hasSize(3)
        }

        @Test
        @DisplayName("person.name:John person.name:John")
        fun `without global constraints using nertag column`() {
            val document = TestDocument(100)

            document["nertag"][3] = "person"
            document["nertag"][7] = "person"


            val result = queryExecutor.doMatch("nertag:person nertag:person", document)
            assertThat(result.intervals).hasSize(3)
        }


        @Test
        @DisplayName("a:=person.name:John b:=person.name:John && a.url != b.url")
        fun `should return only one result`() {
            val document = TestDocument(100)

            document["nertag"][3] = "person"
            document["param0"][3] = "google.com"
            document["param2"][3] = "John"

            document["nertag"][7] = "person"
            document["param0"][7] = "yahoo.com"
            document["param2"][7] = "John"

            val result = queryExecutor.doMatch("a:=person.name:John b:=person.name:John && a.url != b.url", document)
            assertThat(result.intervals).hasSize(1)
        }

        @Test
        @DisplayName("a:=person.name:John b:=person.name:John && a.url != b.url")
        fun `should return ten results`() {
            val document = TestDocument(1000)

            repeat(10) {
                val offset = it * 100
                document["nertag"][offset + 3] = "person"
                document["param0"][offset + 3] = "google.com"
                document["param2"][offset + 3] = "John"

                document["nertag"][offset + 7] = "person"
                document["param0"][offset + 7] = "yahoo.com"
                document["param2"][offset + 7] = "John"
            }

            val result = queryExecutor.doMatch("a:=person.name:John b:=person.name:John && a.url != b.url", document)
            assertThat(result.intervals).hasSize(10)
        }
    }

    @DisplayName("\"one two three\"")
    @Nested
    inner class SequenceOperator1 {

        @Test
        fun `no match, space in the middle`() {
            val document = TestDocument(100)
            document["token"][25] = "one"
            document["token"][26] = "two"
            document["token"][28] = "three"
            val res = queryExecutor.doMatch("\"one two three\"", document)
            assertThat(res.intervals).isEmpty()
        }

        @Test
        fun `no match, missing second`() {
            val document = TestDocument(100)
            document["token"][53] = "one"
            document["token"][55] = "three"
            val res = queryExecutor.doMatch("\"one two three\"", document)
            assertThat(res.intervals).isEmpty()
        }

        @Test
        fun `one match`() {
            val document = TestDocument(100)
            document["token"][37] = "one"
            document["token"][38] = "two"
            document["token"][39] = "three"
            val res = queryExecutor.doMatch("\"one two three\"", document)
            assertThat(res.intervals).hasSize(1)
            val match = res.intervals[0]
            assertThat(match.interval).isEqualTo(Interval.valueOf(37, 39))
        }
    }
}

class TestQueryExecutor(private val metadataConfiguration: MetadataConfiguration) {

    private val eqlCompiler = EqlCompiler(SimpleStdoutLoggerFactory)

    private val logger = SimpleStdoutLoggerFactory.logger { }

    fun doMatch(query: String, document: IndexedDocument): MatchInfo = logger.measure("matchDocument", "$query='query',documentSize=${document.size}") {
        matchDocument(eqlCompiler.parseOrFail(query, metadataConfiguration) as EqlAstNode, document, metadataConfiguration.defaultIndex, 0, metadataConfiguration, Interval.valueOf(0, document.size))
    }

}


fun loadDocument(mg4jFile: String, index: Long): IndexedDocument {
    val factory = Mg4jDocumentFactory(dummyMetadataConfiguration, SimpleStdoutLoggerFactory)
    val collection = Mg4jSingleFileDocumentCollection(File("../data/mg4j/$mg4jFile"), factory, SimpleStdoutLoggerFactory)
    return collection.document(index)
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