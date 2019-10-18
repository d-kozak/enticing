package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.EqlAstNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.EqlListener
import cz.vutbr.fit.knot.enticing.index.boundary.EqlMatch
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument

/**
 * This file contains all the logic which performs query to document matching, inspiration is taken from
 * http://vigna.di.unimi.it/ftp/papers/EfficientAlgorithmsMinimalIntervalSemantics.pdf
 */

fun matchDocument(ast: EqlAstNode, document: IndexedDocument, defaultIndex: String, corpusConfiguration: CorpusConfiguration, interval: Interval): List<EqlMatch> {
    val nodesByIndex = getNodesByIndex(ast, defaultIndex)
    val leafMatch = mutableMapOf<QueryElemNode.SimpleNode, MutableList<Int>>()
            .withDefault { mutableListOf() }
    val indexNameByIndex = corpusConfiguration.indexes.keys.mapIndexed { i, name -> i to name }.toMap()

    for ((i, word) in document.withIndex()) {
        if (i < interval.from) continue
        if (i > interval.to) break

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

    return leafMatch.map { (node, matchList) -> EqlMatch.IndexMatch(node.location, matchList) }
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

