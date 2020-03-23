package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.dto.interval.substring
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.EqlListener
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.DocumentMatch
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatch
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatchType
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.log.Logger
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory

private val log = SimpleStdoutLoggerFactory.namedLogger("EqlDocumentMatching")

fun EqlAstNode.clearMatchInfo() {
    this.forEachNode { it.matchInfo.clear() }
}

fun Logger.dumpNodesByIndex(nodeByIndex: Array<List<QueryElemNode.SimpleNode>>, metadataConfiguration: MetadataConfiguration) {
    val msg = buildString {
        for (index in metadataConfiguration.indexes.values) {
            if (nodeByIndex[index.columnIndex].isNotEmpty()) {
                appendln("\t${index.name}=${nodeByIndex[index.columnIndex].map { it.content }}")
            }
        }
    }
    this.debug("Nodes by index mapping: \n$msg")
}

@Cleanup("this testing is very fragile, maybe compare the AST structure instead of text (to avoid problems with whitespaces), or even better, change somehow the algorithm, so that these redundancies do not occur")
fun isRedundant(match: DocumentMatch, symbolTable: Map<String, QueryElemNode.AssignNode>, originalQuery: String): Boolean {
    val lastOccurrence = mutableMapOf<String, Interval>()
    for (eqlMatch in match.eqlMatch) {
        var id = originalQuery.substring(eqlMatch.queryInterval)
        val matchType = eqlMatch.type
        if (matchType is EqlMatchType.Identifier) {
            id = originalQuery.substring(symbolTable.getValue(matchType.name).elem.location)
        }
        val prev = lastOccurrence[id]
        if (prev == null || prev == eqlMatch.match || prev.to <= eqlMatch.match.from) {
            lastOccurrence[id] = eqlMatch.match
        } else return true
    }
    return false
}

/**
 * This file contains all the logic which performs query to document matching, inspiration should be taken from
 * http://vigna.di.unimi.it/ftp/papers/EfficientAlgorithmsMinimalIntervalSemantics.pdf
 */

fun matchDocument(ast: EqlAstNode, document: IndexedDocument, defaultIndex: String, resultOffset: Int, metadataConfiguration: MetadataConfiguration, interval: Interval): MatchInfo {
    ast as RootNode
    if (!ast.documentRestriction.evaluate(document)) return MatchInfo.empty()

    val seq = evaluateQuery(ast, document, defaultIndex, metadataConfiguration, interval)
            .drop(resultOffset)
            .filter { it.interval.size < 50 }
            .filterNot { isRedundant(it, ast.symbolTable ?: emptyMap(), ast.originalQuery) }
            .filter { ast.accept(GlobalConstraintEvaluationVisitor(ast, metadataConfiguration, document, it)) }
    return MatchInfo(seq.take(512).toList())
}

internal fun evaluateQuery(ast: EqlAstNode, document: IndexedDocument, defaultIndex: String, metadataConfiguration: MetadataConfiguration, interval: Interval): Sequence<DocumentMatch> {
    log.debug("Matching document '${document.title}' with query ${ast.toMgj4Query()} in interval $interval")
    val nodesByIndex = groupNodesByIndex(ast, metadataConfiguration, defaultIndex)
    log.dumpNodesByIndex(nodesByIndex, metadataConfiguration)

    ast.clearMatchInfo()

    val words = document.drop(interval.from)
            .take(interval.size)

    val tokenIndex = metadataConfiguration.indexOf("token")

    val paragraphs = mutableSetOf<Int>()
    val sentences = mutableSetOf<Int>()

    for ((i, word) in words.withIndex()) {
        for ((j, value) in word.withIndex()) {
            for (node in nodesByIndex[j]) {
                node.checkWord(interval.from + i, value)
            }

            if (word[tokenIndex] == IndexedDocument.PARAGRAPH_MARK) paragraphs.add(interval.from + i)
            else if (word[tokenIndex] == IndexedDocument.SENTENCE_MARK) sentences.add(interval.from + i)
        }
    }

    return ast.accept(DocumentMatchingVisitor(document, metadataConfiguration, paragraphs, sentences))
}

