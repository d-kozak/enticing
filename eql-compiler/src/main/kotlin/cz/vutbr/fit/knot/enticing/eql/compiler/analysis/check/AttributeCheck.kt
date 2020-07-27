package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

class AttributeCheck(id: String) : EqlAstCheck<QueryElemNode.AttributeNode>(id, QueryElemNode.AttributeNode::class) {
    override fun analyze(node: QueryElemNode.AttributeNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        var entity: EntityConfiguration? = metadataConfiguration.entities[node.entity]
        var found = false
        while (entity != null) {
            if (node.attribute in entity.ownAttributes) {
                found = true
                break
            }
            entity = metadataConfiguration.entities[entity.parentEntityName]
        }
        if (found) {
            val attribute = entity!!.ownAttributes.getValue(node.attribute)
            node.correspondingIndex = attribute.index.name
        } else {
            val attributeLocation = Interval.valueOf(
                    node.location.from + node.entity.length + 1,
                    node.location.from + node.entity.length + node.attribute.length
            )
            reporter.error("Attribute '${node.attribute}' of entity ${node.entity} is not available", attributeLocation, id)
        }
    }
}