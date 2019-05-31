package cz.vutbr.fit.knot.enticing.eql.compiler.parser

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.MockNode
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import org.antlr.v4.runtime.*

class EqlCompiler {

    fun parse(input: String): ParsedQuery {
        val errorListener = ErrorListener()
        val parseTree = EqlParser(CommonTokenStream(EqlLexer(CharStreams.fromString(input))))
                .also {
                    it.removeErrorListeners()
                    it.addErrorListener(errorListener)
                }.let {
                    it.root()
                }
        val root = MockNode("ast node implemented yet")
        return ParsedQuery(root, errorListener.errors)
    }

    class ErrorListener : BaseErrorListener() {

        fun hasErrors() = errors.isNotEmpty()

        val errors: List<SyntaxError>
            get() = _errors

        private val _errors = mutableListOf<SyntaxError>()

        override fun syntaxError(recognizer: Recognizer<*, *>?, offendingSymbol: Any?, line: Int, charPositionInLine: Int, msg: String, e: RecognitionException?) {
            val token = offendingSymbol as Token
            val from = Math.min(token.startIndex, token.stopIndex)
            val to = Math.min(token.startIndex, token.stopIndex)
            _errors.add(SyntaxError(msg, from, to))
        }

    }

}




