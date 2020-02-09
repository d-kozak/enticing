package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.QueryAgnosticVisitor

fun evaluateGlobalConstraint(ast: RootNode, it: Pair<Int, Interval>, textAt: (String, Interval) -> List<String>, metadataConfiguration: MetadataConfiguration): Boolean {
    val constraint = ast.constraint ?: return true

    ast.forEachNode {
        if (it is QueryElemNode.AssignNode) {
            it.matchInfoIndex = -1
        }
    }

    fun traverse(node: EqlAstNode, i: Int) {
        val (indexes, match) = node.matchInfo?.get(i) ?: return
        when (node) {
            is QueryNode -> node.query.forEachIndexed { index, queryElemNode -> traverse(queryElemNode, indexes[index]) }
            is QueryElemNode.NotNode -> traverse(node.elem, i)
            is QueryElemNode.AssignNode -> {
                node.matchInfoIndex = i
            }
            is QueryElemNode.RestrictionNode -> {
                if (indexes[0] >= 0)
                    traverse(node.left, indexes[0])

                if (indexes[1] >= 0)
                    traverse(node.right, indexes[1])
            }
            is QueryElemNode.SimpleNode -> { /* nothing to do */
            }
            is QueryElemNode.IndexNode -> traverse(node.elem, i)
            is QueryElemNode.ParenNode -> traverse(node.query, i)
            is QueryElemNode.AttributeNode -> {
                traverse(node.entityNode, indexes[0])
                traverse(node.elem, indexes[1])
            }
            is QueryElemNode.OrderNode -> {
                traverse(node.left, indexes[0])
                traverse(node.right, indexes[1])
            }
            is QueryElemNode.SequenceNode -> {
                node.elems.forEachIndexed { index, queryElemNode -> traverse(queryElemNode, indexes[index]) }
            }
            is QueryElemNode.AlignNode -> {
                traverse(node.left, indexes[0])
                traverse(node.right, indexes[1])
            }
            is QueryElemNode.BooleanNode -> {
                if (indexes[0] >= 0) traverse(node.left, indexes[0])
                if (indexes[1] >= 0) traverse(node.right, indexes[1])
            }
            else -> throw IllegalStateException("unknown node type $node")
        }
    }
    traverse(ast.query, it.first)

    ast.forEachNode {
        if (it is QueryElemNode.AssignNode) {
            check(it.matchInfoIndex != -1)
        }
    }

    val symbolTable = ast.symbolTable ?: fail("internal error, symbol table is not set")
    val res = constraint.accept(GlobalConstraintEvaluatingVisitor(symbolTable, textAt, metadataConfiguration))
    return res
}


fun fail(message: String): Nothing = throw IllegalStateException(message)

@Incomplete("not implemented")
class GlobalConstraintEvaluatingVisitor(
        private val symbolTable: MutableMap<String, QueryElemNode.AssignNode>,
        private val textAt: (String, Interval) -> List<String>,
        private val metadataConfiguration: MetadataConfiguration) : QueryAgnosticVisitor<Boolean>() {

    override fun visitConstraintBooleanExpressionComparisonNode(node: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode): Boolean {
        val left = symbolTable[node.left.identifier]
                ?: fail("could not find corresponding node for identifier '${node.left.identifier}', should be caught earlier")
        val right = symbolTable[node.right.identifier]
                ?: fail("could not find corresponding node for identifier, '${node.right.identifier}',' should be caught earlier")
        return when {
            node.left is ReferenceNode.SimpleReferenceNode && node.right is ReferenceNode.SimpleReferenceNode ->
                compareSimpleReferences(left, right, node.operator)
            node.left is ReferenceNode.NestedReferenceNode && node.right is ReferenceNode.NestedReferenceNode ->
                compareNestedReferences(left, right, node)
            else -> fail("cannot compare simple and nested node, should be caught earlier")
        }
    }

    private fun compareSimpleReferences(left: QueryElemNode.AssignNode, right: QueryElemNode.AssignNode, operator: RelationalOperator): Boolean {
        val leftMatch = left.matchInfo?.get(left.matchInfoIndex)
                ?: fail("internal error, could not find matchInfo  for node $left")
        val rightMatch = right.matchInfo?.get(right.matchInfoIndex)
                ?: fail("internal error, could not find matchInfo  for node $right")
        return when (operator) {
            RelationalOperator.EQ -> leftMatch.second == rightMatch.second
            RelationalOperator.NE -> leftMatch.second != rightMatch.second
            else -> fail("other operators not supported")
        }
    }

    private fun compareNestedReferences(leftAssign: QueryElemNode.AssignNode, rightAssign: QueryElemNode.AssignNode, comparisonNode: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode): Boolean {
        val leftReference = comparisonNode.left as ReferenceNode.NestedReferenceNode
        val rightReference = comparisonNode.right as ReferenceNode.NestedReferenceNode
        val (leftEntity, leftMatch) = determineEntity(leftAssign, leftReference)
        val (rightEntity, rightMatch) = determineEntity(rightAssign, rightReference)
        val leftIndex = leftEntity.attributes[leftReference.attribute]?.index
                ?: fail("internal, could not find attribute ${leftReference.attribute} in entity $leftEntity")
        val rightIndex = rightEntity.attributes[rightReference.attribute]?.index
                ?: fail("internal, could not find attribute ${rightReference.attribute} in entity $rightEntity")
        val leftContent = extractFullyDuplicated(textAt(leftIndex.name, leftMatch))
        val rightContent = extractFullyDuplicated(textAt(rightIndex.name, rightMatch))
        return when (comparisonNode.operator) {
            RelationalOperator.EQ -> leftContent == rightContent
            RelationalOperator.NE -> leftContent != rightContent
            else -> fail("other operators not supported")
        }
    }

    private fun extractFullyDuplicated(input: List<String>): String {
        val asSet = input.toSet()
        check(asSet.size == 1) { "there should be only one type of value in : $input" }
        return asSet.first()
    }


    private fun determineEntity(assignNode: QueryElemNode.AssignNode, referenceNode: ReferenceNode.NestedReferenceNode): Pair<EntityConfiguration, Interval> {
        check(referenceNode.correspondingEntities.isNotEmpty()) { "reference $referenceNode has empty entity list, should be caught earlier" }
        val (_, matchInterval) = assignNode.matchInfo?.get(assignNode.matchInfoIndex)
                ?: fail("internal error, could not find match info for node $assignNode")
        val content = textAt(metadataConfiguration.entityIndexName, matchInterval)
        val entity = extractFullyDuplicated(content)
        val entityData = referenceNode.correspondingEntities.find { it.name == entity }
                ?: fail("could not find entity $entity, it was not one of the options in ${referenceNode.correspondingEntities}")
        return entityData to matchInterval
    }

    override fun visitGlobalContraintNode(node: GlobalConstraintNode): Boolean = node.expression.accept(this)

    override fun visitConstraintBooleanExpressionNotNode(node: GlobalConstraintNode.BooleanExpressionNode.NotNode): Boolean = !node.elem.accept(this)

    override fun visitConstraintBooleanExpressionParenNode(node: GlobalConstraintNode.BooleanExpressionNode.ParenNode): Boolean = node.expression.accept(this)

    override fun visitConstraintBooleanExpressionOperatorNode(node: GlobalConstraintNode.BooleanExpressionNode.OperatorNode): Boolean {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        return when (node.operator) {
            BooleanOperator.AND -> left && right
            BooleanOperator.OR -> left || right
        }
    }


}