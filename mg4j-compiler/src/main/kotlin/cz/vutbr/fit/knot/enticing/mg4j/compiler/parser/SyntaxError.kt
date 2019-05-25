package cz.vutbr.fit.knot.enticing.mg4j.compiler.parser

sealed class CompilerError
data class SyntaxError(val msg: String, val from: Int, val to: Int) : CompilerError()
