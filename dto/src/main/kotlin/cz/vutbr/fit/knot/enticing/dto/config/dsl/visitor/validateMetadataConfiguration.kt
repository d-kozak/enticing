package cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration

fun EnticingConfigurationValidator.validateMetadataConfiguration(metadataConfiguration: MetadataConfiguration) {
    val cycleDetected = checkForCycle(metadataConfiguration)
    if (cycleDetected) return
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