package cz.vutbr.fit.knot.enticing.mg4j.compiler.parser

import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer
import org.antlr.v4.runtime.Token


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