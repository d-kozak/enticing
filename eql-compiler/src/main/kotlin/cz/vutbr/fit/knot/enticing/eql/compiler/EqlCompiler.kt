package cz.vutbr.fit.knot.enticing.eql.compiler

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.PureMgj4Node
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.SemanticAnalyzer
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.EqlAstGeneratingVisitor
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.SyntaxError
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.parseToEqlAst
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.parseWithAntlr
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger
import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer
import org.antlr.v4.runtime.Token
import kotlin.math.min

class EqlCompiler(logService: MeasuringLogService) {

    val logger = logService.logger { }

    fun parseOrFail(input: String, metadataConfiguration: MetadataConfiguration): AstNode = logger.measure("Query '$input'") {
        val analyzer = SemanticAnalyzer(metadataConfiguration)
        val (parseTree, errors) = parseWithAntlr(input)
        if (errors.isNotEmpty()) throw EqlCompilerException(errors.toString())
        val ast = parseTree.accept(EqlAstGeneratingVisitor())
        val semanticErrors = analyzer.performAnalysis(ast as EqlAstNode)
        if (semanticErrors.isNotEmpty()) throw EqlCompilerException(semanticErrors.toString())
        ast
    }

    fun parseAndAnalyzeQuery(input: String, metadataConfiguration: MetadataConfiguration): ParsedQuery = logger.measure("Query '$input'") {
        val analyzer = SemanticAnalyzer(metadataConfiguration)
        val (parseTree, errors) = parseWithAntlr(input)
        if (errors.isEmpty()) {
            val ast = parseTree.accept(EqlAstGeneratingVisitor())
            val semanticErrors = analyzer.performAnalysis(ast as EqlAstNode)
            ParsedQuery(ast, semanticErrors)
        } else {
            ParsedQuery(PureMgj4Node(input), errors)
        }
    }

    internal fun parse(input: String): ParsedQuery {
        return parseToEqlAst(input)
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
            _errors.add(SyntaxError(msg, Interval.valueOf(from, to)))
        }

    }

}




