package cz.vutbr.fit.knot.enticing.mg4j.compiler.dto

import cz.vutbr.fit.knot.enticing.mg4j.compiler.ast.AstNode
import cz.vutbr.fit.knot.enticing.mg4j.compiler.parser.CompilerError

data class ParsedQuery(
        val ast: AstNode,
        val errors: List<CompilerError> = emptyList()
)