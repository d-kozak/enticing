package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*

abstract class QueryAgnosticVisitor<T> : EqlVisitor<T> {
    private fun fail(): Nothing = throw IllegalStateException("root nodes should never be called in this type of visitor")

    override fun visitRootNode(node: RootNode): T = fail()

    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode): T = fail()

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode): T = fail()

    override fun visitQueryElemRestrictionNode(node: QueryElemNode.RestrictionNode): T = fail()

    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode): T = fail()

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode): T = fail()

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode): T = fail()

    override fun visitQueryNode(node: QueryNode): T = fail()

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode): T = fail()

    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode): T = fail()

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode): T = fail()

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode): T = fail()

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode): T = fail()

    override fun visitRestrictionProximityNode(node: RestrictionTypeNode.ProximityNode): T = fail()

    override fun visitRestrictionContextNode(node: RestrictionTypeNode.ContextNode): T = fail()

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode): T = fail()

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode): T = fail()
}