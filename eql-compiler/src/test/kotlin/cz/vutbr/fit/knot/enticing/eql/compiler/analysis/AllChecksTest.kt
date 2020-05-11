package cz.vutbr.fit.knot.enticing.eql.compiler.analysis

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.metadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.dto.toCorpusFormat
import cz.vutbr.fit.knot.enticing.dto.toMetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.AstDefinitionsGeneratingVisitor
import cz.vutbr.fit.knot.enticing.eql.compiler.forEachQuery
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.SemanticError
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal val config = metadataConfiguration {
    indexes {
        "position" whichIs "Position of the word in the document"
        "token" whichIs "Original word in the document"
        "tag" whichIs "tag"
        "lemma" whichIs "Lemma of the word"
        "parpos" whichIs "parpos"
        "function" whichIs "function"
        "parwrod" whichIs "parword"
        "parlemma" whichIs "parlemma"
        "paroffset" whichIs "paroffset"
        "link" whichIs "link"
        "length" whichIs "length"
        "docuri" whichIs "docuri"
        "lower" whichIs "lower"
        "nerid" whichIs "nerid"
        "nertag" whichIs "nertag"
        attributeIndexes(10)
        "nertype" whichIs "nertype"
        "nerlength" whichIs "nerlength"
    }
    entities {
        "person" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "profession", "nationality")

        "artist" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "role", "nationality")

        "location" with attributes("url", "image", "name", "country")

        "artwork" with attributes("url", "image", "name", "form", "datebegun", "datecompleted", "movement", "genre", "author")

        "event" with attributes("url", "image", "name", "startdate", "enddate", "location")

        "museum" with attributes("url", "image", "name", "type", "established", "director", "location")

        "family" with attributes("url", "image", "name", "role", "nationality", "members")

        "group" with attributes("url", "image", "name", "role", "nationality")

        "nationality" with attributes("url", "image", "name", "country")

        "date" with attributes("url", "image", "year", "month", "day")

        "interval" with attributes("url", "image", "fromyear", "frommonth", "fromday", "toyear", "tomonth", "today")

        "form" with attributes("url", "image", "name")

        "medium" with attributes("url", "image", "name")

        "mythology" with attributes("url", "image", "name")

        "movement" with attributes("url", "image", "name")

        "genre" with attributes("url", "image", "name")

        extraAttributes("nertype", "nerlength")
    }
}.also {
    it.validateOrFail()
}.toCorpusFormat()
        .toMetadataConfiguration() // "double transform" it to ensure all important data is still there

fun assertHasError(errors: List<CompilerError>, id: String, count: Int = 1, location: Interval? = null) {
    assertThat(errors).hasSize(count)
    val error = errors[0]
    error as SemanticError
    assertThat(error.analysisId).isEqualTo(id)
    println("Encountered expected error $error")
    if (location != null) assertThat(error.location).isEqualTo(location)
}

class AllChecksTest {

    val compiler = EqlCompiler(SimpleStdoutLoggerFactory)

    @Test
    fun `queries from file`() {
        forEachQuery("semantic_ok.eql") {
            val (_, errors) = compiler.parseAndAnalyzeQuery(it, config)
            if (errors.isNotEmpty()) {
                System.err.println(errors)
                false
            } else true
        }
    }

