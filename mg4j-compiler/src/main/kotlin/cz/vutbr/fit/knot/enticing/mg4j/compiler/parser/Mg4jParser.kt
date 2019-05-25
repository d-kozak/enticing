package cz.vutbr.fit.knot.enticing.mg4j.compiler.parser

import cz.vutbr.fit.knot.enticing.mg4j.compiler.ast.ErrorNode
import cz.vutbr.fit.knot.enticing.mg4j.compiler.dto.ParsedQuery
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class Mg4jParser {

    fun parse(input: String): ParsedQuery {
        val errorListener = ErrorListener()
        val parseTree = Mg4jEqlParser(CommonTokenStream(Mg4jEqlLexer(CharStreams.fromString(input))))
                .also {
                    it.removeErrorListeners()
                    it.addErrorListener(errorListener)
                }.let {
                    it.query()
                }
        val root = ErrorNode("ast node implemented yet")
        return ParsedQuery(root, errorListener.errors)
    }

}




