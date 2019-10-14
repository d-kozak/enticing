package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ReferenceNode

class NestedRefCheck(id: String) : EqlAstCheck<ReferenceNode.NestedReferenceNode>(id, ReferenceNode.NestedReferenceNode::class) {
    override fun analyze(node: ReferenceNode.NestedReferenceNode, symbolTable: SymbolTable, corpusConfiguration: CorpusConfiguration, reporter: Reporter) {
        if (node.identifier !in symbolTable) {
            val idLocation = Interval.valueOf(node.location.from, node.location.from + node.identifier.length - 1)
            reporter.error("Identifier ${node.identifier} is not available", idLocation, id)
        } else {
            val source = symbolTable.getValue(node.identifier)
            if (source.elem is QueryElemNode.IndexNode && source.elem.index == corpusConfiguration.entityMapping.entityIndex && source.elem.elem is QueryElemNode.SimpleNode) {
                val entity = corpusConfiguration.entities[source.elem.elem.content]
                if (entity != null) {
                    if (node.attribute !in entity.attributes) {
                        val attributeLoc = Interval.valueOf(node.location.to - (node.attribute.length - 1), node.location.to)
                        reporter.error("Attribute ${node.attribute}  is not part of entity ${entity.name}", attributeLoc, id)
                    }
                }
            }
            if (source.elem is QueryElemNode.AttributeNode) {
                val entity = corpusConfiguration.entities[source.elem.entity] // error should be reporter earlier in EntityCheck
                if (entity != null) {
                    if (node.attribute !in entity.attributes) {
                        val attributeLoc = Interval.valueOf(node.location.to - (node.attribute.length - 1), node.location.to)
                        reporter.error("Attribute ${node.attribute}  is not part of entity ${entity.name}", attributeLoc, id)
                    }
                }
            }
        }
    }
}