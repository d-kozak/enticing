package cz.vutbr.fit.knot.enticing.mg4j.compiler.ast

interface AstNode

data class ErrorNode(val message: String, val children: List<ErrorNode> = emptyList()) : AstNode