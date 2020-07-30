package cz.vutbr.fit.knot.enticing.dto

/**
 * Base interface for any EQL ast node, used in the parts of the system that should not have a dependency to the eql-compiler module
 */
interface AstNode {
    /**
     * Serialize the AST into an mg4j string query
     */
    fun toMgj4Query(): String

    /**
     * Make a deep copy of the tree
     */
    fun deepCopy(): AstNode
}

/**
 * Simple wrapper around a string based EQL query,
 * returned when the real ast could not be created
 */
data class PureMgj4Node(val query: String) : AstNode {
    override fun toMgj4Query(): String = query
    override fun deepCopy(): AstNode = this
}