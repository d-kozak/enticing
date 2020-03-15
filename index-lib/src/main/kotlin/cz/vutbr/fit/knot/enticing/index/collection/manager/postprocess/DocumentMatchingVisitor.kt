package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.DocumentMatch
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatch
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import kotlin.math.abs

class DocumentMatchingVisitor(val document: IndexedDocument, val paragraphs: Set<Int>, val sentences: Set<Int>) : EqlVisitor<Sequence<DocumentMatch>> {
    override fun visitRootNode(node: RootNode): Sequence<DocumentMatch> {
        val query = node.query.accept(this)
        val forbiddenMarks = when (node.contextRestriction) {
            ContextRestriction.SENTENCE -> sentences
            ContextRestriction.PARAGRAPH -> paragraphs
            else -> return query
        }
        return sequence {
            loop@ for (match in query) {
                for (i in match.interval)
                    if (i in forbiddenMarks) continue@loop
                yield(match)
            }
        }
    }

    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode): Sequence<DocumentMatch> = node.matchInfo.asSequence()

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode): Sequence<DocumentMatch> = node.elem.accept(this)

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode): Sequence<DocumentMatch> = node.query.accept(this).proximityRestriction(node.restriction)

    private fun Sequence<DocumentMatch>.proximityRestriction(node: ProximityRestrictionNode?): Sequence<DocumentMatch> {
        if (node == null) return this
        return this.filter {
            if (it.children.size != 2) {
                System.err.println("never ever ever ever!!!")
                return@filter false
            }
            abs(it.children[1].interval.to - it.children[0].interval.from) <= node.distance.toInt()
        }
    }

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode): Sequence<DocumentMatch> {
        val elem = node.elem.accept(this)
        return elem.asSequence()
                .map { DocumentMatch(it.interval, listOf(EqlMatch(node.elem.location, it.interval)), listOf(it)) }
    }


    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode): Sequence<DocumentMatch> {
        val elem = node.elem.accept(this)
        return sequence {
            for (match in elem) {
                val (interval, subIntervals) = match
                if (interval.from > 0) yield(DocumentMatch(Interval.valueOf(0, interval.from - 1), subIntervals, listOf(match)))
                if (interval.to < document.size - 1) yield(DocumentMatch(Interval.valueOf(interval.to + 1, document.size - 1), subIntervals, listOf(match)))
            }
        }
    }

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode): Sequence<DocumentMatch> {
        val entityInfo = node.entityNode.accept(this)
        val elem = node.elem.accept(this)
        return sequence {
            loop@ for (left in entityInfo) {
                for (right in elem) {
                    if (left.interval == right.interval)
                        yield(DocumentMatch(left.interval.combineWith(right.interval), left.eqlMatch + right.eqlMatch, listOf(left, right)))
                }
            }
        }
    }

    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode): Sequence<DocumentMatch> {
        val children = node.children.map { it.accept(this) }
        return when (node.operator) {
            BooleanOperator.AND -> handleAnd(children)
            BooleanOperator.OR -> handleOr(children)
            else -> throw IllegalStateException("Unsupported operator ${node.operator}")
        }.proximityRestriction(node.restriction)
    }

    private fun handleAnd(node: List<Sequence<DocumentMatch>>): Sequence<DocumentMatch> {
        val first = node.first()
        return sequence {
            for (match in first) {
                traverseAnd(1, node, match.interval, match.eqlMatch, listOf(match))
            }
        }

    }

    private suspend fun SequenceScope<DocumentMatch>.traverseAnd(i: Int, node: List<Sequence<DocumentMatch>>, interval: Interval, eqlMatch: List<EqlMatch>, children: List<DocumentMatch>) {
        if (i >= node.size) {
            yield(DocumentMatch(interval, eqlMatch, children))
        } else {
            for (childMatch in node[i]) {
                traverseAnd(i + 1, node, interval.combineWith(childMatch.interval), eqlMatch + childMatch.eqlMatch, children + childMatch)
            }
        }
    }


    private fun handleOr(node: List<Sequence<DocumentMatch>>): Sequence<DocumentMatch> = sequence {
        for (child in node) {
            yieldAll(child)
        }
    }

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode): Sequence<DocumentMatch> {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        return sequence {
            loop@ for (leftMatch in left) {
                val (leftInterval, leftSubIntervals) = leftMatch
                for (rightMatch in right) {
                    val (rightInterval, rightSubIntervals) = rightMatch
                    if (leftInterval.to <= rightInterval.from) {
                        yield(DocumentMatch(leftInterval.combineWith(rightInterval), leftSubIntervals + rightSubIntervals, listOf(leftMatch, rightMatch)))
                    }
                }
            }
        }.proximityRestriction(node.restriction)
    }

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode): Sequence<DocumentMatch> {
        val seq = node.elems.map { it.accept(this) }
        return sequence {
            for (match in seq[0]) {
                traverseSeq(1, seq, match.interval, match.eqlMatch, listOf(match))
            }
        }
    }

    private suspend fun SequenceScope<DocumentMatch>.traverseSeq(i: Int, node: List<Sequence<DocumentMatch>>, interval: Interval, eqlMatch: List<EqlMatch>, children: List<DocumentMatch>) {
        if (i >= node.size) {
            yield(DocumentMatch(interval, eqlMatch, children))
        } else {
            for (childMatch in node[i]) {
                if (interval.to + 1 == childMatch.interval.from)
                    traverseSeq(i + 1, node, interval.combineWith(childMatch.interval), eqlMatch + childMatch.eqlMatch, children + childMatch)
            }
        }
    }

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode): Sequence<DocumentMatch> {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        return sequence {
            loop@ for (leftMatch in left) {
                for (rightMatch in right) {
                    if (leftMatch.interval == rightMatch.interval) {
                        yield(DocumentMatch(leftMatch.interval, leftMatch.eqlMatch + rightMatch.eqlMatch, listOf(leftMatch, rightMatch)))
                    }
                }
            }
        }
    }

    override fun visitConstraintNode(node: ConstraintNode): Sequence<DocumentMatch> {
        throw IllegalStateException("should never be called")
    }

    override fun visitConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode): Sequence<DocumentMatch> {
        throw IllegalStateException("should never be called")
    }

    override fun visitConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode): Sequence<DocumentMatch> {
        throw IllegalStateException("should never be called")
    }

    override fun visitConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode): Sequence<DocumentMatch> {
        throw IllegalStateException("should never be called")
    }

    override fun visitConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode): Sequence<DocumentMatch> {
        throw IllegalStateException("should never be called")
    }

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode): Sequence<DocumentMatch> {
        throw IllegalStateException("should never be called")
    }

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode): Sequence<DocumentMatch> {
        throw IllegalStateException("should never be called")
    }

    override fun visitProximityRestrictionNode(node: ProximityRestrictionNode): Sequence<DocumentMatch> {
        throw IllegalStateException("should never be called")
    }
}