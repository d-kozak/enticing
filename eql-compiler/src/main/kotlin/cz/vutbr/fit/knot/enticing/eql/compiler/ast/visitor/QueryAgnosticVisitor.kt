package cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.*

abstract class QueryAgnosticVisitor<T> : EqlVisitor<T> {
    private fun failInternal(): Nothing = throw IllegalStateException("root nodes should never be called in this type of visitor")

    override fun visitRootNode(node: RootNode): T = failInternal()

    override fun visitQueryElemNotNode(node: QueryElemNode.NotNode): T = failInternal()

    override fun visitQueryElemAssignNode(node: QueryElemNode.AssignNode): T = failInternal()


    override fun visitQueryElemSimpleNode(node: QueryElemNode.SimpleNode): T = failInternal()

    override fun visitQueryElemIndexNode(node: QueryElemNode.IndexNode): T = failInternal()

    override fun visitQueryElemAttributeNode(node: QueryElemNode.AttributeNode): T = failInternal()

    override fun visitQueryElemParenNode(node: QueryElemNode.ParenNode): T = failInternal()

    override fun visitQueryElemBooleanNode(node: QueryElemNode.BooleanNode): T = failInternal()

    override fun visitQueryElemOrderNode(node: QueryElemNode.OrderNode): T = failInternal()

    override fun visitQueryElemSequenceNode(node: QueryElemNode.SequenceNode): T = failInternal()

    override fun visitQueryElemAlignNode(node: QueryElemNode.AlignNode): T = failInternal()

    override fun visitProximityRestrictionNode(node: ProximityRestrictionNode): T = failInternal()

    override fun visitSimpleReferenceNode(node: ReferenceNode.SimpleReferenceNode): T = failInternal()

    override fun visitNestedReferenceNode(node: ReferenceNode.NestedReferenceNode): T = failInternal()

    override fun visitQueryElemNextNode(node: QueryElemNode.NextNode): T = failInternal()
}