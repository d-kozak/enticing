package cz.vutbr.fit.knot.enticing.eql.compiler

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.PureMgj4Node
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.SemanticAnalyzer
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.toEqlAst
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.parseToEqlAst
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.parseWithAntlr
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.measure

/**
 * Main class of the compiler, exposes an interface for other modules to use.
 */
class EqlCompiler(loggerFactory: LoggerFactory) {

    val logger = loggerFactory.logger { }

    /**
     * Parse given query
     * @throws EqlCompilerException if any errors are discovered
     * @return AST of the query after analysis
     */
    fun parseOrFail(input: String, metadataConfiguration: MetadataConfiguration): AstNode = logger.measure("parseOrFail", input) {
        val analyzer = SemanticAnalyzer(metadataConfiguration)
        val (parseTree, errors) = parseWithAntlr(input)
        if (errors.isNotEmpty()) throw EqlCompilerException(errors)
        val ast = parseTree.toEqlAst()
        ast.originalQuery = input
        val semanticErrors = analyzer.performAnalysis(ast)
        if (semanticErrors.isNotEmpty()) throw EqlCompilerException(semanticErrors)
        ast
    }

    /**
     * Parse given query
     * @return AST of the query after analysis (might not be fully analyzed if some serious errors were encountered in early passes)
     * and list of encountered errors
     */
    fun parseAndAnalyzeQuery(input: String, metadataConfiguration: MetadataConfiguration): ParsedQuery = logger.measure("parseAndAnalyze", input) {
        val analyzer = SemanticAnalyzer(metadataConfiguration)
        val (parseTree, errors) = parseWithAntlr(input)
        if (errors.isEmpty()) {
            val ast = parseTree.toEqlAst()
            ast.originalQuery = input
            val semanticErrors = analyzer.performAnalysis(ast)
            ParsedQuery(ast, semanticErrors)
        } else {
            ParsedQuery(PureMgj4Node(input), errors)
        }
    }

    /**
     * Parse the query, but without semantic analysis
     */
    internal fun parse(input: String): ParsedQuery {
        return parseToEqlAst(input)
    }

}




