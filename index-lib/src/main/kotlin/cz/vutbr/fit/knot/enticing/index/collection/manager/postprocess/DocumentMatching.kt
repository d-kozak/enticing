package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.annotation.Speed
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.EqlListener
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.GlobalConstraintAgnosticVisitor
import cz.vutbr.fit.knot.enticing.index.boundary.EqlMatch
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument

internal fun dumpMatch(ast: EqlAstNode, match: Map<Long, List<Interval>>) {
    ast.forEachNode {
        println("$it => ${match[it.id]}")
    }
}

/**
 * This file contains all the logic which performs query to document matching, inspiration is taken from
 * http://vigna.di.unimi.it/ftp/papers/EfficientAlgorithmsMinimalIntervalSemantics.pdf
 */

fun matchDocument(ast: EqlAstNode, document: IndexedDocument, defaultIndex: String, corpusConfiguration: CorpusConfiguration, interval: Interval): List<EqlMatch> {
    val nodesByIndex = getNodesByIndex(ast, defaultIndex)
    val leafMatch = mutableMapOf<QueryElemNode.SimpleNode, MutableList<Int>>()
            .withDefault { mutableListOf() }
    val indexNameByIndex = corpusConfiguration.indexes.keys.mapIndexed { i, name -> i to name }.toMap()

    val sentenceMarks = mutableSetOf<Int>()
    val paragraphMarks = mutableSetOf<Int>()
    val tokenIndex = corpusConfiguration.indexes.getValue("token")

    for ((i, word) in document.withIndex()) {
        if (i < interval.from) continue
        if (i > interval.to) break


        val paragraph = "§"
        val sentence = "¶"
        if (word[tokenIndex.columnIndex] == paragraph) paragraphMarks.add(i)
        else if (word[tokenIndex.columnIndex] == sentence) sentenceMarks.add(i)


        for ((j, cell) in word.withIndex()) {
            val index = indexNameByIndex.getValue(j)
            for (node in nodesByIndex.getValue(index)) {
                if (elementaryCompare(node.content, cell)) {
                    val match = leafMatch.getValue(node)
                    match.add(i)
                    leafMatch[node] = match
                }
            }
        }
    }

    val matchVisitor = DocumentMatchingVisitor(leafMatch, sentenceMarks, paragraphMarks)
    ast.accept(matchVisitor)

    dumpMatch(ast, matchVisitor.intervalsPerNode)
    val matchList = mutableListOf<EqlMatch>()

    val listener = object : EqlListener {
        override fun <T : EqlAstNode> shouldContinue(node: T): Boolean = node === ast || node !is QueryElemNode.NotNode

        override fun enterQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {
            val intervals = matchVisitor.intervalsPerNode[node.id]
                    ?: throw IllegalArgumentException("Unknown location ${node.location}")
            require(intervals.all { it.size == 1 }) { "only intervals of size 1 should exist at the leaf level" }
            matchList.add(EqlMatch.IndexMatch(node.location, intervals.map { it.from }))
        }

        override fun enterQueryElemAssignNode(node: QueryElemNode.AssignNode) {
            val intervals = matchVisitor.intervalsPerNode[node.id]
                    ?: throw IllegalArgumentException("Unknown location ${node.location}")
            matchList.add(EqlMatch.IdentifierMatch(node.location, intervals))
        }
    }
    ast.walk(listener)

    return matchList
}


@Incomplete("add semantic checks limiting where NOT is usable")
@Speed("this could and should be eventually rewritten using more effective algorithms")
class DocumentMatchingVisitor(val leafMatch: Map<QueryElemNode.SimpleNode, List<Int>>, val sentenceMarks: Set<Int>, val paragraphMarks: Set<Int>) : GlobalConstraintAgnosticVisitor<Pair<Boolean, List<Interval>>>() {

    val matchList = mutableListOf<EqlMatch>()

    val intervalsPerNode = mutableMapOf<Long, List<Interval>>()

    fun addIntervalToLocation(node: EqlAstNode, intervals: List<Interval>) {
        require(node.id !in intervalsPerNode) { " slot of $node is already occupied, content: $intervalsPerNode" }
        intervalsPerNode[node.id] = intervals
    }