    @Nested
    inner class BooleanFlatten {

        @Test
        @DisplayName("one two three")
        fun `simple three nodes`() {
            val (tree, errors) = compiler.parseAndAnalyzeQuery("one two three", config)
            assertThat(errors).isEmpty()
            assertThat(tree).isEqualTo(RootNode(
                    QueryElemNode.BooleanNode(
                            mutableListOf(
                                    QueryElemNode.SimpleNode("one", SimpleQueryType.STRING, Interval.valueOf(0, 2)),
                                    QueryElemNode.SimpleNode("two", SimpleQueryType.STRING, Interval.valueOf(4, 6)),
                                    QueryElemNode.SimpleNode("three", SimpleQueryType.STRING, Interval.valueOf(8, 12))),
                            BooleanOperator.AND,
                            null, Interval.valueOf(0, 12)), null,
                    Interval.valueOf(0, 12)))
        }

        @Test
        @DisplayName("nertag:person (visited|entered) context:sentence")
        fun `more complex`() {
            val (tree, errors) = compiler.parseAndAnalyzeQuery("nertag:person (visited|entered) context:sentence", config)
            assertThat(errors).isEmpty()
            assertThat(tree).isEqualTo(RootNode(
                    QueryElemNode.BooleanNode(
                            mutableListOf(
                                    QueryElemNode.IndexNode("nertag", QueryElemNode.SimpleNode("person", SimpleQueryType.STRING, Interval.valueOf(7, 12)), Interval.valueOf(0, 12)),
                                    QueryElemNode.ParenNode(
                                            QueryElemNode.BooleanNode(mutableListOf(
                                                    QueryElemNode.SimpleNode("visited", SimpleQueryType.STRING, Interval.valueOf(15, 21)),
                                                    QueryElemNode.SimpleNode("entered", SimpleQueryType.STRING, Interval.valueOf(23, 29))), BooleanOperator.OR, null, Interval.valueOf(15, 29)), null, Interval.valueOf(14, 30)))
                            , BooleanOperator.AND, null, Interval.valueOf(0, 47)), null, Interval.valueOf(0, 47)))
        }

        @Test
        @DisplayName("not_related letter ink ~ 3")
        fun `with inner proximity restriction`() {
            val (tree, errors) = compiler.parseAndAnalyzeQuery("not_related letter ink ~ 3", config)
            assertThat(errors).isEmpty()
            println((tree as EqlAstNode).accept(AstDefinitionsGeneratingVisitor()))
            assertThat(tree).isEqualTo(
                    RootNode(
                            QueryElemNode.BooleanNode(
                                    mutableListOf(
                                            QueryElemNode.SimpleNode("not_related", SimpleQueryType.STRING, Interval.valueOf(0, 10)),
                                            QueryElemNode.BooleanNode(mutableListOf(
                                                    QueryElemNode.SimpleNode("letter", SimpleQueryType.STRING, Interval.valueOf(12, 17)),
                                                    QueryElemNode.SimpleNode("ink", SimpleQueryType.STRING, Interval.valueOf(19, 21))),
                                                    BooleanOperator.AND,
                                                    ProximityRestrictionNode("3", Interval.valueOf(23, 25)),
                                                    Interval.valueOf(12, 25)
                                            )),
                                    BooleanOperator.AND, null, Interval.valueOf(0, 25)
                            ), null, Interval.valueOf(0, 25)))
        }
    }

    @Test
    fun `invalid proximity`() {
        val (tree, errors) = compiler.parseAndAnalyzeQuery("one two ~ three", config)
        assertHasError(errors, "PROX-1")
        assertThat(errors).hasSize(1)
        val check = errors[0]
        assertThat(check)
                .isInstanceOf(SemanticError::class.java)
        check as SemanticError

        assertThat(check.location)
                .isEqualTo(Interval.valueOf(10, 14))
    }

    @Nested
    inner class InvalidIndex {

        @Test
        fun `simple query`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("lenna:word", config) // should be lemma
            assertHasError(errors, "IND-1", location = Interval.valueOf(0, 4))
        }

