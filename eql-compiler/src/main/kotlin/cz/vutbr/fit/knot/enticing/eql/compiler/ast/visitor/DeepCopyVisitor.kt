package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*
import cz.vutbr.fit.knot.enticing.eql.compiler.emptySymbolTable

class DeepCopyVisitor : EqlVisitor<EqlAstNode> {

    private val newSymbolTable: SymbolTable = emptySymbolTable()

    override fun visitRootNode(node: RootNode): EqlAstNode = RootNode(
            node.query.accept(this) as QueryNode,
            node.constraint?.accept(this) as? GlobalConstraintNode,
            node.location
    ).also { it.symbolTable = newSymbolTable }


    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode): EqlAstNode = QueryElemNode.NotNode(node.elem.accept(this) as QueryElemNode, node.location)

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode): EqlAstNode = QueryElemNode.AssignNode(node.identifier, node.elem.accept(this) as QueryElemNode, node.location)
            .also {
                this.newSymbolTable[it.identifier] = it
            }

    override fun visitQueryElemRestrictionNode(node: QueryElemNode.RestrictionNode): EqlAstNode =
            QueryElemNode.RestrictionNode(node.left.accept(this) as QueryElemNode, node.right.accept(this) as QueryElemNode, node.type.accept(this) as RestrictionTypeNode, node.location)

    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode): EqlAstNode = QueryElemNode.SimpleNode(node.content, node.type, node.location)

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode): EqlAstNode = QueryElemNode.IndexNode(node.index, node.elem.accept(this) as QueryElemNode, node.location)

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode): EqlAstNode = QueryElemNode.AttributeNode(
            node.entityNode.accept(this) as QueryElemNode.SimpleNode, node.attribute, node.elem.accept(this) as QueryElemNode, node.location).also { it.correspondingIndex = node.correspondingIndex }

    override fun visitQueryNode(node: QueryNode): EqlAstNode = QueryNode(node.query.map { it.accept(this) as QueryElemNode }, node.restriction?.accept(this) as? RestrictionTypeNode, node.location)

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode): EqlAstNode = QueryElemNode.ParenNode(node.query.accept(this) as QueryNode, node.restriction?.accept(this) as? RestrictionTypeNode, node.location)


    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode): EqlAstNode = QueryElemNode.BooleanNode(
            node.left.accept(this) as QueryElemNode, node.operator, node.right.accept(this) as QueryElemNode, node.location
    )

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode): EqlAstNode = QueryElemNode.OrderNode(
            node.left.accept(this) as QueryElemNode, node.right.accept(this) as QueryElemNode, node.location)

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode): EqlAstNode = QueryElemNode.SequenceNode(
            node.elems.map { it.accept(this) as QueryElemNode }, node.location
    )

    override fun visitGlobalContraintNode(node: GlobalConstraintNode): EqlAstNode = GlobalConstraintNode(node.expression.accept(this) as GlobalConstraintNode.BooleanExpressionNode, node.location)

    override fun visitConstraintBooleanExpressionNotNode(node: GlobalConstraintNode.BooleanExpressionNode.NotNode): EqlAstNode = GlobalConstraintNode.BooleanExpressionNode.NotNode(node.elem.accept(this) as GlobalConstraintNode.BooleanExpressionNode, node.location)

    override fun visitConstraintBooleanExpressionParenNode(node: GlobalConstraintNode.BooleanExpressionNode.ParenNode): EqlAstNode = GlobalConstraintNode.BooleanExpressionNode.ParenNode(node.expression.accept(this) as GlobalConstraintNode.BooleanExpressionNode, node.location)

    override fun visitConstraintBooleanExpressionOperatorNode(node: GlobalConstraintNode.BooleanExpressionNode.OperatorNode): EqlAstNode = GlobalConstraintNode.BooleanExpressionNode.OperatorNode(node.left.accept(this) as GlobalConstraintNode.BooleanExpressionNode, node.operator,
            node.right.accept(this) as GlobalConstraintNode.BooleanExpressionNode, node.location)

    override fun visitConstraintBooleanExpressionComparisonNode(node: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode): EqlAstNode = GlobalConstraintNode.BooleanExpressionNode.ComparisonNode(node.left.accept(this) as ReferenceNode, node.operator,
            node.right.accept(this) as ReferenceNode, node.location)

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode): EqlAstNode = ReferenceNode.SimpleReferenceNode(node.identifier, node.location)

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode): EqlAstNode = ReferenceNode.NestedReferenceNode(node.identifier, node.attribute, node.location)

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode): EqlAstNode = QueryElemNode.AlignNode(
            node.left.accept(this) as QueryElemNode, node.right.accept(this) as QueryElemNode, node.location
    )

    override fun visitRestrictionProximityNode(node: RestrictionTypeNode.ProximityNode): EqlAstNode = RestrictionTypeNode.ProximityNode(node.distance, node.location)

    override fun visitRestrictionContextNode(node: RestrictionTypeNode.ContextNode): EqlAstNode = RestrictionTypeNode.ContextNode(
            when (node.restriction) {
                is ContextRestrictionType.Paragraph -> ContextRestrictionType.Paragraph
                is ContextRestrictionType.Sentence -> ContextRestrictionType.Sentence
                is ContextRestrictionType.Query -> ContextRestrictionType.Query(node.restriction.query.accept(this) as QueryElemNode)
            },
            node.location
    )
}