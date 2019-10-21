package cz.vutbr.fit.knot.enticing.dto

interface AstNode {
    fun toMgj4Query(): String
    fun deepCopy(): AstNode
}

data class PureMgj4Node(val query: String) : AstNode {
    override fun toMgj4Query(): String = query
    override fun deepCopy(): AstNode = this
}