        @Test
        fun `with or`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("index:(A|B|C)", config)
            assertHasError(errors, "IND-1", location = Interval.valueOf(0, 4))
        }

        @Test
        fun `index inside or`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("index:A | index:B  | index:C", config)
            assertHasError(errors, "IND-1", 3)
        }

        @Test
        fun `nertag index with invalid entity`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("nertag:pepson", config)
            assertHasError(errors, "IND-1", location = Interval.valueOf(7, 12))
        }

        @Test
        fun `nertag should allow order`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("nertag:(person < artist)", config)
            assertThat(errors).isEmpty()
        }

        @Test
        fun `nertag should not allow sequence`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("""nertag:"person artist"""", config)
            assertHasError(errors, "IND-1")
        }

        @Test
        fun `nertag should only include entities correct example`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("nertag:(person|artist|!(location))", config)
            assertThat(errors).isEmpty()
        }

        @Test
        fun `nertag should only include entities with invalid entity`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("nertag:(person|artist|!(location1))", config)
            assertHasError(errors, "IND-1")
        }

        @Test
        fun `nested index operators are not allowed`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("lemma:(token:word)", config)
            assertHasError(errors, "IND-2")
        }

        @Test
        fun `nested index operators are not allowed more complex example`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("lemma:(ahoj (token:((word))))", config)
            assertHasError(errors, "IND-2")
        }

        @Test
        fun ` triply nested index operators are not allowed`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("lemma:(ahoj (token:((position:1))))", config)
            assertHasError(errors, "IND-2", count = 2)
        }

    }

    @Nested
    inner class InvalidEntity {

        @Test
        fun `simple query`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("personek.name:honza", config) // should be person
            assertHasError(errors, "ENT-1", location = Interval.valueOf(0, 7))
        }

        @Test
        fun `more complex query`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("nertag:person^(person1.name:Picasso) lemma:(visit|explore) Paris ctx:sent", config)
            assertHasError(errors, "ENT-1", location = Interval.valueOf(15, 21))
        }
    }

    @Nested
    inner class InvalidAttribute {

        @Test
        fun `person does not have age`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("nertag:person^(person.name:Picasso) lemma:(visit|explore) person.age:10 Paris ctx:sent", config)
            assertHasError(errors, "ENT-2", location = Interval.valueOf(65, 67))
        }
    }

    @Nested
    inner class AssignErrors {

        @Test
        fun `duplicate ids`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("one:= ahoj two:= cau one:=nazdar", config)
            assertHasError(errors, "ASGN-1", location = Interval.valueOf(21, 23))
        }
    }

    @Nested
    inner class GlobalConstraints {

        @Test
        fun `pepa not found`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("house house house && pepa = 100", config)
            assertHasError(errors, "REF-1", count = 2)
        }

        @Test
        fun `simple reference is not found`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("pepa:=nertag:person lemma:visit honza:=nertag:person && pepa != honza1", config)
            assertHasError(errors, "REF-1", location = Interval.valueOf(64, 69))
        }

        @Test
        fun `nested reference id not found`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("pepa:=nertag:person lemma:visit honza:=nertag:person && pepa1.name != honza.name", config)
            assertHasError(errors, "REF-2", location = Interval.valueOf(56, 60))
        }

        @Test
        fun `nested reference nertag index attribute not found`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("pepa:=nertag:person lemma:visit honza:=nertag:person && pepa.name1 != honza.name", config)
            assertHasError(errors, "REF-2", location = Interval.valueOf(61, 65))
        }

        @Test
        fun `nested reference attribute index attribute not found`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("pepa:=person.name:pepa lemma:visit honza:=nertag:person && pepa.name1 != honza.name", config)
            assertHasError(errors, "REF-2", location = Interval.valueOf(64, 68))
        }

        @Test
        fun `nested reference for union of entities using nertag`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("smth:=nertag:(person|location) lemma:visit honza:=nertag:person && smth.name != honza.name", config)
            assertThat(errors).isEmpty()
        }

        @Test
        fun `nested reference for union of entities using nertag deathplace should not be available`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("smth:=nertag:(person|location) lemma:visit honza:=nertag:person && smth.deathplace != honza.name", config)
            assertHasError(errors, "REF-2", location = Interval.valueOf(72, 81))
        }

        @Test
        fun `comparison between two simple refs is allowed`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("a:=(ahoj < cau < sbohem) kocka pes b:=plot && a != b", config)
            assertThat(errors).isEmpty()
        }

        @Test
        fun `comparison between two nested refs is allowed`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) ctx:par && influencer.url != influencee.url", config)
            assertThat(errors).isEmpty()
        }

        @Test
        fun `comparison between simple and nested ref is not allowed`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("influencer:=nertag:(person|artist) < middle:=( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) ctx:par && influencer.url != middle", config)
            assertHasError(errors, "COP-1", location = Interval.valueOf(168, 169))
        }

        @Test
        fun `multiple errors`() {
            val (_, errors) = compiler.parseAndAnalyzeQuery("a:=nertag:(person|location) b:=dog && a.name1 != b", config)
            assertThat(errors).isNotEmpty()
        }
    }

}