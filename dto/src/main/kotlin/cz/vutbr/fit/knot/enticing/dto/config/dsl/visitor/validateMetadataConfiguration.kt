package cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.AttributeConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration

fun EnticingConfigurationValidator.validateMetadataConfiguration(metadataConfiguration: MetadataConfiguration) {
    val cycleDetected = checkForCycle(metadataConfiguration)
    if (cycleDetected || this.hasErrors()) return
    resolveFullNames(metadataConfiguration)
    resolveAttributes(metadataConfiguration)
}

fun EnticingConfigurationValidator.resolveAttributes(metadataConfiguration: MetadataConfiguration) {

    fun resolve(entity: EntityConfiguration) {
        val attributes = mutableMapOf<String, AttributeConfiguration>()
        if (entity.parentEntityName != null) {
            val parent = metadataConfiguration.entities.getValue(entity.parentEntityName!!)
            if (!parent.attributesResolved())
                resolve(parent)
            attributes.putAll(parent.allAttributes)
        }
        attributes.putAll(entity.ownAttributes)
        entity.allAttributes = attributes
    }

    for (entity in metadataConfiguration.entities.values) {
        if (!entity.attributesResolved())
            resolve(entity)
    }
}

fun EnticingConfigurationValidator.resolveFullNames(metadataConfiguration: MetadataConfiguration) {
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

fun EnticingConfigurationValidator.checkForCycle(metadataConfiguration: MetadataConfiguration): Boolean {
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