package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.QueryAgnosticVisitor
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.DocumentMatch
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatch
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.EqlMatchType
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument

class GlobalConstraintEvaluationVisitor(val ast: RootNode, val metadataConfiguration: MetadataConfiguration, val document: IndexedDocument, val match: DocumentMatch) : QueryAgnosticVisitor<Boolean>() {

    internal val identifierMatch: Map<String, EqlMatch> = match.eqlMatch.filter { it.type is EqlMatchType.Identifier }
            .associateBy { match -> (match.type as EqlMatchType.Identifier).name }


    override fun visitRootNode(node: RootNode): Boolean {
        val constraint = node.constraint ?: return true
        return constraint.accept(this)
    }

    override fun visitConstraintNode(node: ConstraintNode) = node.expression.accept(this)

    override fun visitConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode) = node.accept(this).not()

    override fun visitConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode) = node.expression.accept(this)

    override fun visitConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode): Boolean {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        return when (node.operator) {
            BooleanOperator.AND -> left && right
            BooleanOperator.OR -> left || right
        }
    }

    override fun visitConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode): Boolean {
        val left = node.left
        val right = node.right
        return when {
            left is ReferenceNode.SimpleReferenceNode && right is ReferenceNode.SimpleReferenceNode -> simpleReferenceCmp(node.operator, left, right)
            left is ReferenceNode.NestedReferenceNode && right is ReferenceNode.NestedReferenceNode -> nestedReferenceCmp(node.operator, left, right)
            else -> error("Only same types of references can be compared, this should be caught earlier, happened in node $node")
        }
    }

    private fun simpleReferenceCmp(operator: RelationalOperator, left: ReferenceNode.SimpleReferenceNode, right: ReferenceNode.SimpleReferenceNode): Boolean {
        val leftMatch = identifierMatch[left.identifier]
                ?: error("Identifier ${left.identifier} should be found in $identifierMatch")
        val rightMatch = identifierMatch[right.identifier]
                ?: error("Identifier ${right.identifier} should be found in $identifierMatch")
        return when (operator) {
            RelationalOperator.EQ -> leftMatch.match == rightMatch.match
            RelationalOperator.NE -> leftMatch.match != rightMatch.match
            else -> error("Operator $operator not supported")
        }
    }

    private fun nestedReferenceCmp(operator: RelationalOperator, left: ReferenceNode.NestedReferenceNode, right: ReferenceNode.NestedReferenceNode): Boolean {
        val leftWord = computeComparisonWord(left)
        val rightWord = computeComparisonWord(right)

        return when (operator) {
            RelationalOperator.EQ -> leftWord == rightWord
            RelationalOperator.NE -> leftWord != rightWord
            else -> error("Operator $operator not supported")
        }
    }

    private fun computeComparisonWord(node: ReferenceNode.NestedReferenceNode): String {
        val match = identifierMatch[node.identifier]
                ?: error("Identifier ${node.identifier} should be found in $identifierMatch")
        val entity = identifyEntity(node, match)
                ?: throw IllegalStateException("No entity found for node $node")
        val index = entity.attributes[node.attribute]?.index?.columnIndex
                ?: error("Attribute ${node.attribute} not found in $entity, should be caught earlier")
        return document.content[index][match.match.from]
    }

    private fun identifyEntity(node: ReferenceNode.NestedReferenceNode, eqlMatch: EqlMatch): EntityConfiguration? {
        val entityIndex = document.content[metadataConfiguration.entityIndex!!.columnIndex]
        loop@ for (entity in node.correspondingEntities) {
            for (i in eqlMatch.match) {
                val word = entityIndex[i]
                if (word != entity.name) continue@loop
            }
            return entity
        }
        return null
    }

}