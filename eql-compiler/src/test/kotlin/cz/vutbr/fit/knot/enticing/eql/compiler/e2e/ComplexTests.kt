package cz.vutbr.fit.knot.enticing.eql.compiler.e2e

import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.config
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ComplexTests {

    private val compiler = EqlCompiler()

    @Test
    @DisplayName("person.name:And* lemma:go")
    fun one() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("person.name:And* lemma:go", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("((nertag:person{{nertag->token}}) ^ (param2:(And*){{param2->token}})) (lemma:(go){{lemma->token}})")
    }

    @Test
    @DisplayName("nertag:person (visited|entered) - _SENT_")
    fun two() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("nertag:person (visited|entered) - _SENT_", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("(((nertag:(person){{nertag->token}}) (visited | entered))  - ¶)")
    }

    @Test
    @DisplayName("nertag:person (visited|entered) - _PAR_")
    fun three() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("nertag:person (visited|entered) - _PAR_", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("(((nertag:(person){{nertag->token}}) (visited | entered))  - §)")
    }

    @Test
    @DisplayName("(nertag:person (visited|entered)) - held")
    fun four() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("(nertag:person (visited|entered)) - held", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("((nertag:(person){{nertag->token}}) (visited | entered))  - held")
    }


    @Test
    @DisplayName("person.name:Pablo_Picasso | person.name:Benjamin_Franklin")
    fun five() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("person.name:Pablo_Picasso | person.name:Benjamin_Franklin", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("(((nertag:person{{nertag->token}}) ^ (param2:(Pablo_Picasso){{param2->token}})) | ((nertag:person{{nertag->token}}) ^ (param2:(Benjamin_Franklin){{param2->token}})))")
    }


    @Test
    @DisplayName("person.name:(pepa | honza)")
    fun six() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("person.name:(pepa | honza)", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("((nertag:person{{nertag->token}}) ^ (param2:((pepa | honza)){{param2->token}}))")
    }

    @Test
    @DisplayName("person.name:pepa | location.name:new_york")
    fun seven() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("person.name:pepa | location.name:new_york", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("(((nertag:person{{nertag->token}}) ^ (param2:(pepa){{param2->token}})) | ((nertag:location{{nertag->token}}) ^ (param2:(new_york){{param2->token}})))")
    }

}