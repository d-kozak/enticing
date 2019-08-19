package cz.vutbr.fit.knot.enticing.dto

interface AstNode {
    /**
     * Make a deep copy of the ast
     */
    fun copy(): AstNode
}