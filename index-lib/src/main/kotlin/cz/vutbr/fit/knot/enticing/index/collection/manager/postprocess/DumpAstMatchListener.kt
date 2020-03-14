package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.EqlListener
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.DocumentMatch
import cz.vutbr.fit.knot.enticing.log.Logger

fun Logger.dumpAstMatch(ast: EqlAstNode) {
    val listener = DumpAstMatchListener()
    ast.walk(listener)
    val msg = listener.buildMsg()
    this.debug("Match: \n$msg")
}


class DumpAstMatchListener : EqlListener {

    private val builder = StringBuilder()

    private var indent = 4

    private val indentDiff = 4

    fun buildMsg(): String = builder.toString()


    private fun dumpMatch(prefix: String, matchInfo: MutableSet<DocumentMatch>) {
        with(builder) {
            repeat(indent) {
                append(" ")
            }
            append(prefix)
            append(" = ")
            appendln(matchInfo.joinToString { it.interval.toString() })
        }
    }

    private fun indent() {
        indent += indentDiff
    }

    private fun dedent() {
        indent -= indentDiff
    }


    override fun enterRootNode(node: RootNode) {
        dumpMatch("root", node.matchInfo)
        indent()
    }

    override fun enterQueryElemNotNode(node: QueryElemNode.NotNode) {
        dumpMatch("not", node.matchInfo)
        indent()
    }

    override fun enterQueryElemAssignNode(node: QueryElemNode.AssignNode) {
        dumpMatch("assign", node.matchInfo)
        indent()
    }

    override fun enterQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {
        dumpMatch(node.content, node.matchInfo)
        indent()
    }

    override fun enterQueryElemIndexNode(node: QueryElemNode.IndexNode) {
        dumpMatch("index:${node.index}", node.matchInfo)
        indent()
    }

    override fun enterQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {
        dumpMatch("entity: ${node.entity} attribute:${node.attribute}", node.matchInfo)
        indent()
    }

    override fun enterQueryElemParenNode(node: QueryElemNode.ParenNode) {
        dumpMatch("paren", node.matchInfo)
        indent()
    }

    override fun enterQueryElemBooleanNode(node: QueryElemNode.BooleanNode) {
        dumpMatch("${node.operator}", node.matchInfo)
        indent()
    }

    override fun enterQueryElemOrderNode(node: QueryElemNode.OrderNode) {
        dumpMatch("order", node.matchInfo)
        indent()
    }

    override fun enterQueryElemSequenceNode(node: QueryElemNode.SequenceNode) {
        dumpMatch("sequence", node.matchInfo)
        indent()
    }

    override fun enterQueryElemAlignNode(node: QueryElemNode.AlignNode) {
        dumpMatch("align", node.matchInfo)
        indent()
    }

    override fun exitRootNode(node: RootNode) {
        dedent()
    }

    override fun exitQueryElemNotNode(node: QueryElemNode.NotNode) {
        dedent()
    }

    override fun exitQueryElemAssignNode(node: QueryElemNode.AssignNode) {
        dedent()
    }

    override fun exitQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {
        dedent()
    }

    override fun exitQueryElemIndexNode(node: QueryElemNode.IndexNode) {
        dedent()
    }

    override fun exitQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {
        dedent()
    }

    override fun exitQueryElemParenNode(node: QueryElemNode.ParenNode) {
        dedent()
    }

    override fun exitQueryElemBooleanNode(node: QueryElemNode.BooleanNode) {
        dedent()
    }

    override fun exitQueryElemOrderNode(node: QueryElemNode.OrderNode) {
        dedent()
    }

    override fun exitQueryElemSequenceNode(node: QueryElemNode.SequenceNode) {
        dedent()
    }

    override fun exitQueryElemAlignNode(node: QueryElemNode.AlignNode) {
        dedent()
    }
}