    override fun visitRootNode(node: RootNode): Pair<Boolean, List<Interval>> {
        val res = node.query.accept(this)
        addIntervalToLocation(node, res.second)
        return res
    }


    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode): Pair<Boolean, List<Interval>> {
        val intervals = leafMatch[node]?.map { Interval.valueOf(it) }
                ?: emptyList()
        addIntervalToLocation(node, intervals)
        return false to intervals
    }


    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode): Pair<Boolean, List<Interval>> {
        val intervals = node.elem.accept(this)
        addIntervalToLocation(node, intervals.second)
        require(!intervals.first) { "two consecutive NOTs not allowed" }
        return true to emptyList()
    }

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode): Pair<Boolean, List<Interval>> {
        val res = node.elem.accept(this)
        require(!res.first) { "not not allowed here" }
        matchList.add(EqlMatch.IdentifierMatch(node.location, res.second))
        addIntervalToLocation(node, res.second)
        return res
    }


    override fun visitQueryElemRestrictionNode(node: QueryElemNode.RestrictionNode): Pair<Boolean, List<Interval>> {
        val leftResult = node.left.accept(this)
        val rightResult = node.right.accept(this)
        val mix: List<Interval> = when {
            !leftResult.first && rightResult.first -> leftResult.second
            leftResult.first && !rightResult.first -> rightResult.second
            !leftResult.first && !rightResult.first -> {
                val res = mutableListOf<Interval>()
                for (x in leftResult.second) {
                    for (y in leftResult.second) {
                        res.add(x.combineWith(y))
                    }
                }
                res
            }
            else -> throw IllegalStateException("two not clauses have no meaning, should be caught earlier")
        }
        val intervals: List<Interval> = when (node.type) {
            is RestrictionTypeNode.ProximityNode -> {
                val distance = (node.type as RestrictionTypeNode.ProximityNode).distance.toInt()
                if (!leftResult.first && !rightResult.first) {
                    val res = mutableListOf<Interval>()
                    for (x in leftResult.second) {
                        for (y in rightResult.second) {
                            if (y.from - x.to <= distance) res.add(Interval.valueOf(x.from, y.to))
                        }
                    }
                    res
                } else {
                    // cases where both is true and both is false are covered already, two more are left
                    if (!leftResult.first) leftResult.second else rightResult.second
                }
            }

            is RestrictionTypeNode.ContextNode -> when ((node.type as RestrictionTypeNode.ContextNode).restriction) {
                is ContextRestrictionType.Sentence -> mix.filter { interval -> sentenceMarks.none { it in interval } }
                is ContextRestrictionType.Paragraph -> mix.filter { interval -> paragraphMarks.none { it in interval } }
                is ContextRestrictionType.Query -> {
                    val restriction = (((node.type as RestrictionTypeNode.ContextNode)).restriction as ContextRestrictionType.Query).query.accept(this)
                    if (restriction.first) {
                        throw IllegalStateException("Not not allowed here")
                    }
                    mix.filter { interval -> restriction.second.none { it in interval } }
                }
            }
        }
        addIntervalToLocation(node, intervals)
        return false to intervals
    }

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode): Pair<Boolean, List<Interval>> {
        val res = node.elem.accept(this)
        addIntervalToLocation(node, res.second)
        return res
    }

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode): Pair<Boolean, List<Interval>> {
        val entity = node.entityNode.accept(this)
        val attribute = node.elem.accept(this)
        require(!entity.first) { "cannot use not here" }
        require(!attribute.first) { "cannot use not here" }
        val res = mutableListOf<Interval>()
        for (x in entity.second) {
            for (y in attribute.second) {
                if (x == y) res.add(x)
            }
        }
        addIntervalToLocation(node, res)
        return false to res
    }

    @Incomplete("handle restriction")
    override fun visitQueryNode(node: QueryNode): Pair<Boolean, List<Interval>> {
        val andLeaves = node.query.asSequence()
                .map { it.accept(this) }
                .filter { !it.first }
                .map { it.second }
                .toMutableList()
        while (andLeaves.size >= 2) {
            val first = andLeaves.removeAt(0)
            val second = andLeaves.removeAt(0)
            val res = mutableListOf<Interval>()
            for (x in first) {
                for (y in second) {
                    res.add(x.combineWith(y))
                }
            }
            andLeaves.add(0, res)
        }
        require(andLeaves.size == 1) { "there should be exactly one interval list left" }
        addIntervalToLocation(node, andLeaves[0])
        return false to andLeaves[0]
    }

    @Incomplete("handle restriction")
    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode): Pair<Boolean, List<Interval>> {
        val res = node.query.accept(this)
        addIntervalToLocation(node, res.second)
        return false to res.second
    }

    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode): Pair<Boolean, List<Interval>> {
        val leftResult = node.left.accept(this)
        val rightResult = node.right.accept(this)
        check(!(leftResult.first && rightResult.first)) { "cannot handle two not's" }
        val res: List<Interval> = when {
            leftResult.first -> rightResult.second
            rightResult.first -> leftResult.second
            else -> when (node.operator) {
                BooleanOperator.AND -> handleAnd(leftResult.second, rightResult.second)
                BooleanOperator.OR -> handleOr(leftResult.second, rightResult.second)
            }
        }
        addIntervalToLocation(node, res)
        return false to res
    }

    private fun handleOr(left: List<Interval>, right: List<Interval>): List<Interval> = left + right

    private fun handleAnd(left: List<Interval>, right: List<Interval>): List<Interval> {
        val res = mutableListOf<Interval>()
        for (x in left) {
            for (y in right) {
                res.add(x.combineWith(y))
            }
        }
        return res
    }

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode): Pair<Boolean, List<Interval>> {
        val leftResult = node.left.accept(this)
        val rightResult = node.right.accept(this)
        check(!(leftResult.first || rightResult.first)) { "not does not make sense here" }
        val res = mutableListOf<Interval>()
        for (x in leftResult.second) {
            for (y in rightResult.second) {
                if (x.to <= y.from) res.add(x.combineWith(y))
            }
        }
        addIntervalToLocation(node, res)
        return false to res
    }

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode): Pair<Boolean, List<Interval>> {
        val results = node.elems.map { it.accept(this) }
        require(results.none { it.first }) { "no not is allowed in sequence" }
        val allCols = results.map { it.second }.toMutableList()
        while (allCols.size >= 2) {
            val first = allCols.removeAt(0)
            val second = allCols.removeAt(0)
            val res = mutableListOf<Interval>()
            for (x in first) {
                for (y in second) {
                    if (x.to + 1 == y.from) res.add(x.combineWith(y))
                }
            }
            allCols.add(0, res)
        }
        check(allCols.size == 1) { "exactly one col expected here" }
        addIntervalToLocation(node, allCols[0])
        return false to allCols[0]
    }

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode): Pair<Boolean, List<Interval>> {
        val left = node.accept(this)
        val right = node.accept(this)
        require(!(left.first || right.first)) { "not is not allowed here" }

        val res = mutableListOf<Interval>()
        for (x in left.second) {
            for (y in right.second) {
                if (x == y) res.add(x)
            }
        }

        addIntervalToLocation(node, res)
        return false to res
    }

    override fun visitRestrictionProximityNode(node: RestrictionTypeNode.ProximityNode): Pair<Boolean, List<Interval>> {
        throw IllegalStateException("should never be called here")
    }

    override fun visitRestrictionContextNode(node: RestrictionTypeNode.ContextNode): Pair<Boolean, List<Interval>> {
        throw IllegalStateException("should never be called here")
    }
}


