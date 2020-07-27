package cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata

import cz.vutbr.fit.knot.enticing.dto.Entities
import cz.vutbr.fit.knot.enticing.dto.Indexes
import cz.vutbr.fit.knot.enticing.dto.TextMetadata

fun MetadataConfiguration.filterBy(metadata: TextMetadata, defaultIndex: String): MetadataConfiguration {
    val filtered = when (metadata) {
        is TextMetadata.Predefined -> filterPredefined(metadata, defaultIndex)
        is TextMetadata.ExactDefinition -> filterExact(metadata, defaultIndex)
    }
    // copy computed metadata
    filtered.entityIndexName = this.entityIndexName
    filtered.lengthIndexName = this.lengthIndexName
    return filtered
}

private fun MetadataConfiguration.filterPredefined(metadata: TextMetadata.Predefined, defaultIndex: String): MetadataConfiguration = when (metadata.value) {
    "all" -> this
    "none" -> this.copy(entities = LinkedHashMap(), indexes = onlySpecifiedIndexes(defaultIndex))
    else -> throw IllegalArgumentException("Unknown toplevel format ${metadata.value}")
}


private fun MetadataConfiguration.filterExact(metadata: TextMetadata.ExactDefinition, defaultIndex: String): MetadataConfiguration = this.copy(
        indexes = filterIndexes(metadata.indexes, defaultIndex),
        entities = filterEntities(metadata.entities)
)

private fun MetadataConfiguration.filterEntities(entities: Entities): LinkedHashMap<String, EntityConfiguration> = when (entities) {
    is Entities.Predefined -> when (entities.value) {
        "all" -> this.entities
        "none" -> LinkedHashMap()
        else -> throw IllegalArgumentException("Unknown entity format ${entities.value}")
    }
    is Entities.ExactDefinition -> {
        val res = LinkedHashMap<String, EntityConfiguration>()
        for ((name, indexes) in entities.entities) {
            res[name] = when (indexes) {
                is Indexes.Predefined -> {
                    when (indexes.value) {
                        "all" -> getEntityOrDie(name)
                        "none" -> getEntityOrDie(name).copy(ownAttributes = mutableMapOf())
                        else -> throw IllegalArgumentException("Unknown entity attribute format ${indexes.value}")
                    }
                }
                is Indexes.ExactDefinition -> {
                    val entity = getEntityOrDie(name)
                    val attributes = indexes.names
                            .map {
                                it to (entity.ownAttributes[it]
                                        ?: throw IllegalArgumentException("Unknown attribute $it of entity $name"))
                            }
                            .toMap()
                            .toMutableMap()
                    entity.copy(ownAttributes = attributes)
                }
            }
        }
        res
    }
}

private fun MetadataConfiguration.getEntityOrDie(name: String) =
        this.entities[name] ?: throw IllegalArgumentException("Unknown entity $name")


private fun MetadataConfiguration.filterIndexes(indexes: Indexes, defaultIndex: String)
        : LinkedHashMap<String, IndexConfiguration> = when (indexes) {
    is Indexes.Predefined -> when (indexes.value) {
        "all" -> this.indexes
        "none" -> onlySpecifiedIndexes(defaultIndex)
        else -> throw IllegalArgumentException("Unknown index format ${indexes.value}")
    }

    is Indexes.ExactDefinition -> if (defaultIndex in indexes.names)
        onlySpecifiedIndexes(indexes.names)
    else {
        val names = indexes.names.toMutableList()
        names.add(0, defaultIndex)
        onlySpecifiedIndexes(names)
    }
}


private fun MetadataConfiguration.onlySpecifiedIndexes(indexes: List<String>
)
        : LinkedHashMap<String, IndexConfiguration> {
    val res = LinkedHashMap<String, IndexConfiguration>()

    for (index in indexes)
        res[index] = this.indexes[index] ?: throw IllegalArgumentException("Could not find index $index")

    return res
}

private fun MetadataConfiguration.onlySpecifiedIndexes(vararg indexes: String) = onlySpecifiedIndexes(indexes.toList())