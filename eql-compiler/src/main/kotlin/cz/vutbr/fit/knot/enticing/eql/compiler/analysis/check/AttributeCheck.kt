package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

/**
 * Checks whether given attribute really exists for a given entity
 * and if so, it updates to node to contain reference to a proper index to query.
 */
@Cleanup("can't we simply use entity.allAttributes now?")
class AttributeCheck(id: String) : EqlAstCheck<QueryElemNode.AttributeNode>(id, QueryElemNode.AttributeNode::class) {
    override fun execute(node: QueryElemNode.AttributeNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
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