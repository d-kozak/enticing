package cz.vutbr.fit.knot.enticing.eql.compiler.ast

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.AggregatingListener
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.EqlListener
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.listener.EqlWalker
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.DeepCopyVisitor
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.Mgj4QueryGeneratingVisitor
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.DocumentMatch

@WhatIf("""
    this way creating ids prevents use from making the code parallel, but otherwise local ids should be unique, 
    because the ast should never have more than LONG.MAX_VALUE nodes at once, so overflow should not be a problem
    """)
var counter = 0L

/**
 * Base class for all AST nodes.
 *
 */
abstract class EqlAstNode : AstNode {

    /**
     * unique id of current node in his AST
     *
     * does NOT have to globally unique over multiple trees
     */
    val id: Long = counter++

    /**
     * Parent node reference to allow for traversing up
     */
    var parent: EqlAstNode? = null

    /**
     * What part of original query this node represents - for showing the results back to the user
     */
    abstract val location: Interval

    /**
     * Most analysis are implemented as visitors or listeners over this tree
     */
    abstract fun <T> accept(visitor: EqlVisitor<T>): T

    /**
     * Walk down the tree and call the EqlListers callbacks
     */
    fun walk(listener: EqlListener, walker: EqlWalker = EqlWalker(listener)) = this.accept(walker)

    override fun toMgj4Query(): String = this.accept(Mgj4QueryGeneratingVisitor())

    override fun deepCopy(): AstNode = this.accept(DeepCopyVisitor())

    /**
     * Contains information how the query matched the document
     * Each item consists of indexes of node's children intervals and interval created using these subintervals
     */
    var matchInfo: MutableSet<DocumentMatch> = mutableSetOf()

    /**
     * Execute given piece of code for each node in the ast
     */
    fun forEachNode(fn: (EqlAstNode) -> Unit) {
        this.walk(AggregatingListener(fn))
    }
}

/**
 * Visitor over the the AST
 */
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
    fun visitQueryElemNextNode(node: QueryElemNode.NextNode): T

}

/**
 * Root of the AST
 */
data class RootNode(var query: QueryElemNode, val constraint: ConstraintNode?, override val location: Interval) : EqlAstNode() {
    /**
     * Contains references to all identifiers used in the query
     */
    var symbolTable: SymbolTable? = null

    /**
     * Context restriction of the query, if any
     */
    var contextRestriction: ContextRestriction? = null

    /**
     * Document restriction of the query, if any
     */
    var documentRestriction: DocumentRestriction? = null

    /**
     * The original string based query this AST was created from
     */
    lateinit var originalQuery: String

    override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitRootNode(this)
}

/**
 * Context restrictions of the query
 */
enum class ContextRestriction(
        /**
         * All the names that can be used to specify this type of restriction
         */
        val restrictionNames: Set<String>
) {
    /**
     * Limit to a single sentence
     */
    SENTENCE(setOf("sentence", "sent")),

    /**
     * Limit to a single paragraph
     */
    PARAGRAPH(setOf("paragraph", "par"));

    companion object {
        /**
         * Which indexes in the query can be used to specify context restriction
         */
        val indexNames = setOf("context", "ctx")
    }
}

/**
 * Document restrictions of the query
 */
sealed class DocumentRestriction {
    /**
     * Only document with given id (interval id given by Enticing)
     */
    data class Id(val text: String) : DocumentRestriction()

    /**
     * Only document with given title
     */
    data class Title(val text: String) : DocumentRestriction()

    /**
     * Only document with given url
     */
    data class Url(val text: String) : DocumentRestriction()

    /**
     * Only document with given uuid
     */
    data class Uuid(val text: String) : DocumentRestriction()
}

/**
 * Tags nodes which may contain proximity restrictions
 */
interface WithProximityRestriction {
    var restriction: ProximityRestrictionNode?
}

sealed class QueryElemNode : EqlAstNode() {
    data class NotNode(val elem: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemNotNode(this)
    }

    data class AlignNode(val left: QueryElemNode, val right: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemAlignNode(this)
    }

    data class NextNode(var elems: List<QueryElemNode>, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemNextNode(this)
    }

    data class AssignNode(val identifier: String, val elem: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemAssignNode(this)
    }

    data class SimpleNode(var content: String, val type: SimpleQueryType, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemSimpleNode(this)

        /**
         * the index operator this leaf is under, already converted to the column index in the IndexDocument content for convenience
         */
        var index: Int = 0
    }

    data class IndexNode(val index: String, val elem: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemIndexNode(this)
    }

    data class AttributeNode(val entityNode: SimpleNode, val attribute: String, val elem: QueryElemNode, override val location: Interval) : QueryElemNode() {

        val entity: String = entityNode.content

        var correspondingIndex: String = "<<<Unknown>>>"

        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemAttributeNode(this)
    }

    data class ParenNode(var query: QueryElemNode, override var restriction: ProximityRestrictionNode? = null, override val location: Interval) : QueryElemNode(), WithProximityRestriction {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemParenNode(this)
    }

    data class BooleanNode(val children: MutableList<QueryElemNode>, val operator: BooleanOperator, override var restriction: ProximityRestrictionNode? = null, override val location: Interval) : QueryElemNode(), WithProximityRestriction {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemBooleanNode(this)
    }

    data class OrderNode(var left: QueryElemNode, var right: QueryElemNode, override var restriction: ProximityRestrictionNode? = null, override val location: Interval) : QueryElemNode(), WithProximityRestriction {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemOrderNode(this)
    }

    data class SequenceNode(val elems: List<SimpleNode>, override val location: Interval) : QueryElemNode() {
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
@Cleanup("the mg4j values should not be here, this part should be mg4j agnostic")
enum class RelationalOperator(val mg4jValue: String) {
    EQ("="), NE("!="), LT("<"), LE("<="), GT(">"), GE(">=");
}

@Cleanup("the mg4j values should not be here, this part should be mg4j agnostic")
enum class BooleanOperator(val mg4jValue: String) {
    AND("&"), OR("|")
}



