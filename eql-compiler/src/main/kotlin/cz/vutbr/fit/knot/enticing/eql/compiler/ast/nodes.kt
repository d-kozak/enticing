package cz.vutbr.fit.knot.enticing.eql.compiler.ast

interface AstNode

data class QueryNode(val query: List<QueryElem>, val constraint: Constraint?) : AstNode

sealed class QueryElem : AstNode {
    data class Sequence(val elems: List<QueryElem>) : QueryElem()
    data class Word(val value: String) : QueryElem()
    data class Index(val index: String, val value: String, val attributes: List<Attribute>) : QueryElem()
    data class Order(val left: QueryElem, val right: QueryElem, val limitation: Limitation? = null) : QueryElem()
    data class Paren(val elems: List<QueryElem>) : QueryElem()
    data class BinaryOperation(val operator: LogicBinaryOperator, val left: QueryElem, val right: QueryElem, val limitation: Limitation? = null) : QueryElem()
    data class UnaryOperation(val operator: LogicUnaryOperator, val child: QueryElem) : QueryElem()
    data class ErrorQueryElem(val message: String = "") : QueryElem()
}

sealed class Limitation : AstNode {
    object Par : Limitation()
    object Sent : Limitation()
    data class Proximity(val distance: Int) : Limitation()
    data class ErrorLimitation(val message: String = "") : Limitation()
}

sealed class Attribute : AstNode {
    data class ValidAttribute(val index: String, val attribute: String, val value: String) : Attribute()
    data class ErrorAttribute(val message: String = "") : Attribute()
}

sealed class Constraint : AstNode {
    data class RelationalOperation(val operator: RelationalOperator, val left: Reference, val right: Reference)
    data class RelationalOperationWithConstant(val operator: RelationalOperator, val left: Reference, val right: String)
    data class LogicBinaryOperation(val operator: LogicBinaryOperator, val left: Constraint, val right: Constraint) : Constraint()
    data class LogicUnaryOperation(val operator: LogicUnaryOperator, val child: Constraint)
    data class ErrorConstraint(val message: String = "") : Constraint()

}

sealed class Reference : AstNode {
    data class ValidReference(val index: String, val value: String) : Reference()
    data class ErrorReference(val message: String) : Reference()
}

enum class RelationalOperator {
    EQ, NEQ, LT, LE, GT, GE;
}

enum class LogicBinaryOperator {
    AND, OR;
}

enum class LogicUnaryOperator {
    NOT;
}


data class MockNode(val query: String = "") : AstNode

