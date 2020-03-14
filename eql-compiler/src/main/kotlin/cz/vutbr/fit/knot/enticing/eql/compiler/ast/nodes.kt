package cz.vutbr.fit.knot.enticing.eql.compiler.ast

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.AgregatingListener
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.EqlListener
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.DeepCopyVisitor
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.Mgj4QueryGeneratingVisitor

@WhatIf("""
    this way creating ids prevents use from making the code parallel, but otherwise local ids shoudl be unique, 
    because the ast should never have more than LONG.MAX_VALUE nodes at once, so overflow should not be a problem
    """)
var counter = 0L

abstract class EqlAstNode : AstNode {
    val id: Long = counter++
    var parent: EqlAstNode? = null
    abstract val location: Interval
    abstract fun <T> accept(visitor: EqlVisitor<T>): T
    fun walk(listener: EqlListener, walker: AstWalker = AstWalker(listener)) = this.accept(walker)
    override fun toMgj4Query(): String = this.accept(Mgj4QueryGeneratingVisitor())

    override fun deepCopy(): AstNode = this.accept(DeepCopyVisitor())

    /**
     * Contains information how the query matched the document
     * Each item consists of indexes of node's children intervals and interval created using these subintervals
     */
    var matchInfo: MutableList<Pair<List<Int>, Interval>> = mutableListOf()

    /**
     * Execute given piece of code for each node in the ast
     */
    fun forEachNode(fn: (EqlAstNode) -> Unit) {
        this.walk(AgregatingListener(fn))
    }
}

interface EqlVisitor<T> {
    fun visitRootNode(node: RootNode): T
    fun visitQueryElemNotNode(node: QueryElemNode.NotNode): T
    fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode): T
    fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode): T
    fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode): T
    fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode): T
    fun visitQueryElemParenNode(node: QueryElemNode.ParenNode): T
    fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode): T
    fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode): T
    fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode): T
    fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode): T
    fun visitConstraintNode(node: ConstraintNode): T
    fun visitConstraintBooleanExpressionNotNode(node: ConstraintNode.BooleanExpressionNode.NotNode): T
    fun visitConstraintBooleanExpressionParenNode(node: ConstraintNode.BooleanExpressionNode.ParenNode): T
    fun visitConstraintBooleanExpressionOperatorNode(node: ConstraintNode.BooleanExpressionNode.OperatorNode): T
    fun visitConstraintBooleanExpressionComparisonNode(node: ConstraintNode.BooleanExpressionNode.ComparisonNode): T
    fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode): T
    fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode): T
    fun visitProximityRestrictionNode(node: ProximityRestrictionNode): T

}

data class RootNode(val query: QueryElemNode, val constraint: ConstraintNode?, override val location: Interval) : EqlAstNode() {
    var symbolTable: MutableMap<String, QueryElemNode.AssignNode>? = null
    var contextRestriction: ContextRestriction? = null
    var documentRestriction: DocumentRestriction? = null

    override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitRootNode(this)
}

enum class ContextRestriction(val restrictionNames: Set<String>) {
    SENTENCE(setOf("sentence", "sent")),
    PARAGRAPH(setOf("paragraph", "par"));

    companion object {
        val indexNames = setOf("context", "ctx")
    }
}

sealed class DocumentRestriction {
    data class Id(val text: String) : DocumentRestriction()
    data class Title(val text: String) : DocumentRestriction()
    data class Url(val text: String) : DocumentRestriction()
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

        var matchInfoIndex: Int = -1
    }

    data class SimpleNode(val content: String, val type: SimpleQueryType, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemSimpleNode(this)
    }

    data class IndexNode(val index: String, val elem: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemIndexNode(this)
    }

    data class AttributeNode(val entityNode: SimpleNode, val attribute: String, val elem: QueryElemNode, override val location: Interval) : QueryElemNode() {

        val entity: String
            get() = entityNode.content

        var correspondingIndex: String = "<<<Unknown>>>"

        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemAttributeNode(this)
    }

    data class ParenNode(val query: QueryElemNode, val restriction: ProximityRestrictionNode?, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemParenNode(this)
    }

    data class BooleanNode(val children: MutableList<QueryElemNode>, val operator: BooleanOperator, val restriction: ProximityRestrictionNode?, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemBooleanNode(this)
    }

    data class OrderNode(val left: QueryElemNode, val right: QueryElemNode, val restriction: ProximityRestrictionNode?, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemOrderNode(this)
    }

    data class SequenceNode(val elems: List<QueryElemNode>, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemSequenceNode(this)
    }
}

data class ProximityRestrictionNode(val distance: String, override val location: Interval) : EqlAstNode() {
    override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitProximityRestrictionNode(this)
}

data class ConstraintNode(val expression: BooleanExpressionNode, override val location: Interval) : EqlAstNode() {
    override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitConstraintNode(this)


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

    abstract val identifier: String

    data class SimpleReferenceNode(override val identifier: String, override val location: Interval) : ReferenceNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitSimpleReferenceNode(this)
    }

    data class NestedReferenceNode(override val identifier: String, val attribute: String, override val location: Interval) : ReferenceNode() {

        var correspondingEntities = emptySet<EntityConfiguration>()

        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitNestedReferenceNode(this)
    }
}

enum class SimpleQueryType {
    STRING,
    INT_INTERVAL,
    DATE_INTERVAL
}

@Cleanup("actually only EQ and NE should be enough")
enum class RelationalOperator {
    EQ, NE, LT, LE, GT, GE;
}

enum class BooleanOperator(val mg4jValue: String) {
    AND("&"), OR("|")
}



