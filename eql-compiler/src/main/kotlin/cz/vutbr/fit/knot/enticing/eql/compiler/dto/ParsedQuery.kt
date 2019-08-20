package cz.vutbr.fit.knot.enticing.eql.compiler.dto

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError

data class ParsedQuery(
        val ast: AstNode,
        val errors: List<CompilerError> = emptyList()
)