@WhatIf("make sure we are case insensitive - should happen before when transforming the AST")
private fun QueryElemNode.SimpleNode.checkWord(index: Int, value: String) {
    val match = if (this.content.endsWith("*")) {
        value.startsWith(this.content.substring(0, this.content.length - 1))
    } else {
        value == this.content
    }
    if (match) matchInfo.add(DocumentMatch(Interval.valueOf(index), listOf(EqlMatch(this.location, Interval.valueOf(index))), emptyList()))
}


fun groupNodesByIndex(ast: EqlAstNode, metadataConfiguration: MetadataConfiguration, defaultIndex: String): Array<List<QueryElemNode.SimpleNode>> = GroupNodesByIndexListener(metadataConfiguration, defaultIndex)
        .let {
            ast.walk(it)
            it.nodesByIndex as Array<List<QueryElemNode.SimpleNode>>
        }

class GroupNodesByIndexListener(val metadataConfiguration: MetadataConfiguration, defaultIndex: String) : EqlListener {

    val nodesByIndex = Array(metadataConfiguration.indexes.size) { mutableListOf<QueryElemNode.SimpleNode>() }

    private val indexStack = mutableListOf(defaultIndex)

    override fun enterQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {
        if (node.isEntityNameNode()) return
        nodesByIndex[metadataConfiguration.indexOf(indexStack.last())].add(node)
    }

    override fun enterQueryElemIndexNode(node: QueryElemNode.IndexNode) {
        indexStack.add(node.index)
    }

    override fun exitQueryElemIndexNode(node: QueryElemNode.IndexNode) {
        indexStack.removeAt(indexStack.size - 1)
    }

    override fun enterQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {
        nodesByIndex[metadataConfiguration.indexOf(metadataConfiguration.entityIndexName)].add(node.entityNode)
        indexStack.add(node.correspondingIndex)
    }

    override fun exitQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {
        indexStack.removeAt(indexStack.size - 1)
    }

    private fun QueryElemNode.SimpleNode.isEntityNameNode(): Boolean {
        val parent = this.parent
        return parent is QueryElemNode.AttributeNode && this == parent.entityNode
    }
}

//    log.debug("Matching document $document using query $ast")
//    ast as RootNode
//    val nodesByIndex = getNodesByIndex(ast, defaultIndex)
//    val leafMatch = mutableMapOf<QueryElemNode.SimpleNode, MutableList<Int>>()
//            .withDefault { mutableListOf() }
//    val indexNameByIndex = metadataConfiguration.indexes.keys.mapIndexed { i, name -> i to name }.toMap()
//
//    val sentenceMarks = mutableSetOf<Int>()
//    val paragraphMarks = mutableSetOf<Int>()
//    val tokenIndex = metadataConfiguration.indexes.getValue("token")
//
//    for ((i, word) in document.withIndex()) {
//        if (i < interval.from) continue
//        if (i > interval.to) break
//
//
//        val paragraph = "§"
//        val sentence = "¶"
//        if (word[tokenIndex.columnIndex] == paragraph) paragraphMarks.add(i)
//        else if (word[tokenIndex.columnIndex] == sentence) sentenceMarks.add(i)
//
//
//        for ((j, cell) in word.withIndex()) {
//            val index = indexNameByIndex.getValue(j)
//            for (node in nodesByIndex.getValue(index)) {
//                if (elementaryCompare(node.content, cell)) {
//                    val match = leafMatch.getValue(node)
//                    match.add(i)
//                    leafMatch[node] = match
//                }
//            }
//        }
//    }
//
//    @Speed("this is expensive, but good for debug purposes")
//    ast.forEachNode {
//        require(it.matchInfo == null) { "match info is already set in node $it" }
//    }
//
//    val matchVisitor = DocumentMatchingVisitor(leafMatch, sentenceMarks, paragraphMarks)
//    val info = ast.accept(matchVisitor).second
//    if (info.isEmpty()) {
//        log.debug("no intervals discovered, returning empty match")
//        return MatchInfo.empty()
//    }
//    check(info.all { it.first.size == 1 }) {
//        "top level match should have only one child index in $info for query $ast"
//    }
//    log.debug("found ${info.size} root matches")
//
//    val filtered = filterIntervals(info)
//    if (filtered.isEmpty()) {
//        log.debug("no intervals survived the filtering, returning empty match")
//        return MatchInfo.empty()
//    }
//    val textAt: (String, Interval) -> List<String> = { indexName, (from, to) ->
//        val index = metadataConfiguration.indexes[indexName]?.columnIndex
//                ?: fail("could not load text for index $indexName")
//        document.content[index].subList(from, to + 1)
//    }
//    val evaluated = filtered.filter { evaluateGlobalConstraint(ast, it, textAt, metadataConfiguration) }
//    return MatchInfo(evaluated.map { it.second to createMatchInfo(ast.query, it.first) })
//}


