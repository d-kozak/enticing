package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.BooleanOperator
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ReferenceNode

/**
 * Checks
 * 1) whether nested reference really corresponds to a query which evaluates to an entity or a list of entities
 * 2) whether this list of entities really have requested attribute
 */
class NestedRefCheck(id: String) : EqlAstCheck<ReferenceNode.NestedReferenceNode>(id, ReferenceNode.NestedReferenceNode::class) {
    override fun execute(node: ReferenceNode.NestedReferenceNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        val idLocation = Interval.valueOf(node.location.from, node.location.from + node.identifier.length - 1)
        val attrLocation = Interval.valueOf(node.location.from + node.identifier.length + 1, node.location.to)
        val source = symbolTable[node.identifier]
        if (source != null) {
            /**
             * nested ref is only allowed for expressions that results in a non-empty set of entities
             * currently, this can happen in two ways:
             * a) index operator with or inside -> "nertag:(a|b|c)"
             * b) or operator over attributes -> "person.name:york | place.name:york"
             *
             * overlapping is also possible -> "person.name:york | nertag:location"
             */
            val entities = collectAllEntities(source.elem, metadataConfiguration)
                    ?.map { it.replace("*", "") }
                    ?.map { metadataConfiguration.resolveEntity(it) }

            if (entities != null) {
                val validEntities = entities.filterNotNull().toSet()
                if (!validEntities.all { node.attribute in it.allAttributes }) {
                    val all = validEntities.map { it.name }
                    val missingIn = validEntities.filter { node.attribute !in it.ownAttributes }
                            .map { it.name }
                    reporter.error("Attribute ${node.attribute} is not a common attribute of entities $all, it is missing in $missingIn", attrLocation, id)
                } else {
                    node.correspondingEntities = validEntities
                }
            } else {
                reporter.error("This identifier should correspond to an entity-like subquery", idLocation, id)
            }
        } else {
            reporter.error("Identifier ${node.identifier} is not available", idLocation, id)
        }
    }

    @WhatIf("Check if queries really contain just a single node")
    fun collectAllEntities(root: QueryElemNode, metadataConfiguration: MetadataConfiguration): Set<String>? {
        val nodes = mutableSetOf<String>()
        fun collect(node: QueryElemNode): Boolean = when (node) {
            is QueryElemNode.SimpleNode -> {
                nodes.add(node.content)
                true
            }
            is QueryElemNode.IndexNode -> if (node.index == metadataConfiguration.entityIndexName) collect(node.elem) else false
            is QueryElemNode.AttributeNode -> if (node.entity in metadataConfiguration.entities && node.attribute in metadataConfiguration.entities.getValue(node.entity).ownAttributes) {
                nodes.add(node.entity)
            } else false
            is QueryElemNode.ParenNode -> collect(node.query)
            is QueryElemNode.BooleanNode -> if (node.operator == BooleanOperator.OR) node.children.map { collect(it) }.all { it } else false
            else -> false
        }
        return if (collect(root)) nodes else null
    }
}