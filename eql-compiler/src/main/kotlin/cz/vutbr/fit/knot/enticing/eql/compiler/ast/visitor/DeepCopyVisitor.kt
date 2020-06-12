package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*

class DeepCopyVisitor : EqlVisitor<EqlAstNode> {

    private val newSymbolTable: SymbolTable = SymbolTable()

    override fun visitRootNode(node: RootNode): EqlAstNode = RootNode(
            node.query.accept(this) as QueryElemNode,
            node.constraint?.accept(this) as? ConstraintNode,
            node.location
    ).also {
        it.symbolTable = newSymbolTable
        newSymbolTable.rootNode = it
        it.originalQuery = node.originalQuery
        it.contextRestriction = node.contextRestriction
        it.documentRestriction = node.documentRestriction
    }


    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode): EqlAstNode = QueryElemNode.NotNode(node.elem.accept(this) as QueryElemNode, node.location)

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode): EqlAstNode = QueryElemNode.AssignNode(node.identifier, node.elem.accept(this) as QueryElemNode, node.location)
            .also {
                this.newSymbolTable[it.identifier] = it
            }

    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode): EqlAstNode = QueryElemNode.SimpleNode(node.content, node.type, node.location)

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode): EqlAstNode = QueryElemNode.IndexNode(node.index, node.elem.accept(this) as QueryElemNode, node.location)

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode): EqlAstNode = QueryElemNode.AttributeNode(
            node.entityNode.accept(this) as QueryElemNode.SimpleNode, node.attribute, node.elem.accept(this) as QueryElemNode, node.location).also { it.correspondingIndex = node.correspondingIndex }

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode): EqlAstNode = QueryElemNode.ParenNode(node.query.accept(this) as QueryElemNode, node.restriction?.accept(this) as? ProximityRestrictionNode, node.location)


    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode): EqlAstNode = QueryElemNode.BooleanNode(
            node.children.map { it.accept(this) as QueryElemNode }.toMutableList(), node.operator, node.restriction?.accept(this) as? ProximityRestrictionNode, node.location
    )

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode): EqlAstNode = QueryElemNode.OrderNode(
            node.left.accept(this) as QueryElemNode, node.right.accept(this) as QueryElemNode, node.restriction?.accept(this) as? ProximityRestrictionNode, node.location)

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode): EqlAstNode = QueryElemNode.SequenceNode(
            node.elems.map { it.accept(this) as QueryElemNode.SimpleNode }, node.location
    )

    override fun visitConstraintNode(node: ConstraintNode): EqlAstNode = ConstraintNode(node.expression.accept(this) as ConstraintNode.BooleanExpressionNode, node.location)

    override fun visitConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode): EqlAstNode = ConstraintNode.BooleanExpressionNode.NotNode(node.elem.accept(this) as ConstraintNode.BooleanExpressionNode, node.location)

    override fun visitConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode): EqlAstNode = ConstraintNode.BooleanExpressionNode.ParenNode(node.expression.accept(this) as ConstraintNode.BooleanExpressionNode, node.location)

    override fun visitConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode): EqlAstNode = ConstraintNode.BooleanExpressionNode.OperatorNode(node.left.accept(this) as ConstraintNode.BooleanExpressionNode, node.operator,
            node.right.accept(this) as ConstraintNode.BooleanExpressionNode, node.location)

    override fun visitConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode): EqlAstNode = ConstraintNode.BooleanExpressionNode.ComparisonNode(node.left.accept(this) as ReferenceNode, node.operator,
            node.right.accept(this) as ReferenceNode, node.location)

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode): EqlAstNode = ReferenceNode.SimpleReferenceNode(node.identifier, node.location)

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode): EqlAstNode = ReferenceNode.NestedReferenceNode(node.identifier, node.attribute, node.location).also {
        it.correspondingEntities = node.correspondingEntities
    }

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode): EqlAstNode = QueryElemNode.AlignNode(
            node.left.accept(this) as QueryElemNode, node.right.accept(this) as QueryElemNode, node.location
    )

    override fun visitProximityRestrictionNode(node: ProximityRestrictionNode): EqlAstNode = ProximityRestrictionNode(node.distance, node.location)

}