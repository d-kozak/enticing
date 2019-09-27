package cz.vutbr.fit.knot.enticing.eql.compiler.ast

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.interval.Interval

abstract class EqlAstNode : AstNode {
    abstract val location: Interval
    abstract fun <T> accept(visitor: EqlVisitor<T>): T
}

interface EqlVisitor<T> {
    fun visitRootNode(rootNode: RootNode): T
    fun visitQueryElemNotNode(notNode: QueryElemNode.NotNode): T
    fun visitQueryElemAssignNode(assignNode: QueryElemNode.AssignNode): T
    fun visitQueryElemRestrinctionNode(restrictionNode: QueryElemNode.RestrictionNode): T
    fun visitQueryElemSimpleNode(simpleNode: QueryElemNode.SimpleNode): T
    fun visitQueryElemIndexNode(indexNode: QueryElemNode.IndexNode): T
    fun visitQueryElemAttributeNode(attributeNode: QueryElemNode.AttributeNode): T
    fun visitQueryNode(queryNode: QueryNode): T
    fun visitQueryElemParenNode(parenNode: QueryElemNode.ParenNode): T
    fun visitQueryElemBooleanNode(booleanNode: QueryElemNode.BooleanNode): T
    fun visitQueryElemOrderNode(orderNode: QueryElemNode.OrderNode): T
    fun visitQueryElemSequenceNode(sequenceNode: QueryElemNode.SequenceNode): T
    fun visitGlobalContraintNode(globalConstraintNode: GlobalConstraintNode): T
    fun visitConstraintBooleanExpressionNotNode(notNode: GlobalConstraintNode.BooleanExpressionNode.NotNode): T
    fun visitConstraintBooleanExpressionParenNode(parenNode: GlobalConstraintNode.BooleanExpressionNode.ParenNode): T
    fun visitConstraintBooleanExpressionOperatorNode(operatorNode: GlobalConstraintNode.BooleanExpressionNode.OperatorNode): T
    fun visitConstraintBooleanExpressionComparisonNode(comparisonNode: GlobalConstraintNode.BooleanExpressionNode.ComparisonNode): T
    fun visitSimpleReferenceNode(simpleReference: ReferenceNode.SimpleReferenceNode): T
    fun visitNestedReferenceNode(nestedReference: ReferenceNode.NestedReferenceNode): T
    fun visitQueryElemAlignNode(alignNode: QueryElemNode.AlignNode): T
    fun visitRestrictionProximityNode(proximityNode: RestrictionTypeNode.ProximityNode): T
    fun visitRestrictionContextNode(contextNode: RestrictionTypeNode.ContextNode): T

}

data class RootNode(val query: QueryNode, val constraint: GlobalConstraintNode?, override val location: Interval) : EqlAstNode() {
    override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitRootNode(this)
}

data class QueryNode(val query: List<QueryElemNode>, val restriction: RestrictionTypeNode?, override val location: Interval) : EqlAstNode() {
    override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryNode(this)
}

sealed class QueryElemNode : EqlAstNode() {
    data class NotNode(val elem: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemNotNode(this)
    }

    data class AlignNode(val left: QueryElemNode, val right: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemAlignNode(this)
    }

    data class AssignNode(val identifier: String, val elem: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemAssignNode(this)
    }

    data class SimpleNode(val content: String, val type: SimpleQueryType, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemSimpleNode(this)
    }

    data class IndexNode(val index: String, val elem: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemIndexNode(this)
    }

    data class AttributeNode(val entity: String, val attribute: String, val elem: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemAttributeNode(this)
    }

    data class ParenNode(val query: QueryNode, val restriction: RestrictionTypeNode?, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemParenNode(this)
    }

    data class BooleanNode(val left: QueryElemNode, val operator: BooleanOperator, val right: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemBooleanNode(this)
    }

    data class OrderNode(val left: QueryElemNode, val right: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemOrderNode(this)
    }

    data class SequenceNode(val elems: List<QueryElemNode>, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemSequenceNode(this)
    }

    data class RestrictionNode(val left: QueryElemNode, val right: QueryElemNode, val type: RestrictionTypeNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemRestrinctionNode(this)
    }
}

sealed class RestrictionTypeNode : EqlAstNode() {
    data class ProximityNode(val distance: String, override val location: Interval) : RestrictionTypeNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitRestrictionProximityNode(this)
    }

    data class ContextNode(val restriction: ContextRestrictionType, override val location: Interval) : RestrictionTypeNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitRestrictionContextNode(this)
    }
}

sealed class ContextRestrictionType {
    object Paragraph : ContextRestrictionType()
    object Sentence : ContextRestrictionType()
    data class Query(val query: QueryElemNode) : ContextRestrictionType()
}

data class GlobalConstraintNode(val expression: BooleanExpressionNode, override val location: Interval) : EqlAstNode() {
    override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitGlobalContraintNode(this)


    sealed class BooleanExpressionNode : EqlAstNode() {
        data class NotNode(val elem: BooleanExpressionNode, override val location: Interval) : BooleanExpressionNode() {
            override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitConstraintBooleanExpressionNotNode(this)
        }

        data class ParenNode(val expression: BooleanExpressionNode, override val location: Interval) : BooleanExpressionNode() {
            override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitConstraintBooleanExpressionParenNode(this)
        }

        data class OperatorNode(val left: BooleanExpressionNode, val operator: BooleanOperator, val right: BooleanExpressionNode, override val location: Interval) : BooleanExpressionNode() {
            override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitConstraintBooleanExpressionOperatorNode(this)
        }

        data class ComparisonNode(val left: ReferenceNode, val operator: RelationalOperator, val right: ReferenceNode, override val location: Interval) : BooleanExpressionNode() {
            override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitConstraintBooleanExpressionComparisonNode(this)
        }
    }
}

sealed class ReferenceNode : EqlAstNode() {
    data class SimpleReferenceNode(val identifier: String, override val location: Interval) : ReferenceNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitSimpleReferenceNode(this)
    }

    data class NestedReferenceNode(val identifier: String, val attribute: String, override val location: Interval) : ReferenceNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitNestedReferenceNode(this)
    }
}

enum class SimpleQueryType {
    STRING,
    INT_INTERVAL,
    DATE_INTERVAL
}

enum class RelationalOperator {
    EQ, NE, LT, LE, GT, GE;
}

enum class BooleanOperator {
    AND, OR;
}



