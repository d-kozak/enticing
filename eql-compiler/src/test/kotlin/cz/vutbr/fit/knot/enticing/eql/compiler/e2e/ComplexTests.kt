package cz.vutbr.fit.knot.enticing.eql.compiler.e2e

import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.config
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ComplexTests {

    private val compiler = EqlCompiler(config)

    @Test
    @DisplayName("person.name:And* lemma:go")
    fun one() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("person.name:And* lemma:go")
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("((nertag:person{{nertag->token}}) ^ (param2:(And*){{param2->token}})) (lemma:(go){{lemma->token}})")
    }

    @Test
    @DisplayName("nertag:person (visited|entered) - _SENT_")
    fun two() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("nertag:person (visited|entered) - _SENT_")
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("(((nertag:(person){{nertag->token}}) (visited | entered))  - ¶)")
    }

    @Test
    @DisplayName("nertag:person (visited|entered) - _PAR_")
    fun three() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("nertag:person (visited|entered) - _PAR_")
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("(((nertag:(person){{nertag->token}}) (visited | entered))  - §)")
    }

    @Test
    @DisplayName("(nertag:person (visited|entered)) - held")
    fun four() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("(nertag:person (visited|entered)) - held")
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("((nertag:(person){{nertag->token}}) (visited | entered))  - held")
    }


}