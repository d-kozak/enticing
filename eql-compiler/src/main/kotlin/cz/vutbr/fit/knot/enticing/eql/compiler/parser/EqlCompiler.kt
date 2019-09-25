package cz.vutbr.fit.knot.enticing.eql.compiler.parser

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompilerException
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.DummyRoot
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import org.antlr.v4.runtime.*
import kotlin.math.min

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
        val root = DummyRoot()
        return ParsedQuery(root, errorListener.errors)
    }

    fun parseOrFail(input: String): AstNode {
        val (ast, errors) = parse(input)
        if (errors.isNotEmpty()) throw EqlCompilerException(errors.toString())
        return ast
    }

    class ErrorListener : BaseErrorListener() {

        fun hasErrors() = errors.isNotEmpty()

        val errors: List<SyntaxError>
            get() = _errors

        private val _errors = mutableListOf<SyntaxError>()

        override fun syntaxError(recognizer: Recognizer<*, *>?, offendingSymbol: Any?, line: Int, charPositionInLine: Int, msg: String, e: RecognitionException?) {
            val token = offendingSymbol as Token
            val from = min(token.startIndex, token.stopIndex)
            val to = min(token.startIndex, token.stopIndex)
            _errors.add(SyntaxError(msg, from, to))
        }

    }

}




