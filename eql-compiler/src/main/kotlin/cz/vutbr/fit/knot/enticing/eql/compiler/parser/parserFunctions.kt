package cz.vutbr.fit.knot.enticing.eql.compiler.parser

import cz.vutbr.fit.knot.enticing.dto.PureMgj4Node
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.EqlAstGeneratingVisitor
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import org.antlr.v4.runtime.*
import kotlin.math.min

/**
 * Parse the query to EQL AST, but without semantic analysis
 * @return AST representing the query
 */
@Incomplete("perform semantic analysis over syntactically incorrect ast as well")
internal fun parseToEqlAst(input: String): ParsedQuery {
    val (parseTree, errors) = parseWithAntlr(input)
    val ast = if (errors.isEmpty()) parseTree.accept(EqlAstGeneratingVisitor()) else PureMgj4Node(input)
    return ParsedQuery(ast, errors)
}

/**
 * Parse the query into Anltr ParseTree
 * @return root of the parse tree AND list of errors
 */
internal fun parseWithAntlr(input: String): Pair<EqlParser.RootContext, List<SyntaxError>> {
    val errorListener = ErrorListener()
    val parseTree = EqlParser(CommonTokenStream(EqlLexer(CharStreams.fromString(input))))
            .also {
                it.removeErrorListeners()
                it.addErrorListener(errorListener)
            }.root()

    return parseTree to errorListener.errors
}

/**
 * Helper class to gather errors encountered while parsing the query using Antlr
 */
private class ErrorListener : BaseErrorListener() {

    fun hasErrors() = errors.isNotEmpty()

    val errors: List<SyntaxError>
        get() = _errors

    private val _errors = mutableListOf<SyntaxError>()

    override fun syntaxError(recognizer: Recognizer<*, *>?, offendingSymbol: Any?, line: Int, charPositionInLine: Int, msg: String, e: RecognitionException?) {
        val token = offendingSymbol as Token
        val from = min(token.startIndex, token.stopIndex)
        val to = min(token.startIndex, token.stopIndex)
        _errors.add(SyntaxError(msg, Interval.valueOf(from, to)))
    }

}


