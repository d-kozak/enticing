package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

class AttributeCheck(id: String) : EqlAstCheck<QueryElemNode.AttributeNode>(id, QueryElemNode.AttributeNode::class) {
    override fun analyze(node: QueryElemNode.AttributeNode, corpusConfiguration: CorpusConfiguration, reporter: Reporter) {
        val entity = corpusConfiguration.entities[node.entity] ?: return // Missing entity should be reported earlier
        if (node.attribute !in entity.attributes) {
            reporter.error("Attribute '${node.attribute}' of entity ${node.entity} is not available", node.location, id)
        } else {
            val attribute = entity.attributes.getValue(node.attribute)
            node.correspondingIndex = attribute.correspondingIndex
        }
    }
}