//fun filterIntervals(info: List<Pair<List<Int>, Interval>>): List<Pair<Int, Interval>> {
//    log.debug("started working on interval list of size ${info.size}")
//    val filtered = info.asSequence()
//            .filter { it.first.isNotEmpty() }
//            .filter { it.second.size < 250 }
//            .sortedBy { it.second.size }
//            .toList()
//    log.debug("finished filtering and sorting, started filtering out overlaps")
//    val nonOverlap = mutableListOf<Pair<Int, Interval>>()
//
//    main@ for (x in filtered) {
//        for (y in nonOverlap) {
//            @WhatIf(""""overlaps are actually OK as long as the leaves for these intervals are different, however there has to be some
//                 entity merging, otherwise multiword entities will be matched multiple times
//            """)
//            if (y.second.computeOverlap(x.second) > 0.5) continue@main
//        }
//        nonOverlap.add(x.first[0] to x.second)
//    }
//    log.debug("resulting interval list has size ${nonOverlap.size}, smaller than original by ${1 - info.size.toFloat() / nonOverlap.size.toFloat()}%")
//    return nonOverlap
//}
//
//
//fun createMatchInfo(ast: QueryNode, i: Int): List<EqlMatch> {
//    log.debug("matching using index $i")
//    @Speed("this is expensive, but good for debug purposes")
//    ast.forEachNode {
//        if (it !is RestrictionTypeNode.ContextNode && it !is RestrictionTypeNode.ProximityNode) // context node does not need a match unless it is as query
//            require(it.matchInfo != null) { "match info is not set in node $it" }
//    }
//
//    val result = mutableListOf<EqlMatch>()
//
//    fun traverse(node: EqlAstNode, i: Int) {
//        val (indexes, match) = node.matchInfo?.get(i) ?: return
//        when (node) {
//            is RootNode -> throw IllegalStateException("root node should never be encountered")
//            is QueryNode -> node.query.forEachIndexed { index, queryElemNode -> traverse(queryElemNode, indexes[index]) }
//            is QueryElemNode.NotNode -> traverse(node.elem, i)
//            is QueryElemNode.AssignNode -> {
//                result.add(EqlMatch(Interval.valueOf(node.location.from, node.location.from + node.identifier.length - 1), match))
//            }
//            is QueryElemNode.RestrictionNode -> {
//                if (indexes[0] >= 0)
//                    traverse(node.left, indexes[0])
//
//                if (indexes[1] >= 0)
//                    traverse(node.right, indexes[1])
//            }
//            is QueryElemNode.SimpleNode -> {
//                result.add(EqlMatch(node.location, match))
//            }
//            is QueryElemNode.IndexNode -> traverse(node.elem, i)
//            is QueryElemNode.ParenNode -> traverse(node.query, i)
//            is QueryElemNode.AttributeNode -> {
//                result.add(EqlMatch(node.location, match))
//            }
//            is QueryElemNode.OrderNode -> {
//                traverse(node.left, indexes[0])
//                traverse(node.right, indexes[1])
//            }
//            is QueryElemNode.SequenceNode -> {
//                node.elems.forEachIndexed { index, queryElemNode -> traverse(queryElemNode, indexes[index]) }
//            }
//            is QueryElemNode.AlignNode -> {
//                traverse(node.left, indexes[0])
//                traverse(node.right, indexes[1])
//            }
//            is QueryElemNode.BooleanNode -> {
//                if (indexes[0] >= 0) traverse(node.left, indexes[0])
//                if (indexes[1] >= 0) traverse(node.right, indexes[1])
//            }
//            else -> throw IllegalStateException("unknown node type $node")
//        }
//    }
//
//    traverse(ast, i)
//    return result
//}
//
//
//fun QueryNode.extractTwoQueryElems(): Pair<QueryElemNode, QueryElemNode> {
//    require(this.query.size in 1..2) { "size should be within 1..2 ${this.query}" }
//    if (this.query.size == 2) return this.query[0] to this.query[1]
//    val node = this.query[0]
//    return if (node is QueryElemNode.ParenNode) {
//        node.query.extractTwoQueryElems()
//    } else throw IllegalStateException("invalid node strcuture for extracting two query elems $this")
//}
//
//typealias DocumentMatchResult = Pair<Boolean, MutableList<Pair<List<Int>, Interval>>>
//
///**
// * Nodes that do NOT generate their own matches:
// *  not, assign, index, paren
// */
//@Incomplete("add semantic checks limiting where NOT is usable")
//@Speed("this could and should be eventually rewritten using more effective algorithms")
//class DocumentMatchingVisitor(val leafMatch: Map<QueryElemNode.SimpleNode, List<Int>>, val sentenceMarks: Set<Int>, val paragraphMarks: Set<Int>) : GlobalConstraintAgnosticVisitor<DocumentMatchResult>() {
//
//
//    private fun addMatchInfoToNode(node: EqlAstNode, result: MutableList<Pair<List<Int>, Interval>>) {
//        require(node.matchInfo == null) { "$node already has it's matchInfo set" }
//        node.matchInfo = result.toMutableList()
//    }
//
//    override fun visitRootNode(node: RootNode): DocumentMatchResult {
//        val res = node.query.accept(this)
//        val match = res.second.mapIndexed { index, pair -> listOf(index) to pair.second }.toMutableList()
//        addMatchInfoToNode(node, match)
//        return false to match
//    }
//
//
//    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode): DocumentMatchResult {
//        val intervals = leafMatch[node]?.map { listOf(-1) to Interval.valueOf(it) }?.toMutableList()
//                ?: mutableListOf()
//        addMatchInfoToNode(node, intervals)
//        return false to intervals
//    }
//
//
//    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode): DocumentMatchResult {
//        val intervals = node.elem.accept(this)
//        addMatchInfoToNode(node, intervals.second)
//        require(!intervals.first) { "two consecutive NOTs not allowed" }
//        return true to intervals.second
//    }
//
//    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode): DocumentMatchResult {
//        val res = node.elem.accept(this)
//        require(!res.first) { "not not allowed here" }
//        addMatchInfoToNode(node, res.second)
//        return res
//    }
//
//    private fun handleProximity(left: QueryElemNode, right: QueryElemNode, distance: Int): MutableList<Pair<List<Int>, Interval>> {
//        val leftResult = left.accept(this)
//        val rightResult = right.accept(this)
//        return when {
//            leftResult.first && rightResult.first -> throw IllegalStateException("two not clauses have no meaning, should be caught earlier")
//            leftResult.first && !rightResult.first -> rightResult.second.mapIndexed { i, e -> listOf(-1, i) to e.second }.toMutableList()
//            !leftResult.first && rightResult.first -> leftResult.second.mapIndexed { i, e -> listOf(i, -1) to e.second }.toMutableList()
//            else -> {
//                val res = mutableListOf<Pair<List<Int>, Interval>>()
//                for ((i, x) in leftResult.second.withIndex()) {
//                    for ((j, y) in rightResult.second.withIndex()) {
//                        if (x.second.combineWith(y.second).size <= distance) res.add(listOf(i, j) to Interval.valueOf(x.second.from, y.second.to))
//                    }
//                }
//                res
//            }
//        }
//    }
//
//    private fun handleContext(intervals: List<Pair<List<Int>, Interval>>, restriction: ContextRestrictionType): MutableList<Pair<List<Int>, Interval>> {
//        return when (restriction) {
//            is ContextRestrictionType.Sentence -> intervals.filter { interval -> sentenceMarks.none { it in interval.second } }.toMutableList()
//            is ContextRestrictionType.Paragraph -> intervals.filter { interval -> paragraphMarks.none { it in interval.second } }.toMutableList()
//            is ContextRestrictionType.Query -> {
//                val restrictionMatch = restriction.query.accept(this)
//                if (restrictionMatch.first) {
//                    throw IllegalStateException("Not not allowed here")
//                }
//                intervals.filter { interval -> restrictionMatch.second.none { it.second in interval.second } }.toMutableList()
//            }
//        }
//    }
//
//    override fun visitQueryElemRestrictionNode(node: QueryElemNode.RestrictionNode): DocumentMatchResult {
//        val intervals: MutableList<Pair<List<Int>, Interval>> = when (node.type) {
//            is RestrictionTypeNode.ProximityNode -> handleProximity(node.left, node.right, (node.type as RestrictionTypeNode.ProximityNode).distance.toInt())
//            is RestrictionTypeNode.ContextNode -> handleContext(handleAnd(listOf(node.left, node.right)), (node.type as RestrictionTypeNode.ContextNode).restriction)
//        }
//        addMatchInfoToNode(node, intervals)
//        return false to intervals
//    }
//
//    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode): DocumentMatchResult {
//        val res = node.elem.accept(this)
//        addMatchInfoToNode(node, res.second)
//        return res
//    }
//
//    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode): DocumentMatchResult {
//        val entity = node.entityNode.accept(this)
//        val attribute = node.elem.accept(this)
//        require(!entity.first) { "cannot use not here" }
//        require(!attribute.first) { "cannot use not here" }
//        val res = mutableListOf<Pair<List<Int>, Interval>>()
//        for ((i, x) in entity.second.withIndex()) {
//            for ((j, y) in attribute.second.withIndex()) {
//                if (x.second == y.second) res.add(listOf(i, j) to x.second)
//            }
//        }
//        addMatchInfoToNode(node, res)
//        return false to res
//    }
//
//    override fun visitQueryNode(node: QueryNode): DocumentMatchResult {
//        val andLeaves = when (node.restriction) {
//            is RestrictionTypeNode.ProximityNode -> {
//                val (left, right) = node.extractTwoQueryElems()
//                handleProximity(left, right, (node.restriction as RestrictionTypeNode.ProximityNode).distance.toInt())
//            }
//            is RestrictionTypeNode.ContextNode -> handleContext(handleAnd(node.query), (node.restriction as RestrictionTypeNode.ContextNode).restriction)
//            else -> handleAnd(node.query)
//        }
//        addMatchInfoToNode(node, andLeaves)
//        return false to andLeaves
//    }
//
//    private fun handleAnd(query: List<QueryElemNode>): MutableList<Pair<List<Int>, Interval>> {
//        val andLeaves = query.asSequence()
//                .map { it.accept(this) }
//                .filter { !it.first }
//                .map { it.second }
//                .toMutableList()
//        // update the first matchlist so that the others can just append their indexes to it
//        if (andLeaves.isNotEmpty()) {
//            andLeaves[0] = andLeaves[0].mapIndexed { i, e -> listOf(i) to e.second }.toMutableList()
//        }
//        while (andLeaves.size >= 2) {
//            val first = andLeaves.removeAt(0)
//            val second = andLeaves.removeAt(0)
//            val res = mutableListOf<Pair<List<Int>, Interval>>()
//            for (x in first) {
//                for ((j, y) in second.withIndex()) {
//                    res.add(x.first + j to x.second.combineWith(y.second))
//                }
//            }
//            andLeaves.add(0, res)
//        }
//        require(andLeaves.size == 1) { "there should be exactly one interval list left" }
//        return andLeaves[0]
//    }
//
//    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode): DocumentMatchResult {
//        val res = node.query.accept(this)
//        val andLeaves = when (node.restriction) {
//            is RestrictionTypeNode.ProximityNode -> {
//                val (left, right) = node.query.extractTwoQueryElems()
//                handleProximity(left, right, (node.restriction as RestrictionTypeNode.ProximityNode).distance.toInt())
//            }
//            is RestrictionTypeNode.ContextNode -> handleContext(res.second, (node.restriction as RestrictionTypeNode.ContextNode).restriction)
//            else -> res.second
//        }
//        addMatchInfoToNode(node, andLeaves)
//        return false to andLeaves
//    }
//
//    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode): DocumentMatchResult {
//        if (node.operator == BooleanOperator.AND) {
//            val res = handleAnd(listOf(node.left, node.right))
//            addMatchInfoToNode(node, res)
//            return false to res
//        }
//
//        val leftResult = node.left.accept(this)
//        val rightResult = node.right.accept(this)
//        check(!(leftResult.first && rightResult.first)) { "cannot handle two not's" }
//        val res: MutableList<Pair<List<Int>, Interval>> = when {
//            leftResult.first -> rightResult.second.mapIndexed { i, e -> listOf(-1, i) to e.second }.toMutableList()
//            rightResult.first -> leftResult.second.mapIndexed { i, e -> listOf(i, -1) to e.second }.toMutableList()
//            else -> when (node.operator) {
//                BooleanOperator.AND -> handleAnd(leftResult.second, rightResult.second)
//                BooleanOperator.OR -> handleOr(leftResult.second, rightResult.second)
//            }
//        }
//        addMatchInfoToNode(node, res)
//        return false to res
//    }
//
//    private fun handleOr(left: MutableList<Pair<List<Int>, Interval>>, right: MutableList<Pair<List<Int>, Interval>>): MutableList<Pair<List<Int>, Interval>> = (left.mapIndexed { i, e -> listOf(i, -1) to e.second }.toMutableList() + right.mapIndexed { i, e -> listOf(-1, i) to e.second }.toMutableList()).toMutableList()
//
//    private fun handleAnd(left: MutableList<Pair<List<Int>, Interval>>, right: MutableList<Pair<List<Int>, Interval>>): MutableList<Pair<List<Int>, Interval>> {
//        val res = mutableListOf<Pair<List<Int>, Interval>>()
//        for ((i, x) in left.withIndex()) {
//            for ((j, y) in right.withIndex()) {
//                res.add(listOf(i, j) to x.second.combineWith(y.second))
//            }
//        }
//        return res
//    }
//
//    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode): DocumentMatchResult {
//        val leftResult = node.left.accept(this)
//        val rightResult = node.right.accept(this)
//        check(!(leftResult.first || rightResult.first)) { "not does not make sense here" }
//        val res = mutableListOf<Pair<List<Int>, Interval>>()
//        for ((i, x) in leftResult.second.withIndex()) {
//            for ((j, y) in rightResult.second.withIndex()) {
//                if (x.second.to <= y.second.from) res.add(listOf(i, j) to x.second.combineWith(y.second))
//            }
//        }
//        addMatchInfoToNode(node, res)
//        return false to res
//    }
//
//    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode): DocumentMatchResult {
//        val results = node.elems.map { it.accept(this) }
//        require(results.none { it.first }) { "no not is allowed in sequence" }
//        val allCols = results.map { it.second }.toMutableList()
//        if (allCols.isNotEmpty()) {
//            allCols[0] = allCols[0].mapIndexed { i, e -> listOf(i) to e.second }.toMutableList()
//        }
//        while (allCols.size >= 2) {
//            val first = allCols.removeAt(0)
//            val second = allCols.removeAt(0)
//            val res = mutableListOf<Pair<List<Int>, Interval>>()
//            for (x in first) {
//                for ((j, y) in second.withIndex()) {
//                    if (x.second.to + 1 == y.second.from) res.add(x.first + j to x.second.combineWith(y.second))
//                }
//            }
//            allCols.add(0, res)
//        }
//        check(allCols.size == 1) { "exactly one col expected here" }
//        addMatchInfoToNode(node, allCols[0])
//        return false to allCols[0]
//    }
//
//    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode): DocumentMatchResult {
//        val left = node.accept(this)
//        val right = node.accept(this)
//        require(!(left.first || right.first)) { "not is not allowed here" }
//
//        val res = mutableListOf<Pair<List<Int>, Interval>>()
//        for ((i, x) in left.second.withIndex()) {
//            for ((j, y) in right.second.withIndex()) {
//                if (x.second == y.second) res.add(listOf(i, j) to x.second)
//            }
//        }
//
//        addMatchInfoToNode(node, res)
//        return false to res
//    }
//
//    override fun visitRestrictionProximityNode(node: RestrictionTypeNode.ProximityNode): DocumentMatchResult {
//        throw IllegalStateException("should never be called here")
//    }
//
//    override fun visitRestrictionContextNode(node: RestrictionTypeNode.ContextNode): DocumentMatchResult {
//        throw IllegalStateException("should never be called here")
//    }
//}
//
//
//internal fun elementaryCompare(queryValue: String, cellValue: String): Boolean {
//    val queryLower = queryValue.toLowerCase()
//    val cellLower = cellValue.toLowerCase()
//    return if (queryLower.endsWith("*")) cellLower.startsWith(queryLower.substring(0, queryLower.length - 1))
//    else queryLower == cellLower
//}
//
//internal class LeafNodesWithoutNotAboveListener(val root: EqlAstNode) : EqlListener {
//
//    val nodes = mutableListOf<QueryElemNode.SimpleNode>()
//
//    override fun <T : EqlAstNode> shouldContinue(node: T): Boolean {
//        return root === node || (node !is QueryElemNode.NotNode && node !is QueryElemNode.IndexNode && node !is QueryElemNode.AttributeNode)
//    }
//
//    override fun enterQueryElemSimpleNode(node: QueryElemNode.SimpleNode) {
//        nodes.add(node)
//    }
//}
//
//internal class IndexSeparatingListener(val defaultIndex: String) : EqlListener {
//    val nodesByIndex = mutableMapOf<String, MutableList<QueryElemNode.SimpleNode>>()
//            .withDefault { mutableListOf() }
//
//    override fun <T : EqlAstNode> shouldContinue(node: T): Boolean {
//        return node !is QueryElemNode.NotNode && node !is QueryElemNode.IndexNode && node !is QueryElemNode.AttributeNode
//    }
//
//    override fun enterRootNode(node: RootNode) {
//        val listener = LeafNodesWithoutNotAboveListener(node)
//        node.query.walk(listener)
//        val nodes = nodesByIndex.getValue(defaultIndex)
//        nodes.addAll(listener.nodes)
//        nodesByIndex[defaultIndex] = nodes
//    }
//
//    override fun enterQueryElemAttributeNode(node: QueryElemNode.AttributeNode) {
//        require(node.correspondingIndex.isNotEmpty()) { "corresponding index has to be set at this point, node: $node" }
//        val listener = LeafNodesWithoutNotAboveListener(node)
//        node.elem.walk(listener)
//        val nodes = nodesByIndex.getValue(node.correspondingIndex)
//        nodes.addAll(listener.nodes)
//        nodesByIndex[node.correspondingIndex] = nodes
//
//
//        @Incomplete("nertag should NOT be hardcoded!")
//        val nertagNodes = nodesByIndex.getValue("nertag")
//        nertagNodes.add(node.entityNode)
//        nodesByIndex["nertag"] = nertagNodes
//    }
//
//    override fun enterQueryElemIndexNode(node: QueryElemNode.IndexNode) {
//        val listener = LeafNodesWithoutNotAboveListener(node)
//        node.walk(listener)
//        val nodes = nodesByIndex.getValue(node.index)
//        nodes.addAll(listener.nodes)
//        nodesByIndex[node.index] = nodes
//    }
//}
//
//internal fun getNodesByIndex(ast: EqlAstNode, defaultIndex: String): Map<String, List<QueryElemNode.SimpleNode>> {
//    val listener = IndexSeparatingListener(defaultIndex)
//    ast.walk(listener)
//    return listener.nodesByIndex
//}

