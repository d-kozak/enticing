package cz.vutbr.fit.knot.enticing.eql.compiler.parser

import cz.vutbr.fit.knot.enticing.dto.interval.Interval

enum class Severity {
    INFO,
    WARN,
    ERROR
}

sealed class CompilerError
data class SyntaxError(val message: String, val location: Interval, val type: String = "syntaxError") : CompilerError()
data class SemanticError(val message: String, val location: Interval, val type: String = "semanticError", val severity: Severity, val analysisId: String) : CompilerError()