internal fun elementaryCompare(queryValue: String, cellValue: String): Boolean {
    val queryLower = queryValue.toLowerCase()
    val cellLower = cellValue.toLowerCase()
    return if (queryLower.endsWith("*")) cellLower.startsWith(queryLower.substring(0, queryLower.length - 1))
    else queryLower == cellLower
}

internal class LeafNodesWithoutNotAboveListener(val root: EqlAstNode) : EqlListener {

    val nodes = mutableListOf<QueryElemNode.SimpleNode>()

    override fun <T : EqlAstNode> shouldContinue(node: T): Boolean {
        return root === node || (node !is QueryElemNode.NotNode && node !is QueryElemNode.IndexNode && node !is QueryElemNode.AttributeNode)
    }

    override fun enterQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {
        nodes.add(node)
    }
}

internal class IndexSeparatingListener(val defaultIndex: String) : EqlListener {
    val nodesByIndex = mutableMapOf<String, MutableList<QueryElemNode.SimpleNode>>()
            .withDefault { mutableListOf() }

    override fun <T : EqlAstNode> shouldContinue(node: T): Boolean {
        return node !is QueryElemNode.NotNode && node !is QueryElemNode.IndexNode && node !is QueryElemNode.AttributeNode
    }

    override fun enterRootNode(node: RootNode) {
        val listener = LeafNodesWithoutNotAboveListener(node)
        node.query.walk(listener)
        val nodes = nodesByIndex.getValue(defaultIndex)
        nodes.addAll(listener.nodes)
        nodesByIndex[defaultIndex] = nodes
    }

    override fun enterQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {
        require(node.correspondingIndex.isNotEmpty()) { "corresponding index has to be set at this point, node: $node" }
        val listener = LeafNodesWithoutNotAboveListener(node)
        node.elem.walk(listener)
        val nodes = nodesByIndex.getValue(node.correspondingIndex)
        nodes.addAll(listener.nodes)
        nodesByIndex[node.correspondingIndex] = nodes


        @Incomplete("nertag should NOT be hardcoded!")
        val nertagNodes = nodesByIndex.getValue("nertag")
        nertagNodes.add(node.entityNode)
        nodesByIndex["nertag"] = nertagNodes
    }

    override fun enterQueryElemIndexNode(node: QueryElemNode.IndexNode) {
        val listener = LeafNodesWithoutNotAboveListener(node)
        node.walk(listener)
        val nodes = nodesByIndex.getValue(node.index)
        nodes.addAll(listener.nodes)
        nodesByIndex[node.index] = nodes
    }
}

internal fun getNodesByIndex(ast: EqlAstNode, defaultIndex: String): Map<String, List<QueryElemNode.SimpleNode>> {
    val listener = IndexSeparatingListener(defaultIndex)
    ast.walk(listener)
    return listener.nodesByIndex
}

