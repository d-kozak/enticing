package cz.vutbr.fit.knot.enticing.eql.compiler.parser

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompilerException
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer
import org.antlr.v4.runtime.Token
import kotlin.math.min

class EqlCompiler {

    fun parse(input: String): ParsedQuery {
        return parseToEqlAst(input)
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




