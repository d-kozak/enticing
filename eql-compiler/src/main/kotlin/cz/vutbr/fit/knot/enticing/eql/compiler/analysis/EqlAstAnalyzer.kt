package cz.vutbr.fit.knot.enticing.eql.compiler.analysis

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.SemanticError
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.Severity
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

abstract class EqlAstCheck<AstNote : EqlAstNode>(val id: String, internal val clazz: KClass<AstNote>) {
    internal fun doAnalyze(node: EqlAstNode, symbolTable: SymbolTable, reporter: Reporter, corpusConfiguration: CorpusConfiguration) = analyze(clazz.cast(node), symbolTable, corpusConfiguration, reporter)
    abstract fun analyze(node: AstNote, symbolTable: SymbolTable, corpusConfiguration: CorpusConfiguration, reporter: Reporter)
}

interface Reporter {
    val reports: List<CompilerError>

    fun info(message: String, location: Interval, analysisId: String)
    fun warning(message: String, location: Interval, analysisId: String)
    fun error(message: String, location: Interval, analysisId: String)
}

class SimpleAgregatingReporter : Reporter {

    override val reports = mutableListOf<CompilerError>()

    override fun info(message: String, location: Interval, analysisId: String) {
        reports.add(SemanticError(message, location, Severity.INFO, analysisId))
    }

    override fun warning(message: String, location: Interval, analysisId: String) {
        reports.add(SemanticError(message, location, Severity.WARN, analysisId))
    }

    override fun error(message: String, location: Interval, analysisId: String) {
        reports.add(SemanticError(message, location, Severity.ERROR, analysisId))
    }
}