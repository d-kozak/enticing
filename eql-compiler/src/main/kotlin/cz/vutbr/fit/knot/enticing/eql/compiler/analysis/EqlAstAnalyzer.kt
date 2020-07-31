package cz.vutbr.fit.knot.enticing.eql.compiler.analysis

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.SemanticError
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.Severity
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

/**
 * Base class for any check/rewrite that should be done on the AST,
 */
abstract class EqlAstCheck<AstNote : EqlAstNode>(
        /**
         * Unique id of this check - to determine which analysis reported given error
         */
        val id: String,
        /**
         * Type of node for which this analysis should be perform
         */
        internal val clazz: KClass<AstNote>
) {
    /**
     * Run the check for given node and context.
     */
    internal fun performCheck(node: EqlAstNode, symbolTable: SymbolTable, reporter: Reporter, metadataConfiguration: MetadataConfiguration) = execute(clazz.cast(node), symbolTable, metadataConfiguration, reporter)

    /**
     * This is where the core analysis happens. performCheck is just a wrapper whose goal is to perform the type cast of the node.
     */
    protected abstract fun execute(node: AstNote, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter)
}

/**
 * Reporting system which gathers problems found by the AST checks.
 */
interface Reporter {
    val reports: List<CompilerError>

    fun info(message: String, location: Interval, analysisId: String)
    fun warning(message: String, location: Interval, analysisId: String)
    fun error(message: String, location: Interval, analysisId: String)
}

/**
 * Simple implementation of the Reporter interface gathering all checks into a single list.
 */
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