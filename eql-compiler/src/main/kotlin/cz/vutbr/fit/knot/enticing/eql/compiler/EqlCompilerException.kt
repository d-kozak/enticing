package cz.vutbr.fit.knot.enticing.eql.compiler

import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError

/**
 * Exception thrown by the compiler when errors are discovered
 */
class EqlCompilerException(val errors: List<CompilerError>) : RuntimeException(errors.toString())