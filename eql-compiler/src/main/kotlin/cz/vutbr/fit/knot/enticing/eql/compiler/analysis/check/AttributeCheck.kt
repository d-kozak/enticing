package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

class AttributeCheck(id: String) : EqlAstCheck<QueryElemNode.AttributeNode>(id, QueryElemNode.AttributeNode::class) {
    override fun analyze(node: QueryElemNode.AttributeNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        val entity = metadataConfiguration.entities[node.entity] ?: return // Missing entity should be reported earlier
        if (node.attribute !in entity.attributes) {
            val attributeLocation = Interval.valueOf(
                    node.location.from + node.entity.length + 1,
                    node.location.from + node.entity.length + node.attribute.length
            )
            reporter.error("Attribute '${node.attribute}' of entity ${node.entity} is not available", attributeLocation, id)
        } else {
            val attribute = entity.attributes.getValue(node.attribute)
            node.correspondingIndex = attribute.index.name
        }
    }
}