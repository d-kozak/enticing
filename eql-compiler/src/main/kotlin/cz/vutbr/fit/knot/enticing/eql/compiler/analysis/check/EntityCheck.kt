package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

/**
 * Checks:
 * 1) whether the requested entity really exists
 * 2) updates the node to contains proper prefix string to match the entity
 */
@Cleanup("put the entity analysis into a separate node?")
class EntityCheck(id: String) : EqlAstCheck<QueryElemNode.AttributeNode>(id, QueryElemNode.AttributeNode::class) {
    override fun execute(node: QueryElemNode.AttributeNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        val entityConfig = metadataConfiguration.entities[node.entity]
        if (entityConfig != null) {
            @WhatIf("one entity can't be a prefix of another entity now..? :X")
            node.entityNode.content = entityConfig.fullName + "*"
        } else {
            val entityLocation = Interval.valueOf(node.location.from, node.location.from + node.entity.length - 1)
            reporter.error("Entity '${node.entity}' is not available", entityLocation, id)
        }
    }
}