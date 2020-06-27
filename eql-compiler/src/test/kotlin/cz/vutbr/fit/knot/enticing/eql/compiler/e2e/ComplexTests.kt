package cz.vutbr.fit.knot.enticing.eql.compiler.e2e

import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.config
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ContextRestriction
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.DocumentRestriction
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.toEqlQuery
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ComplexTests {

    private val compiler = EqlCompiler(SimpleStdoutLoggerFactory)

    @Test
    @DisplayName("person.name:And* lemma:go")
    fun one() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("person.name:And* lemma:go", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("(((nertag:person{{nertag->token}}) ^ (param2:(and*){{param2->token}})) & (lemma:(go){{lemma->token}}))")
    }

    @Test
    @DisplayName("nertag:person (visited|entered) context:sentence")
    fun two() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("nertag:person (visited|entered) context:sentence", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("((((nertag:(person){{nertag->token}}) & (visited | entered)))  - ¶)")
    }

    @Test
    @DisplayName("nertag:person (visited|entered) ctx:sent")
    fun three() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("nertag:person (visited|entered) ctx:sent", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("((((nertag:(person){{nertag->token}}) & (visited | entered)))  - ¶)")
    }

    @Test
    @DisplayName("(nertag:person (visited|entered)) ctx:par")
    fun four() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("(nertag:person (visited|entered)) ctx:par", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("((((nertag:(person){{nertag->token}}) & (visited | entered)))  - §)")
    }


    @Test
    @DisplayName("person.name:Pablo_Picasso | person.name:Benjamin_Franklin")
    fun five() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("person.name:Pablo_Picasso | person.name:Benjamin_Franklin", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("(((nertag:person{{nertag->token}}) ^ (param2:(pablo_picasso){{param2->token}})) | ((nertag:person{{nertag->token}}) ^ (param2:(benjamin_franklin){{param2->token}})))")
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

    @Test
    @WhatIf("this might not be correct :X better check it out")
    @DisplayName("(a:=(horse !bread))|(b:=(house !wall))")
    fun eight() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("(a:=(horse !bread))|(b:=(house !wall))", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("((horse & !(bread)) | (house & !(wall)))")
    }


    @Test
    @DisplayName("nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) ctx:par")
    fun nine() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) ctx:par", config)
        assertThat(errors).isEmpty()
        assertThat(ast.toMgj4Query()).isEqualTo("((((((nertag:((person | artist)){{nertag->token}}) < ((lemma:((influence | impact)){{lemma->token}}) | ((lemma:(paid){{lemma->token}}) < (lemma:(tribute){{lemma->token}})))) < (nertag:((person | artist)){{nertag->token}}))))  - §)")
    }


    @Test
    @DisplayName("(influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist)) ctx:par && influencer.url != influencee.url")
    fun ten() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < nertag:(person|artist) ctx:par", config)
        assertThat(errors).isEmpty()
        ast as RootNode
        assertThat(ast.toMgj4Query()).isEqualTo("((((((nertag:((person | artist)){{nertag->token}}) < ((lemma:((influence | impact)){{lemma->token}}) | ((lemma:(paid){{lemma->token}}) < (lemma:(tribute){{lemma->token}})))) < (nertag:((person | artist)){{nertag->token}}))))  - §)")
        assertThat(ast.contextRestriction).isEqualTo(ContextRestriction.PARAGRAPH)
    }


    @Test
    @DisplayName("person.name:Honz* lemma:(work  < ( from home ))  ctx:sent doc.url:'https://www.google.com'")
    fun eleven() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("person.name:Honz* lemma:(work  < ( from home ))  ctx:sent doc.url:'https://www.google.com'", config)
        assertThat(errors).isEmpty()
        ast as RootNode
        assertThat(ast.toMgj4Query()).isEqualTo("(((((nertag:person{{nertag->token}}) ^ (param2:(honz*){{param2->token}})) & (lemma:((work < (from & home))){{lemma->token}})))  - ¶)")
        assertThat(ast.contextRestriction).isEqualTo(ContextRestriction.SENTENCE)
        assertThat(ast.documentRestriction).isEqualTo(DocumentRestriction.Url("https://www.google.com"))
    }

    @Test
    @DisplayName("person.name:Honz* lemma:(work  < ( from home ))  ctx:sent doc.uuid:'1e07edd3-5042-5605-9d68-5b5eeafe1e40'")
    fun twelve() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("person.name:Honz* lemma:(work  < ( from home ))  ctx:sent doc.uuid:'1e07edd3-5042-5605-9d68-5b5eeafe1e40'", config)
        assertThat(errors).isEmpty()
        ast as RootNode
        assertThat(ast.toMgj4Query()).isEqualTo("(((((nertag:person{{nertag->token}}) ^ (param2:(honz*){{param2->token}})) & (lemma:((work < (from & home))){{lemma->token}})))  - ¶)")
        assertThat(ast.contextRestriction).isEqualTo(ContextRestriction.SENTENCE)
        assertThat(ast.documentRestriction).isEqualTo(DocumentRestriction.Uuid("1e07edd3-5042-5605-9d68-5b5eeafe1e40"))
    }


    @Test
    @DisplayName("(Picasso visited) | (explored Paris) ctx:sent")
    fun thirteen() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("(Picasso visited) | (explored Paris) ctx:sent", config)
        assertThat(errors).isEmpty()
        ast as RootNode
        assertThat(ast.toEqlQuery()).isEqualTo("((picasso visited) | (explored paris)) ctx:sent")
        assertThat(ast.toMgj4Query()).isEqualTo("(((((picasso & visited) | (explored & paris))))  - ¶)")
    }

    @Test
    @DisplayName("picasso visited | explored paris ctx:sent")
    fun fourteen() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("picasso visited | explored paris ctx:sent", config)
        assertThat(errors).isEmpty()
        ast as RootNode
        assertThat(ast.toEqlQuery()).isEqualTo("picasso (visited | explored) paris ctx:sent")
    }

    @Test
    @DisplayName("hello hi ~ 3")
    fun fifteen() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("hello hi ~ 3", config)
        assertThat(errors).isEmpty()
        ast as RootNode
        assertThat(ast.toEqlQuery()).isEqualTo("hello hi ~ 3")
        assertThat(ast.toMgj4Query()).isEqualTo("((hello & hi) ~ 3)")
    }

    @Test
    @DisplayName("hello hi howdy ~ 3")
    fun sixteen() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("hello hi howdy ~ 3", config)
        assertThat(errors).isEmpty()
        ast as RootNode
        assertThat(ast.toEqlQuery()).isEqualTo("(hello hi) howdy ~ 3")
        assertThat(ast.toMgj4Query()).isEqualTo("(((hello & hi) & howdy) ~ 3)")
    }

    @Test
    @DisplayName("lemma:(is a ~ 10)")
    fun seventeen() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("lemma:(is a ~ 10)", config)
        assertThat(errors).isEmpty()
        ast as RootNode
        assertThat(ast.toEqlQuery()).isEqualTo("lemma:(is a ~ 10)")
        assertThat(ast.toMgj4Query()).isEqualTo("(lemma:(((is & a) ~ 10)){{lemma->token}})")
    }

    @Test
    @DisplayName("a b !(c < d)")
    fun eighteen() {
        val (ast, errors) = compiler.parseAndAnalyzeQuery("a b !(c < d)", config)
        assertThat(errors).isEmpty()
        ast as RootNode
        assertThat(ast.toEqlQuery()).isEqualTo("a b !(c < d)")
        assertThat(ast.toMgj4Query()).isEqualTo("(a & b & !((c < d)))")
    }
}