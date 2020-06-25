package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
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
            .filter { it.interval.size < 50 }
            .distinct()
            .filterNot { isRedundant(it, ast.symbolTable ?: emptyMap(), ast.originalQuery) }
            .filter { ast.accept(GlobalConstraintEvaluationVisitor(ast, metadataConfiguration, document, it)) }
            .drop(resultOffset)
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

    val paragraphs = mutableListOf<Int>()
    val sentences = mutableListOf<Int>()

    for ((i, word) in words.withIndex()) {
        for ((j, value) in word.withIndex()) {
            for (node in nodesByIndex[j]) {
                node.checkWord(interval.from + i, value.toLowerCase())
            }

            if (word[tokenIndex] == IndexedDocument.PARAGRAPH_MARK) paragraphs.add(interval.from + i)
            else if (word[tokenIndex] == IndexedDocument.SENTENCE_MARK) sentences.add(interval.from + i)
        }
    }

    return ast.accept(DocumentMatchingVisitor(document, metadataConfiguration, paragraphs, sentences))
}

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
        val lastIndex = indexStack.last()
        node.index = metadataConfiguration.indexes.getValue(lastIndex).columnIndex
        nodesByIndex[metadataConfiguration.indexOf(lastIndex)].add(node)
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