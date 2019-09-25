package cz.vutbr.fit.knot.enticing.eql.compiler.ast

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.interval.Interval

interface EqlAstNode : AstNode {
    val location: Interval
    fun <T> accept(visitor: EqlVisitor<T>): T
}

interface EqlVisitor<T> {
    fun visitRootNode(): T
    fun visitQueryElemNotNode(): T
    fun visitQueryElemAssignNode(): T
}

data class PureMgj4Node(val query: String) : EqlAstNode {
    override val location: Interval = Interval.valueOf(-1, -1)
    override fun <T> accept(visitor: EqlVisitor<T>): T {
        throw IllegalStateException("Cannot visit pure mg4j node")
    }
}

data class RootNode(val query: List<QueryElemNode>, val constraint: Constraint?, override val location: Interval) : EqlAstNode {
    override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitRootNode()
}

sealed class QueryElemNode : EqlAstNode {
    data class NotNode(val elem: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemNotNode()
    }

    data class AssignNode(val identifier: String, val elem: QueryElemNode, override val location: Interval) : QueryElemNode() {
        override fun <T> accept(visitor: EqlVisitor<T>): T = visitor.visitQueryElemAssignNode()
    }
}


enum class RelationalOperator {
    EQ, NEQ, LT, LE, GT, GE;
}

enum class LogicBinaryOperator {
    AND, OR;
}



