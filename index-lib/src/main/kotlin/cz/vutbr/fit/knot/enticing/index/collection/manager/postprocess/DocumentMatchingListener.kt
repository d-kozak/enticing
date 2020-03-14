package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.BooleanOperator
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.EqlListener
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.DocumentMatch
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatch
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument


class DocumentMatchingListener(val document: IndexedDocument, val matchLimit: Int = 100) : EqlListener {
    override fun exitRootNode(node: RootNode) {
        node.matchInfo = node.query.matchInfo
    }

    override fun exitQueryElemNotNode(node: QueryElemNode.NotNode) {
        for ((interval, subIntervals) in node.matchInfo) {
            if (interval.from > 0) node.matchInfo.add(DocumentMatch(Interval.valueOf(0, interval.from - 1), subIntervals))
            if (interval.to < document.size - 1) node.matchInfo.add(DocumentMatch(Interval.valueOf(interval.to + 1, document.size - 1), subIntervals))
            if (node.matchInfo.size >= matchLimit) break
        }
    }

    override fun exitQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {
        loop@ for (left in node.entityNode.matchInfo) {
            for (right in node.elem.matchInfo) {
                node.matchInfo.add(DocumentMatch(left.interval.combineWith(right.interval), left.eqlMatch + right.eqlMatch))
                if (node.matchInfo.size >= matchLimit) break@loop
            }
        }
    }

    override fun exitQueryElemBooleanNode(node: QueryElemNode.BooleanNode) {
        when (node.operator) {
            BooleanOperator.AND -> handleAnd(node)
            BooleanOperator.OR -> handleOr(node)
            else -> throw IllegalStateException("Unsupported operator ${node.operator}")
        }
        super.exitQueryElemBooleanNode(node)
    }

    private fun handleAnd(node: QueryElemNode.BooleanNode) {
        fun traverse(i: Int, interval: Interval, eqlMatch: List<EqlMatch>) {
            if (node.matchInfo.size >= matchLimit) return
            if (i >= node.children.size) {
                node.matchInfo.add(DocumentMatch(interval, eqlMatch))
            } else {
                for (childMatch in node.children[i].matchInfo)
                    traverse(i + 1, interval.combineWith(childMatch.interval), eqlMatch + childMatch.eqlMatch)
            }
        }

        for (match in node.children[0].matchInfo)
            traverse(1, match.interval, match.eqlMatch)
    }

    private fun handleOr(node: QueryElemNode.BooleanNode) {
        for (child in node.children) {
            node.matchInfo.addAll(child.matchInfo)
            if (node.matchInfo.size >= matchLimit) break
        }
    }

    override fun exitQueryElemOrderNode(node: QueryElemNode.OrderNode) {
        loop@ for ((leftInterval, leftSubIntervals) in node.left.matchInfo) {
            for ((rightInterval, rightSubIntervals) in node.right.matchInfo) {
                if (leftInterval.to <= rightInterval.from) {
                    node.matchInfo.add(DocumentMatch(leftInterval.combineWith(rightInterval), leftSubIntervals + rightSubIntervals))
                    if (node.matchInfo.size >= matchLimit) break@loop
                }
            }
        }
    }

    override fun exitQueryElemSequenceNode(node: QueryElemNode.SequenceNode) {
        fun traverse(i: Int, interval: Interval, eqlMatch: List<EqlMatch>) {
            if (node.matchInfo.size >= matchLimit) return
            if (i >= node.elems.size) {
                node.matchInfo.add(DocumentMatch(interval, eqlMatch))
            } else {
                for (childMatch in node.elems[i].matchInfo) {
                    if (interval.to + 1 == childMatch.interval.from)
                        traverse(i + 1, interval.combineWith(childMatch.interval), eqlMatch + childMatch.eqlMatch)
                }
            }
        }
        for (match in node.elems[0].matchInfo)
            traverse(1, match.interval, match.eqlMatch)
    }

    override fun exitQueryElemAlignNode(node: QueryElemNode.AlignNode) {
        loop@ for (left in node.left.matchInfo) {
            for (right in node.right.matchInfo) {
                if (left.interval == right.interval) {
                    node.matchInfo.add(DocumentMatch(left.interval, left.eqlMatch + right.eqlMatch))
                    if (node.matchInfo.size >= matchLimit) break@loop
                }
            }
        }
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