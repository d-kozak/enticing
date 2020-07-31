package cz.vutbr.fit.knot.enticing.eql.compiler.dto

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError

/**
 * DTO for transferring the query in AST along with compiler errors, if any
 */
data class ParsedQuery(
        val ast: AstNode,
        val errors: List<CompilerError> = emptyList()
)