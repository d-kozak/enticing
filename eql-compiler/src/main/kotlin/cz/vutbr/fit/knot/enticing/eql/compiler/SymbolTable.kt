package cz.vutbr.fit.knot.enticing.eql.compiler

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode

data class SymbolTable(val symbols: MutableMap<String, QueryElemNode.AssignNode> = mutableMapOf()) : MutableMap<String, QueryElemNode.AssignNode> by symbols {
    lateinit var rootNode: RootNode

    constructor(root: RootNode) : this() {
        this.rootNode = root
    }
}