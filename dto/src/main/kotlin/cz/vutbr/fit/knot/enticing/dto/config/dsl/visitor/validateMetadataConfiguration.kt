package cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.AttributeConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration

/**
 * Validates the metadata configuration part of EnticingConfiguration.
 * It consists of the following parts:
 * 1) Cycle detection in the entity inheritance graph
 * 2) Entity full name resolution ( person->artist->painter...)
 * 3) Attribute inheritance
 */
fun EnticingConfigurationValidator.validateMetadataConfiguration(metadataConfiguration: MetadataConfiguration) {
    val cycleDetected = checkForCycle(metadataConfiguration)
    if (cycleDetected || this.hasErrors()) return
    resolveFullNames(metadataConfiguration)
    resolveAttributes(metadataConfiguration)
}

/**
 * For each entity, inherit all parent attributes by 'pulling them down the graph'
 */
private fun EnticingConfigurationValidator.resolveAttributes(metadataConfiguration: MetadataConfiguration) {
    val seen = mutableSetOf<String>()

    fun resolve(entity: EntityConfiguration) {
        seen.add(entity.name)
        val attributes = mutableMapOf<String, AttributeConfiguration>()
        if (entity.parentEntityName != null) {
            val parent = metadataConfiguration.entities.getValue(entity.parentEntityName!!)
            if (parent.name !in seen)
                resolve(parent)
            for ((name, info) in parent.allAttributes)
                attributes[name] = info.copyOf()
        }
        for ((name, info) in entity.ownAttributes)
            attributes[name] = info.copyOf()

        entity.allAttributes = attributes
        // update attribute indexes
        for ((i, attribute) in attributes.values.withIndex())
            attribute.attributeIndex = i
    }

    for (entity in metadataConfiguration.entities.values) {
        if (entity.name !in seen)
            resolve(entity)
    }
}

/**
 * Resolve full entity names by traversing the graph
 */
private fun EnticingConfigurationValidator.resolveFullNames(metadataConfiguration: MetadataConfiguration) {
    val seen = mutableSetOf<String>()

    fun resolve(entity: EntityConfiguration): String {
        if (entity.name in seen) return entity.fullName
        entity.fullName = if (entity.parentEntityName != null) resolve(metadataConfiguration.entities.getValue(entity.parentEntityName!!)) + "->" else ""
        entity.fullName += entity.name

        seen.add(entity.name)
        return entity.fullName
    }

    for (entity in metadataConfiguration.entities.values) {
        if (entity.name !in seen) {
            entity.fullName = resolve(entity)
        }
    }
}

/**
 * Performs DFS based cycle detection on the entity graph.
 * As a side effect, it also report invalid parent references,
 * because they are discovered during the execution
 * @return true if there is a cycle
 */
private fun EnticingConfigurationValidator.checkForCycle(metadataConfiguration: MetadataConfiguration): Boolean {
    val seen = mutableSetOf<String>()
    val active = mutableSetOf<String>()

    val cycle = mutableSetOf<String>()

    fun hasCycle(entity: String): Boolean {
        seen.add(entity)
        active.add(entity)

        val parent = metadataConfiguration.entities.getValue(entity).parentEntityName
        if (parent != null) {
            if (metadataConfiguration.entities[parent] == null) {
                report("Entity '$entity' has invalid parent entity '$parent'")
                return false
            }
            if (parent in active) {
                cycle.add(parent)
                cycle.add(entity)
                return true
            } else if (parent !in seen) {
                if (hasCycle(parent)) {
                    val fullCycleReached = entity in cycle
                    if (fullCycleReached) {
                        report("Cycle in entity graph detected: $cycle")
                        cycle.clear()
                    } else if (cycle.isNotEmpty()) cycle.add(entity)

                    return true
                }
            }
        }

        active.remove(entity)
        return false
    }

    for (entity in metadataConfiguration.entities.keys)
        if (entity !in seen && hasCycle(entity))
            return true

    return false
}