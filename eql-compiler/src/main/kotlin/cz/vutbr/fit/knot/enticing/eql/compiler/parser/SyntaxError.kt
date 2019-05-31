package cz.vutbr.fit.knot.enticing.eql.compiler.parser

sealed class CompilerError
data class SyntaxError(val message: String, val from: Int, val to: Int) : CompilerError()
