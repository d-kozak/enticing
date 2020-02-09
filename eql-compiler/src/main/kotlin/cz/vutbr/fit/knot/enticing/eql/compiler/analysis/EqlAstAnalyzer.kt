package cz.vutbr.fit.knot.enticing.eql.compiler.analysis

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.SemanticError
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.Severity
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

abstract class EqlAstCheck<AstNote : EqlAstNode>(val id: String, internal val clazz: KClass<AstNote>) {
    internal fun doAnalyze(node: EqlAstNode, symbolTable: SymbolTable, reporter: Reporter, metadataConfiguration: MetadataConfiguration) = analyze(clazz.cast(node), symbolTable, metadataConfiguration, reporter)
    abstract fun analyze(node: AstNote, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter)
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