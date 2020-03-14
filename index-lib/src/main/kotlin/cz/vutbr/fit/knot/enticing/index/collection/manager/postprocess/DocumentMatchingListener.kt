package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.EqlListener
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument

val Pair<List<Int>, Interval>.indexes
    get() = this.first

val Pair<List<Int>, Interval>.interval
    get() = this.second


class DocumentMatchingListener(val document: IndexedDocument) : EqlListener {
    override fun exitRootNode(node: RootNode) {
        node.matchInfo = node.query.matchInfo
    }

    override fun exitQueryElemNotNode(node: QueryElemNode.NotNode) {
        for ((indexes, interval) in node.matchInfo) {
            if (interval.from > 0) node.matchInfo.add(indexes to Interval.valueOf(0, interval.from - 1))
            if (interval.to < document.size - 1) node.matchInfo.add(indexes to Interval.valueOf(interval.to + 1, document.size - 1))
        }
    }

    override fun exitQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {
        super.exitQueryElemAttributeNode(node)
    }

    override fun exitQueryElemBooleanNode(node: QueryElemNode.BooleanNode) {
        super.exitQueryElemBooleanNode(node)
    }

    override fun exitQueryElemOrderNode(node: QueryElemNode.OrderNode) {
        for ((i, leftMatch) in node.left.matchInfo.withIndex()) {
            for ((j, rightMatch) in node.right.matchInfo.withIndex()) {
                if (leftMatch.interval.to <= rightMatch.interval.from) {
                    node.matchInfo.add(listOf(i, j) to leftMatch.interval.combineWith(rightMatch.interval))
                }
            }
        }
    }

    override fun exitQueryElemSequenceNode(node: QueryElemNode.SequenceNode) {
        super.exitQueryElemSequenceNode(node)
    }

    override fun exitQueryElemAlignNode(node: QueryElemNode.AlignNode) {
        super.exitQueryElemAlignNode(node)
    }

    override fun exitQueryElemParenNode(node: QueryElemNode.ParenNode) {
        node.matchInfo = node.query.matchInfo
    }

    override fun exitQueryElemAssignNode(node: QueryElemNode.AssignNode) {
        node.matchInfo = node.elem.matchInfo
    }

    override fun exitQueryElemIndexNode(node: QueryElemNode.IndexNode) {
        node.matchInfo = node.elem.matchInfo
    }

}